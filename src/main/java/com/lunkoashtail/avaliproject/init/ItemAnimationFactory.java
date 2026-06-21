package com.lunkoashtail.avaliproject.init;

import com.lunkoashtail.avaliproject.item.custom.*;
import software.bernie.geckolib.animatable.GeoItem;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;

@EventBusSubscriber
public class ItemAnimationFactory {
    @SubscribeEvent
    public static void animatedItems(PlayerTickEvent.Post event) {
        String animation = "";
        ItemStack mainhandItem = event.getEntity().getMainHandItem().copy();
        ItemStack offhandItem = event.getEntity().getOffhandItem().copy();
        if (mainhandItem.getItem() instanceof GeoItem || offhandItem.getItem() instanceof GeoItem) {
            if (mainhandItem.getItem() instanceof AvaliswordItem animatable) {
                animation = mainhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getMainHandItem(), tag -> tag.putString("geckoAnim", ""));
                    if (event.getEntity().level().isClientSide()) {
                        ((AvaliswordItem) event.getEntity().getMainHandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (offhandItem.getItem() instanceof AvaliswordItem animatable) {
                animation = offhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getOffhandItem(), tag -> tag.putString("geckoAnim", ""));
                    if (event.getEntity().level().isClientSide()) {
                        ((AvaliswordItem) event.getEntity().getOffhandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
        }
        if (mainhandItem.getItem() instanceof FirelanceItem animatable) {
            animation = mainhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getMainHandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((FirelanceItem) event.getEntity().getMainHandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (offhandItem.getItem() instanceof FirelanceItem animatable) {
            animation = offhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getOffhandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((FirelanceItem) event.getEntity().getOffhandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (mainhandItem.getItem() instanceof MlseriesItem animatable) {
            animation = mainhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getMainHandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((MlseriesItem) event.getEntity().getMainHandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (offhandItem.getItem() instanceof MlseriesItem animatable) {
            animation = offhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getOffhandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((MlseriesItem) event.getEntity().getOffhandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (mainhandItem.getItem() instanceof NovaItem animatable) {
            animation = mainhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getMainHandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((NovaItem) event.getEntity().getMainHandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (offhandItem.getItem() instanceof NovaItem animatable) {
            animation = offhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getOffhandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((NovaItem) event.getEntity().getOffhandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (mainhandItem.getItem() instanceof QrcItem animatable) {
            animation = mainhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getMainHandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((QrcItem) event.getEntity().getMainHandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (offhandItem.getItem() instanceof QrcItem animatable) {
            animation = offhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getOffhandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((QrcItem) event.getEntity().getOffhandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (mainhandItem.getItem() instanceof StormItem animatable) {
            animation = mainhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getMainHandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((StormItem) event.getEntity().getMainHandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (offhandItem.getItem() instanceof StormItem animatable) {
            animation = offhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getOffhandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((StormItem) event.getEntity().getOffhandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (mainhandItem.getItem() instanceof BlizzardItem animatable) {
            animation = mainhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getMainHandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((BlizzardItem) event.getEntity().getMainHandItem().getItem()).animationprocedure = animation;
                }
            }
        }
        if (offhandItem.getItem() instanceof BlizzardItem animatable) {
            animation = offhandItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getString("geckoAnim");
            if (!animation.isEmpty()) {
                CustomData.update(DataComponents.CUSTOM_DATA, event.getEntity().getOffhandItem(), tag -> tag.putString("geckoAnim", ""));
                if (event.getEntity().level().isClientSide()) {
                    ((BlizzardItem) event.getEntity().getOffhandItem().getItem()).animationprocedure = animation;
                }
            }
        }
    }
}