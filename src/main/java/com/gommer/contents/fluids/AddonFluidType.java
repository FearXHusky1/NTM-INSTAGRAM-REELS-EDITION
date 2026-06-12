package com.gommer.contents.fluids;

import com.gommer.contents.registers.AddonFluids;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.render.misc.EnumSymbol;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.List;
import com.gommer.init.AddonFluidTraits;

public class AddonFluidType extends FluidType {
    public static int id = 0;

    public AddonFluidType(String name, int color, int p, int f, int r, EnumSymbol symbol) {
        this(name, color, p, f, r, symbol, name);
    }


    public AddonFluidType(String name, FluidType base) {
        this(name, base.getColor(), base.poison, base.flammability, base.reactivity, base.symbol, base.getName());
        temperature = base.temperature;
        //copyTraits(base,(trait)->true);
        copyFunction = (trait) -> true;
        AddonFluidTraits.copyTraits.put(this, base);
    }

    public AddonFluidType(String name, int color, int p, int f, int r, EnumSymbol symbol, String texFluid) {
        super(name, color, p, f, r, symbol, texFluid.toLowerCase(Locale.US), 0xFFFFFF, 1121 + (id++) /* eevee */, null);
        AddonFluids.metaOrderPointer.add(this);
    }
}


