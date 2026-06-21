package com.lunkoashtail.avaliproject.block.fluid;

import com.lunkoashtail.avaliproject.AvaliProject;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModFluids {

    public static final DeferredRegister<Fluid> FluidRegister = DeferredRegister.create(ForgeRegistries.FLUIDS, AvaliProject.MOD_ID);
    public static final DeferredRegister<FluidType> FluidTypeRegister = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, AvaliProject.MOD_ID);

    public static final RegistryObject<Fluid> AMMONIA = FluidRegister.register("ammonia", Ammonia.Source::new);
    public static final RegistryObject<Fluid> FLOWING_AMMONIA = FluidRegister.register("ammonia_flowing", Ammonia.Flowing::new);// .create(ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID,"flowing_ammonia"), ForgeRegistries.FLUIDS);



    public static final RegistryObject<FluidType> AMMONIA_TYPE = FluidTypeRegister.register(
            AMMONIA.getId().getPath(),
            () -> new FluidType(
                    FluidType.Properties.create()
                            .canExtinguish(true)
                            .canConvertToSource(true)
                            .temperature(45) //45 Kelvin, very cold.
                            .supportsBoating(true)
                            ){



                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
                {
                    consumer.accept(new IClientFluidTypeExtensions()
                    {
                        private static final ResourceLocation AMMONIA_STILL = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/ammonia_still"),
                                AMMONIA_FLOW = ResourceLocation.fromNamespaceAndPath(AvaliProject.MOD_ID, "block/ammonia_flow"),
                                UNDERAMMONIA_LOCATION = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/misc/underwater.png"),
                                AMMONIA_OVERLAY = ResourceLocation.fromNamespaceAndPath("minecraft","block/water_overlay");

                        @Override
                        public ResourceLocation getStillTexture()
                        {
                            return AMMONIA_STILL;
                        }

                        @Override
                        public ResourceLocation getFlowingTexture()
                        {
                            return AMMONIA_FLOW;
                        }

                        @Nullable
                        @Override
                        public ResourceLocation getOverlayTexture()
                        {
                            return AMMONIA_OVERLAY;
                        }

                        @Override
                        public ResourceLocation getRenderOverlayTexture(Minecraft mc)
                        {
                            return UNDERAMMONIA_LOCATION;
                        }

                        @Override
                        public int getTintColor()
                        {
                            return 0x6AB7B79E;
                        }

                        @Override
                        public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos)
                        {
                            return 0x6AB7B79E;
                        }
                    });
                }
            });
    public static final ForgeFlowingFluid.Properties AmmoniaProperties = new ForgeFlowingFluid.Properties(AMMONIA_TYPE, AMMONIA, FLOWING_AMMONIA).bucket(ModItems.AMMONIA_BUCKET).tickRate(2);



    public static void register(IEventBus eventBus) {
        FluidRegister.register(eventBus);
        FluidTypeRegister.register(eventBus);


    }

}
