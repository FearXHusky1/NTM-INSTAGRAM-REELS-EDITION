package com.reelsedition.overwrite_contents.mixin.hbm;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.render.misc.EnumSymbol;
import com.reelsedition.contents.AddonFluids;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = com.hbm.inventory.fluid.Fluids.class, remap = false)
public abstract class MixinFluids {
    /*
    static {
        System.out.println("Fluid Mixin Loading");
    }
    @Shadow @Final
    protected static List<FluidType> metaOrder;

    @Shadow
    private static void registerCalculatedFuel(FluidType type, double base, double combustMult, FT_Combustible.FuelGrade grade) {}

    @Unique
    private static FluidType reels$createFixed(String name, int color, int p, int f, int r, EnumSymbol symbol, int id) {
        FluidType fluid = new FluidType(name, color, p, f, r, symbol, name.toLowerCase(java.util.Locale.US), 0xFFFFFF, id, null);
        fluid.renderWithTint = false;
        return fluid;
    }
*/ // we live by our own rules
    @Inject(method = "init", at = @At("TAIL"), require = 1)
    private static void reelsedition$registerExtraFluids(CallbackInfo ci) {
        AddonFluids.init();
    }
}