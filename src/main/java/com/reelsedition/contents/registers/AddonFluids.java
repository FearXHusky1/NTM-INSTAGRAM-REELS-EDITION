package com.reelsedition.contents.registers;

import com.reelsedition.contents.fluids.FentFluid;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class AddonFluids {

    public static Fluid fent = new FentFluid("fent");

    public static void preInit() {
        FluidRegistry.registerFluid(fent);
        FluidRegistry.addBucketForFluid(fent);
    }

    public static void init() {
        fent = FluidRegistry.getFluid("fent");
    }
}