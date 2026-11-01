package com.lunkoashtail.avaliproject.vitals;

import com.lunkoashtail.avaliproject.component.BloodContents;
import com.lunkoashtail.avaliproject.component.ModDataComponents;
import com.lunkoashtail.avaliproject.effect.ModEffects;
import com.lunkoashtail.avaliproject.item.ModItems;
import com.lunkoashtail.avaliproject.limb.Limb;
import com.lunkoashtail.avaliproject.limb.LimbData;
import com.lunkoashtail.avaliproject.limb.ModAttachments;
import com.lunkoashtail.avaliproject.limb.PainData;
import com.lunkoashtail.avaliproject.network.LimbDataSyncPayload;
import com.lunkoashtail.avaliproject.network.PainSyncPayload;
import com.lunkoashtail.avaliproject.species.Species;
import com.lunkoashtail.avaliproject.vitals.Vitals.Stat;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

public enum MedicalAction {
    WOUND_GLUE(Target.LIMB, Limbs.ANY, 0, UseAnim.NONE, SoundEvents.SLIME_SQUISH_SMALL),
    TOURNIQUET(Target.LIMB, Limbs.ARMS_AND_LEGS, 0, UseAnim.NONE, SoundEvents.ARMOR_EQUIP_LEATHER.value()),
    ANTISEPTIC(Target.LIMB, Limbs.ANY, 0, UseAnim.NONE, SoundEvents.BOTTLE_EMPTY),
    ALCOHOL(Target.LIMB, Limbs.ANY, 0, UseAnim.NONE, SoundEvents.BOTTLE_EMPTY),
    ALCOHOL_DRINK(Target.SELF, Limbs.ANY, 32, UseAnim.DRINK, SoundEvents.GENERIC_DRINK),
    RELIEF_CREAM(Target.LIMB, Limbs.ANY, 0, UseAnim.NONE, SoundEvents.HONEY_DRINK),
    BRUISE_KIT(Target.LIMB, Limbs.ANY, 0, UseAnim.NONE, SoundEvents.ARMOR_EQUIP_LEATHER.value()),
    MED_KIT(Target.SELF, Limbs.ANY, 60, UseAnim.BOW, SoundEvents.ARMOR_EQUIP_LEATHER.value()),
    CHEST_DRAIN(Target.LIMB, Limbs.TORSO, 0, UseAnim.NONE, SoundEvents.BUCKET_EMPTY),
    COMBAT_PEN(Target.LIMB, Limbs.ANY, 0, UseAnim.NONE, SoundEvents.PISTON_EXTEND),
    MORPHINE_AUTOINJECTOR(Target.LIMB, Limbs.LEGS, 0, UseAnim.NONE, SoundEvents.PISTON_EXTEND),
    NALOXONE(Target.SELF, Limbs.ANY, 24, UseAnim.BOW, SoundEvents.PISTON_EXTEND),
    OPIUM(Target.SELF, Limbs.ANY, 32, UseAnim.EAT, SoundEvents.GENERIC_EAT),
    SODIUM_NITROPRUSSIDE(Target.SELF, Limbs.ANY, 32, UseAnim.DRINK, SoundEvents.GENERIC_DRINK),
    STREPTOKINASE(Target.SELF, Limbs.ANY, 32, UseAnim.BOW, SoundEvents.PISTON_EXTEND),
    SALINE(Target.SELF, Limbs.ANY, 60, UseAnim.BOW, SoundEvents.BOTTLE_EMPTY),
    BLOOD_BAG(Target.SELF, Limbs.ANY, 80, UseAnim.BOW, SoundEvents.BOTTLE_EMPTY),
    ANTIBIOTICS(Target.SELF, Limbs.ANY, 32, UseAnim.EAT, SoundEvents.GENERIC_EAT),
    ANTIRAD(Target.SELF, Limbs.ANY, 32, UseAnim.DRINK, SoundEvents.GENERIC_DRINK),
    ANTISERUM(Target.SELF, Limbs.ANY, 32, UseAnim.BOW, SoundEvents.PISTON_EXTEND),
    ANTI_DEPRESSANTS(Target.SELF, Limbs.ANY, 32, UseAnim.EAT, SoundEvents.GENERIC_EAT),
    SLEEPING_PILLS(Target.SELF, Limbs.ANY, 32, UseAnim.EAT, SoundEvents.GENERIC_EAT),
    MINDWIPE(Target.SELF, Limbs.ANY, 40, UseAnim.BOW, SoundEvents.PISTON_EXTEND),
    MANUAL_DEFIBRILLATOR(Target.LIMB, Limbs.TORSO, 0, UseAnim.NONE, SoundEvents.LIGHTNING_BOLT_IMPACT);

    public enum Target { SELF, LIMB }

    public enum Limbs {
        ANY(EnumSet.allOf(Limb.class)),
        ARMS_AND_LEGS(EnumSet.of(Limb.LEFT_ARM, Limb.RIGHT_ARM, Limb.LEFT_LEG, Limb.RIGHT_LEG)),
        LEGS(EnumSet.of(Limb.LEFT_LEG, Limb.RIGHT_LEG)),
        TORSO(EnumSet.of(Limb.BACK));

        public final Set<Limb> allowed;

        Limbs(Set<Limb> allowed) {
            this.allowed = allowed;
        }
    }

    public static final int CHEST_DRAIN_RECHARGE_TICKS = 5000;
    public static final int DEFIBRILLATOR_COOLDOWN_TICKS = 100;
    public static final float ARREST_RESTART_CHANCE = 0.2f;
    public static final float FLATLINE_RESTART_CHANCE = 0.5f;
    public static final float EPINEPHRINE_RESTART_CHANCE = 0.15f;
    public static final float OXYLINE_SECONDS = 60f;
    public static final float FIBRILLATION_FIX_CHANCE = 0.9f;
    public static final int ANTIDEPRESSANT_TICKS = 6000;
    public static final int ANTIDEPRESSANT_COOLDOWN_TICKS = 18000;

    public final Target target;
    public final Limbs limbs;
    public final int useTicks;
    public final UseAnim animation;
    public final SoundEvent sound;

    MedicalAction(Target target, Limbs limbs, int useTicks, UseAnim animation, SoundEvent sound) {
        this.target = target;
        this.limbs = limbs;
        this.useTicks = useTicks;
        this.animation = animation;
        this.sound = sound;
    }

    public boolean speciesAllowed(net.minecraft.world.entity.player.Player patient) {
        Species species = patient.getData(ModAttachments.SPECIES);
        if (this == ANTI_DEPRESSANTS) return true;
        return species == Species.EXPIE;
    }

    public enum Result { CONSUME, KEEP, DAMAGE, FAIL }

    public Result apply(ServerPlayer user, ServerPlayer patient, @Nullable Limb limb, ItemStack stack) {
        Vitals v = patient.getData(ModAttachments.VITALS);
        LimbHealth health = patient.getData(ModAttachments.LIMB_HEALTH);
        LimbData bleed = patient.getData(ModAttachments.LIMB_DATA);
        PainData pain = patient.getData(ModAttachments.PAIN_DATA);

        Result result = switch (this) {
            case WOUND_GLUE -> {
                if (bleed.getBleed(limb) == 0 && health.getSkin(limb) >= 95f) yield fail(user, "There is no open wound there.");
                bleed.setBleed(limb, 0);
                health.addSkin(limb, 15f);
                health.setClean(limb, 300f);
                yield Result.CONSUME;
            }
            case TOURNIQUET -> {
                if (health.hasTourniquet(limb)) {
                    health.setTourniquet(limb, false);
                    if (!user.getInventory().add(new ItemStack(ModItems.TOURNIQUET.get()))) {
                        user.drop(new ItemStack(ModItems.TOURNIQUET.get()), false);
                    }
                    message(user, "Tourniquet removed.");
                    yield Result.KEEP;
                }
                health.setTourniquet(limb, true);
                pain.add(8f);
                message(user, "Tourniquet tightened. Remove it before the limb dies.");
                yield Result.CONSUME;
            }
            case ANTISEPTIC -> {
                health.addInfection(limb, -40f);
                health.setClean(limb, 600f);
                pain.add(5f);
                yield Result.CONSUME;
            }
            case ALCOHOL -> {
                health.addInfection(limb, -20f);
                health.setClean(limb, 300f);
                pain.add(10f);
                yield Result.CONSUME;
            }
            case ALCOHOL_DRINK -> {
                v.add(Stat.ANALGESIA, 10f);
                v.add(Stat.VISCOSITY, -15f);
                v.add(Stat.THIRST, -10f);
                patient.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0));
                yield Result.CONSUME;
            }
            case RELIEF_CREAM -> {
                pain.add(-15f);
                health.addSkin(limb, 5f);
                yield Result.CONSUME;
            }
            case BRUISE_KIT -> {
                if (health.getMuscle(limb) >= 99f) yield fail(user, "That limb isn't bruised.");
                health.addMuscle(limb, 40f);
                yield Result.CONSUME;
            }
            case MED_KIT -> {
                for (Limb each : Limb.values()) {
                    health.addSkin(each, 15f);
                    health.addMuscle(each, 15f);
                    bleed.reduceBleed(each, 15);
                }
                yield Result.DAMAGE;
            }
            case CHEST_DRAIN -> {
                if (v.get(Stat.HEMOTHORAX) < 5f) yield fail(user, "There is no fluid to drain.");
                if (user.getCooldowns().isOnCooldown(stack.getItem())) yield fail(user, "The chest drain is still recharging.");
                v.add(Stat.HEMOTHORAX, -35f);
                pain.add(15f);
                if (health.getSkin(Limb.BACK) < 30f) bleed.addBleed(Limb.BACK, 3);
                user.getCooldowns().addCooldown(stack.getItem(), CHEST_DRAIN_RECHARGE_TICKS);
                yield Result.KEEP;
            }
            case COMBAT_PEN -> {
                boolean alreadyStimulated = v.get(Stat.STIMULANT) > 50f;
                v.add(Stat.STIMULANT, 45f);
                if (!v.isCardiacArrest() && v.get(Stat.DYING_TIME) <= 0f) v.add(Stat.CONSCIOUSNESS, 30f);
                v.set(Stat.OXYLINE_TIME, Math.max(v.get(Stat.OXYLINE_TIME), OXYLINE_SECONDS));
                if (alreadyStimulated) {
                    v.add(Stat.FIBRILLATION, 15f);
                    message(user, "The heart is pounding dangerously...");
                } else {
                    v.add(Stat.FIBRILLATION, -20f);
                }
                if ((v.isCardiacArrest() || v.get(Stat.HEART_RATE) < 1f) && patient.getRandom().nextFloat() < EPINEPHRINE_RESTART_CHANCE) {
                    v.set(Stat.FIBRILLATION, 0f);
                    v.set(Stat.HEART_RATE, 60f);
                    message(user, "The heart kicked back in!");
                }
                yield Result.CONSUME;
            }
            case MORPHINE_AUTOINJECTOR -> {
                float dose = com.lunkoashtail.avaliproject.item.custom.AutoinjectorItem.contentsOf(stack);
                if (dose <= 0.5f) yield fail(user, "The autoinjector is empty.");
                v.add(Stat.OPIOID, dose * 0.8f);
                v.add(Stat.ANALGESIA, dose * 2f);
                stack.remove(com.lunkoashtail.avaliproject.component.ModDataComponents.FLUID_AMOUNT);
                yield Result.KEEP;
            }
            case NALOXONE -> {
                v.set(Stat.OPIOID, 0f);
                v.set(Stat.ANALGESIA, 0f);
                v.add(Stat.CONSCIOUSNESS, 20f);
                pain.add(10f);
                yield Result.CONSUME;
            }
            case OPIUM -> {
                v.add(Stat.OPIOID, 18f);
                v.add(Stat.ANALGESIA, 40f);
                yield Result.CONSUME;
            }
            case SODIUM_NITROPRUSSIDE -> {
                v.add(Stat.VASODILATOR, 60f);
                yield Result.CONSUME;
            }
            case STREPTOKINASE -> {
                if (v.get(Stat.VISCOSITY) > 60f) v.add(Stat.FIBRILLATION, -30f);
                v.add(Stat.VISCOSITY, -70f);
                yield Result.CONSUME;
            }
            case SALINE -> {
                v.add(Stat.BLOOD, 500f);
                v.add(Stat.THIRST, 20f);
                v.add(Stat.VISCOSITY, -10f);
                yield Result.CONSUME;
            }
            case BLOOD_BAG -> {
                BloodContents contents = stack.get(ModDataComponents.BLOOD_CONTENTS);
                if (contents == null || contents.amountMl() <= 0f) yield fail(user, "The blood bag is empty.");
                Species species = patient.getData(ModAttachments.SPECIES);
                if (contents.species() == species) {
                    v.add(Stat.BLOOD, contents.amountMl());
                } else {
                    v.add(Stat.BLOOD, contents.amountMl() * 0.5f);
                    v.add(Stat.FIBRILLATION, 30f);
                    v.add(Stat.VISCOSITY, 40f);
                    health.addInfection(Limb.BACK, 20f);
                    patient.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
                    message(user, "The blood doesn't match! The body is rejecting it.");
                }
                yield Result.CONSUME;
            }
            case ANTIBIOTICS -> {
                v.set(Stat.ANTIBIOTIC_TIME, 600f);
                yield Result.CONSUME;
            }
            case ANTIRAD -> {
                v.set(Stat.ANTIRAD_TIME, 120f);
                yield Result.CONSUME;
            }
            case ANTISERUM -> {
                patient.removeEffect(MobEffects.POISON);
                patient.removeEffect(MobEffects.WITHER);
                yield Result.CONSUME;
            }
            case ANTI_DEPRESSANTS -> {
                if (user.getCooldowns().isOnCooldown(stack.getItem())) yield fail(user, "Too soon for another dose.");
                if (!patient.hasEffect(ModEffects.PACK_LOSS)) {
                    message(user, "You don't feel any different.");
                } else {
                    patient.addEffect(new MobEffectInstance(ModEffects.ANTIDEPRESSANT, ANTIDEPRESSANT_TICKS, 0));
                }
                user.getCooldowns().addCooldown(stack.getItem(), ANTIDEPRESSANT_COOLDOWN_TICKS);
                yield Result.CONSUME;
            }
            case SLEEPING_PILLS -> {
                if (patient.level().isDay()) yield fail(user, "You can only sleep at night.");
                if (!patient.onGround() || patient.isPassenger()) yield fail(user, "You need to lie down on solid ground.");
                VitalsEventHandler.startPillSleep(patient);
                yield Result.CONSUME;
            }
            case MINDWIPE -> {
                v.set(Stat.BRAIN, 100f);
                v.set(Stat.ENERGY, 0f);
                v.set(Stat.MINDWIPE_TIME, 30f);
                v.set(Stat.CONSCIOUSNESS, 0f);
                pain.set(0f);
                patient.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 3600, 0));
                patient.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 1));
                patient.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 3600, 1));
                yield Result.CONSUME;
            }
            case MANUAL_DEFIBRILLATOR -> {
                if (user.getCooldowns().isOnCooldown(stack.getItem())) yield fail(user, "The defibrillator is charging.");
                user.getCooldowns().addCooldown(stack.getItem(), DEFIBRILLATOR_COOLDOWN_TICKS);
                RandomSource random = patient.getRandom();
                if (v.get(Stat.DYING_TIME) > 0f) {
                    if (random.nextFloat() < FLATLINE_RESTART_CHANCE) {
                        VitalsLogic.resuscitate(v);
                        message(user, "Pulse is back! Keep them stable.");
                    } else {
                        message(user, "Still flatlining. Shock again!");
                    }
                } else if (v.isCardiacArrest() || v.get(Stat.HEART_RATE) < 1f) {
                    if (random.nextFloat() < ARREST_RESTART_CHANCE) {
                        v.set(Stat.FIBRILLATION, 0f);
                        v.set(Stat.HEART_RATE, 50f);
                        message(user, "Heartbeat restored!");
                    } else {
                        message(user, "No response. Keep shocking.");
                    }
                } else if (v.get(Stat.FIBRILLATION) >= 5f) {
                    if (random.nextFloat() < FIBRILLATION_FIX_CHANCE) {
                        v.set(Stat.FIBRILLATION, 0f);
                        v.set(Stat.HEART_RATE, 70f);
                        message(user, "Rhythm restored.");
                    } else {
                        message(user, "The rhythm didn't take. Try again.");
                    }
                } else {
                    v.add(Stat.FIBRILLATION, 40f);
                    message(user, "You shocked a beating heart!");
                }
                health.addSkin(Limb.BACK, -5f);
                v.add(Stat.CONSCIOUSNESS, -10f);
                pain.add(20f);
                yield Result.DAMAGE;
            }
        };

        if (result != Result.FAIL) {
            patient.level().playSound(null, patient.getX(), patient.getY(), patient.getZ(), sound, SoundSource.PLAYERS, 0.8f, 1.0f);
            PacketDistributor.sendToPlayer(patient, LimbDataSyncPayload.from(bleed));
            PacketDistributor.sendToPlayer(patient, PainSyncPayload.from(pain));
            VitalsEventHandler.sync(patient);
        }
        return result;
    }

    private static Result fail(ServerPlayer user, String text) {
        message(user, text);
        return Result.FAIL;
    }

    private static void message(ServerPlayer user, String text) {
        user.displayClientMessage(Component.literal(text), true);
    }
}
