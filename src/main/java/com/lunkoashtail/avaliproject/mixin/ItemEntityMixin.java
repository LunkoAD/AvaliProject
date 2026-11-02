package com.lunkoashtail.avaliproject.mixin;

import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;


// thanks to this file for the code: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/mixins/ItemEntityMixin.java

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract ItemStack getItem();

    @IfModLoaded("ae2")
    @Inject(at = @At("HEAD"), method = "hurt", cancellable = true)
    void handleExplosion(DamageSource src, float dmg, CallbackInfoReturnable<Boolean> ci) {
        if (src.is(DamageTypeTags.IS_EXPLOSION) && !isRemoved()) {

            var self = (ItemEntity) (Object) this;
            if (!self.getType().equals(EntityType.ITEM)) return; //return and stop processing if not an item entity.
            // check if the explosion damage source is hitting one of our sync crystals - @989onan
            if (self.getItem().is(ModItems.SYNC_CRYSTAL)) {
                //construct a new sync crystal with a quantum pair ID for applied energistics - @989onan
                ItemStack shattered_pair = new ItemStack(ModItems.SHATTERED_SYNC_CRYSTAL.asItem(), 2);
                //shattered_pair
                appeng.blockentity.qnb.QuantumBridgeBlockEntity.assignFrequency(shattered_pair);
                self.setItem(shattered_pair);
                ci.cancel();
            }
        }
    }
}

