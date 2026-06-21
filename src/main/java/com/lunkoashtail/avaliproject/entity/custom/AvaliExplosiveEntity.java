package com.lunkoashtail.avaliproject.entity.custom;

import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.event.ExplosiveOnBlockEvent;
import com.lunkoashtail.avaliproject.item.ModItems;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class AvaliExplosiveEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.AVALI_PROJECTILE_ITEM.get());
    private int knockback = 0;

    public AvaliExplosiveEntity(EntityType<? extends AvaliExplosiveEntity> type, Level world) {
        super(type, world);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    @Override
    public void playerTouch(Player entity) {
        super.playerTouch(entity);
        ExplosiveOnBlockEvent.execute(this.level(), this.getX(), this.getY(), this.getZ());
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        ExplosiveOnBlockEvent.execute(this.level(), this.getX(), this.getY(), this.getZ());
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        ExplosiveOnBlockEvent.execute(this.level(), blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround)
            this.discard();
    }

    public static AvaliExplosiveEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return shoot(world, entity, source, 5f, 5, 5);
    }

    public static AvaliExplosiveEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
        return shoot(world, entity, source, pullingPower * 5f, 5, 2);
    }

    public static AvaliExplosiveEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        AvaliExplosiveEntity entityarrow = new AvaliExplosiveEntity(ModEntities.AVALI_EXPLOSIVE.get(), world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        return entityarrow;
    }

    public static AvaliExplosiveEntity shoot(LivingEntity entity, LivingEntity target) {
        AvaliExplosiveEntity entityarrow = new AvaliExplosiveEntity(ModEntities.AVALI_EXPLOSIVE.get(), entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 5f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(5);
        entityarrow.setKnockback(5);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity(entityarrow);
        return entityarrow;
    }

    @Override
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }
}
