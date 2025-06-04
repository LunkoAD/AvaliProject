package com.lunkoashtail.avaliproject.event;

import com.lunkoashtail.avaliproject.entity.ModEntities;
import com.lunkoashtail.avaliproject.entity.custom.AvaliExplosiveEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;

public class ExplosiveProjectileEvent {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;
        if (world instanceof ServerLevel projectileLevel) {
            Projectile _entityToSpawn = new Object() {
                public Projectile getArrow(Level level, float damage, int knockback, byte piercing) {
                    AbstractArrow entityToSpawn = new AvaliExplosiveEntity(ModEntities.AVALI_EXPLOSIVE.get(), level) {
                        @Override
                        public byte getPierceLevel() {
                            return piercing;
                        }


                    };
                    entityToSpawn.setBaseDamage(damage);
                    entityToSpawn.setSilent(true);
                    return entityToSpawn;
                }
            }.getArrow(projectileLevel, 20, 0, (byte) 2);
            _entityToSpawn.setPos(x, (y + 3), z);
            _entityToSpawn.shoot((entity.getLookAngle().x), (entity.getLookAngle().y), (entity.getLookAngle().z), 5, 0);
            projectileLevel.addFreshEntity(_entityToSpawn);
        }
        if (entity instanceof Player _player)
            _player.getCooldowns().addCooldown(itemstack.getItem(), 150);
    }
}
