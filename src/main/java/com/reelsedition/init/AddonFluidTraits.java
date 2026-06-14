package com.reelsedition.init;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.reelsedition.contents.AddonFluids;
import com.reelsedition.contents.fluids.AddonFluidType;
import com.hbm.inventory.fluid.FluidType;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AddonFluidTraits {
	public static final Map<AddonFluidType, FluidType> copyTraits = new HashMap<>();
	public static void preInit() {
		// Code your own traits here (I think it won't work if you code it in AddonFluids)
		for (Entry<AddonFluidType,FluidType> entry : copyTraits.entrySet()) {
			if (entry.getKey().copyFunction != null)
				entry.getKey().copyTraits(entry.getValue(),entry.getKey().copyFunction);
		}
	}
}
