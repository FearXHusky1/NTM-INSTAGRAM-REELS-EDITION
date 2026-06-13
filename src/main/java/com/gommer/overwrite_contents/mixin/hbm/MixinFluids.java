package com.gommer.overwrite_contents.mixin.hbm;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.render.misc.EnumSymbol;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = com.hbm.inventory.fluid.Fluids.class, remap = false)
public abstract class MixinFluids {

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
        int idCounter = 5000;
        FluidType test = reels$createFixed("TEST", 0x4CC2A2, 0, 0, 0, EnumSymbol.NONE, idCounter++);
        test.addTraits(com.hbm.inventory.fluid.Fluids.LIQUID, com.hbm.inventory.fluid.Fluids.VISCOUS);
        metaOrder.add(test);

        System.out.println("[overwrite_contents] TEST registered, id=" + test.getID());
    }
}