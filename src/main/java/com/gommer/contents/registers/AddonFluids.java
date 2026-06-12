package com.gommer.contents.registers;

import com.gommer.contents.fluids.FentFluid;
import com.gommer.contents.fluids.AddonFluidType;
import com.hbm.render.misc.EnumSymbol;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.List;

public class AddonFluids {

    public static Fluid fent = new FentFluid("fent");
    public static AddonFluidType fentType;
    public static final List<AddonFluidType> metaOrderPointer = new ArrayList<>();

    public static void preInit() {
        FluidRegistry.registerFluid(fent);
        FluidRegistry.addBucketForFluid(fent);
        // NTM FluidType — poison=3, flammability=0, reactivity=0, no hazard symbol
        fentType = new AddonFluidType("fent", 0xAACCFF, 3, 0, 0, EnumSymbol.NONE, "gommer:textures/gui/fluids/fent.png");
    }

    public static void init() {
        fent = FluidRegistry.getFluid("fent");
    }
}
