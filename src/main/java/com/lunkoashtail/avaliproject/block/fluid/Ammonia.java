package com.lunkoashtail.avaliproject.block.fluid;

import com.lunkoashtail.avaliproject.datagen.ModFluidTags;
import com.lunkoashtail.avaliproject.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class Ammonia extends ForgeFlowingFluid {
    public Ammonia(ForgeFlowingFluid.Properties properties)
    {
        super(properties);
    }

    @Override
    public @NotNull Item getBucket() {
        return ModItems.AMMONIA_BUCKET.get();
    }


    @Override
    public int getAmount(FluidState pState) { //this should NEVER be used. use extended classes.
        return pState.getValue(LEVEL);
    }

    @Override
    public boolean isSource(FluidState pState) { //this should NEVER be used. use extended classes.
        return true;
    }

    public static class Flowing extends ForgeFlowingFluid.Flowing
    {
        public Flowing()
        {
            super(ModFluids.AmmoniaProperties);
        }

    }

    public static class Source extends ForgeFlowingFluid.Source
    {
        public Source()
        {
            super(ModFluids.AmmoniaProperties);
        }



    }

}
