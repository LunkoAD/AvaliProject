package com.lunkoashtail.avaliproject.avaligear.ship;

import com.lunkoashtail.avaliproject.avaligear.AvaliGear;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;

public class CatalinaEntity extends Entity implements GeoEntity {
    public static final int LANDED = 0, TAKEOFF = 1, FLYING = 2, LANDING = 3;
    private static final int TAKEOFF_TICKS = 92;
    private static final int LANDING_TICKS = 60;
    private static final double MAX_SPEED = 0.9, HOVER_SPEED = 0.3, CLIMB = 0.35;
    private static final int MAX_RIDERS = 9;

    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(CatalinaEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HOVER = SynchedEntityData.defineId(CatalinaEntity.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation ANIM_LANDED = RawAnimation.begin().thenPlay("ramp_open").thenLoop("landed");
    private static final RawAnimation ANIM_TAKEOFF = RawAnimation.begin().thenPlay("ramp_close").thenPlay("takeoff");
    private static final RawAnimation ANIM_FLY = RawAnimation.begin().thenLoop("fly");
    private static final RawAnimation ANIM_HOVER = RawAnimation.begin().thenPlay("to_hover").thenLoop("hover");
    private static final RawAnimation ANIM_LAND = RawAnimation.begin().thenPlay("land").thenLoop("landed_closed");

    public static IntSupplier clientVerticalInput = () -> 0;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final List<BlockPos> hull = new ArrayList<>();
    private int stateTicks;
    private Vec3 landFrom = Vec3.ZERO;
    private Vec3 landTo = Vec3.ZERO;
    private float landYawFrom, landYawTo;

    private int lerpSteps;
    private double lerpX, lerpY, lerpZ, lerpYRot;

    public CatalinaEntity(EntityType<? extends CatalinaEntity> type, Level level) {
        super(type, level);
        this.noCulling = true;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(STATE, LANDED);
        builder.define(HOVER, false);
    }

    public int getShipState() {
        return entityData.get(STATE);
    }

    private void setShipState(int state) {
        entityData.set(STATE, state);
        stateTicks = 0;
    }

    public boolean isHovering() {
        return entityData.get(HOVER);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        entityData.set(STATE, tag.getInt("ShipState"));
        entityData.set(HOVER, tag.getBoolean("Hover"));
        stateTicks = tag.getInt("StateTicks");
        hull.clear();
        for (long l : tag.getLongArray("Hull")) hull.add(BlockPos.of(l));
        if (tag.contains("LandTo")) {
            long[] to = tag.getLongArray("LandTo");
            landTo = Vec3.atBottomCenterOf(BlockPos.of(to[0]));
            landYawTo = Float.intBitsToFloat((int) to[1]);
            landFrom = position();
            landYawFrom = getYRot();
        }
        if (getShipState() == TAKEOFF) setShipState(FLYING);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("ShipState", getShipState());
        tag.putBoolean("Hover", isHovering());
        tag.putInt("StateTicks", stateTicks);
        tag.putLongArray("Hull", hull.stream().mapToLong(BlockPos::asLong).toArray());
        if (getShipState() == LANDING) {
            tag.putLongArray("LandTo", new long[]{BlockPos.containing(landTo).asLong(), Float.floatToIntBits(landYawTo)});
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof Player p ? p : null;
    }

    @Override
    public boolean isControlledByLocalInstance() {

        return getShipState() == FLYING && super.isControlledByLocalInstance();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return getPassengers().size() < MAX_RIDERS;
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
        int index = getPassengers().indexOf(passenger);
        double x, y, z;
        if (index <= 0) {
            x = 0; y = 50; z = -128;
        } else {
            int seat = index - 1;
            x = seat % 2 == 0 ? -33 : 33;
            y = 21;
            z = 40 + (seat / 2) * 22;
        }
        return CatalinaLayout.toWorld(x, y, z, getYRot());
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        if (getShipState() == LANDED) {
            int i = Math.max(0, getPassengers().indexOf(passenger));
            return position().add(CatalinaLayout.toWorld(-20 + (i % 3) * 20, 17, 100 + (i / 3) * 10, getYRot()));
        }
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) return InteractionResult.PASS;
        if (level().isClientSide) return InteractionResult.SUCCESS;
        return boardAsPilot(player) || (player.getVehicle() == null && player.startRiding(this))
                ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    public boolean boardAsPilot(Player player) {
        if (player.getVehicle() == this) return false;
        if (getControllingPassenger() != null && getControllingPassenger() != player) {
            player.displayClientMessage(Component.translatable("message.avaliproject.catalina.pilot_taken"), true);
            return player.startRiding(this);
        }
        List<Entity> others = new ArrayList<>(getPassengers());
        others.forEach(Entity::stopRiding);
        boolean ok = player.startRiding(this, true);
        others.forEach(e -> e.startRiding(this, true));
        if (ok) player.displayClientMessage(Component.translatable("message.avaliproject.catalina.pilot_help"), true);
        return ok;
    }

    @Override
    public boolean isPickable() {
        return !isRemoved();
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (level().isClientSide || isRemoved()) return false;
        if (source.getEntity() instanceof Player player && player.isShiftKeyDown() && getShipState() == LANDED
                && getPassengers().isEmpty()) {
            if (!player.getAbilities().instabuild) spawnAtLocation(new ItemStack(AvaliGear.CATALINA_ITEM));
            discard();
            return true;
        }
        return false;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (reason.shouldDestroy() && !level().isClientSide) removeHull();
        super.remove(reason);
    }

    public void toggleFlight(Player pilot) {
        switch (getShipState()) {
            case LANDED -> takeOff();
            case FLYING -> {
                if (!beginLanding()) pilot.displayClientMessage(Component.translatable("message.avaliproject.catalina.too_high"), true);
            }
            default -> {
            }
        }
    }

    public void toggleHover() {
        entityData.set(HOVER, !isHovering());
    }

    private void takeOff() {

        List<Player> inside = level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(14, 6, 14),
                p -> !p.isPassenger() && CatalinaLayout.insideBay(CatalinaLayout.toLocalPx(p.position().subtract(position()), getYRot())));
        for (Player p : inside) {
            if (getPassengers().size() >= MAX_RIDERS) break;
            p.startRiding(this, true);
        }
        removeHull();
        setShipState(TAKEOFF);
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.NEUTRAL, 2.0F, 0.6F);
    }

    private boolean beginLanding() {
        float yaw = Math.round(getYRot() / 90.0F) * 90.0F;
        int bx = Mth.floor(getX()), bz = Mth.floor(getZ());
        int ground = Integer.MIN_VALUE;
        for (int dx = -3; dx <= 3; dx += 3) {
            for (int dz = -9; dz <= 9; dz += 3) {
                Vec3 o = new Vec3(dx, 0, dz).yRot((float) Math.toRadians(-yaw));
                ground = Math.max(ground, level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        bx + (int) Math.round(o.x), bz + (int) Math.round(o.z)));
            }
        }
        if (getY() - ground > 28) return false;
        landFrom = position();
        landTo = new Vec3(bx + 0.5, ground, bz + 0.5);
        landYawFrom = getYRot();
        landYawTo = yaw;
        setDeltaMovement(Vec3.ZERO);
        setShipState(LANDING);
        return true;
    }

    public boolean landHere() {
        float yaw = Math.round(getYRot() / 90.0F) * 90.0F;
        setYRot(yaw);
        yRotO = yaw;
        setPos(Mth.floor(getX()) + 0.5, Math.floor(getY()), Mth.floor(getZ()) + 0.5);
        entityData.set(STATE, LANDED);
        placeHull();
        return true;
    }

    private void finishLanding() {
        setPos(landTo);
        setYRot(landYawTo);
        yRotO = landYawTo;
        setDeltaMovement(Vec3.ZERO);
        placeHull();
        setShipState(LANDED);

        List<Entity> riders = new ArrayList<>(getPassengers());
        for (int i = 1; i < riders.size(); i++) riders.get(i).stopRiding();
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.PISTON_EXTEND, SoundSource.NEUTRAL, 1.5F, 0.5F);
    }

    private void placeHull() {
        if (level().isClientSide) return;
        removeHull();
        BlockPos origin = BlockPos.containing(getX(), getY(), getZ());
        float yaw = getYRot();
        for (CatalinaLayout.Cell c : CatalinaLayout.CELLS) {
            BlockPos p = CatalinaLayout.cellToWorld(origin, c, yaw);
            BlockState existing = level().getBlockState(p);
            if (!existing.canBeReplaced()) continue;
            BlockState state = (c.kind() == CatalinaLayout.CONSOLE ? AvaliGear.CATALINA_CONSOLE : AvaliGear.CATALINA_HULL)
                    .defaultBlockState().setValue(CatalinaHullBlock.HEIGHT, c.height());
            level().setBlock(p, state, 3);
            hull.add(p);
        }
    }

    private void removeHull() {
        for (BlockPos p : hull) {
            BlockState s = level().getBlockState(p);
            if (s.getBlock() instanceof CatalinaHullBlock) level().removeBlock(p, false);
        }
        hull.clear();
    }

    @Override
    public void tick() {
        super.tick();
        tickLerp();
        stateTicks++;
        int state = getShipState();
        if (state == LANDED) {
            setDeltaMovement(Vec3.ZERO);
            return;
        }
        if (!level().isClientSide) {
            if (state == TAKEOFF) {
                double rise = stateTicks > 32 ? 0.07 : 0.0;
                setDeltaMovement(0, rise, 0);
                move(MoverType.SELF, getDeltaMovement());
                if (stateTicks >= TAKEOFF_TICKS) setShipState(FLYING);
                return;
            }
            if (state == LANDING) {
                float t = Math.min(1.0F, stateTicks / (float) LANDING_TICKS);
                float e = t * t * (3 - 2 * t);
                setPos(landFrom.lerp(landTo, e));
                setYRot(Mth.rotLerp(e, landYawFrom, landYawTo));
                if (stateTicks >= LANDING_TICKS) finishLanding();
                return;
            }
        }
        if (state == FLYING) {
            if (isControlledByLocalInstance()) {
                pilotMove();
            }
        }
    }

    private void pilotMove() {
        LivingEntity pilot = getControllingPassenger();
        if (pilot == null) {
            setDeltaMovement(getDeltaMovement().scale(0.85));
            move(MoverType.SELF, getDeltaMovement());
            return;
        }
        float yaw = Mth.approachDegrees(getYRot(), pilot.getYRot(), isHovering() ? 6.0F : 3.0F);
        setYRot(yaw);
        double speed = isHovering() ? HOVER_SPEED : MAX_SPEED;
        float rad = (float) Math.toRadians(yaw);
        Vec3 forward = new Vec3(-Mth.sin(rad), 0, Mth.cos(rad));
        Vec3 right = new Vec3(-forward.z, 0, forward.x);
        Vec3 target = forward.scale(pilot.zza * speed).add(right.scale(-pilot.xxa * speed * 0.4))
                .add(0, clientVerticalInput.getAsInt() * CLIMB, 0);
        setDeltaMovement(getDeltaMovement().lerp(target, 0.08));
        move(MoverType.SELF, getDeltaMovement());
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYRot = yRot;
        this.lerpSteps = 6;
    }

    private void tickLerp() {
        if (isControlledByLocalInstance()) {
            lerpSteps = 0;
            syncPacketPositionCodec(getX(), getY(), getZ());
        }
        if (lerpSteps > 0) {
            lerpPositionAndRotationStep(lerpSteps, lerpX, lerpY, lerpZ, lerpYRot, getXRot());
            lerpSteps--;
        }
    }

    @Override
    public double lerpTargetX() {
        return lerpSteps > 0 ? lerpX : getX();
    }

    @Override
    public double lerpTargetY() {
        return lerpSteps > 0 ? lerpY : getY();
    }

    @Override
    public double lerpTargetZ() {
        return lerpSteps > 0 ? lerpZ : getZ();
    }

    @Override
    public float lerpTargetYRot() {
        return lerpSteps > 0 ? (float) lerpYRot : getYRot();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 256.0 * 256.0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "ship", 6, this::animate));
    }

    private PlayState animate(AnimationState<CatalinaEntity> state) {
        return state.setAndContinue(switch (getShipState()) {
            case TAKEOFF -> ANIM_TAKEOFF;
            case FLYING -> isHovering() ? ANIM_HOVER : ANIM_FLY;
            case LANDING -> ANIM_LAND;
            default -> ANIM_LANDED;
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
