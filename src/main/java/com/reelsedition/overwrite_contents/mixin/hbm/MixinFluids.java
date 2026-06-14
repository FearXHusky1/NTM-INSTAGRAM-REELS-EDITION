package com.reelsedition.overwrite_contents.mixin.hbm;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.render.misc.EnumSymbol;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = com.hbm.inventory.fluid.Fluids.class, remap = false)
public abstract class MixinFluids {
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

    @Inject(method = "init", at = @At("TAIL"))
    private static void hbmextra$registerExtraFluids(CallbackInfo ci) {
        int idCounter = 6000;

        FluidType fent = reels$createFixed("fent", 0x4CC2A2, 0, 0, 0, EnumSymbol.NONE, idCounter++);
        FluidType fent_radioactive = reels$createFixed("fent_radioactive", 0x00ffaa, 0, 0, 0, EnumSymbol.NONE, idCounter++);
        FluidType fent_schrabatic = reels$createFixed("fent_schrabatic", 0x00d5ff, 0, 0, 0, EnumSymbol.NONE, idCounter++);


        fent.addTraits(com.hbm.inventory.fluid.Fluids.LIQUID, com.hbm.inventory.fluid.Fluids.VISCOUS);
        fent_radioactive.addTraits(com.hbm.inventory.fluid.Fluids.LIQUID, com.hbm.inventory.fluid.Fluids.VISCOUS);
        fent_radioactive.addTraits(com.hbm.inventory.fluid.Fluids.LIQUID);
        metaOrder.add(fent);
        metaOrder.add(fent_radioactive);
        metaOrder.add(fent_schrabatic);

    }
}