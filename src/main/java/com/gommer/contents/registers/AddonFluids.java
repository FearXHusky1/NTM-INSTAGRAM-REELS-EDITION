package com.gommer.contents.registers;

import com.gommer.contents.fluids.FentFluid;
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