package com.reelsedition.init;

import com.hbm.inventory.fluid.trait.FT_Toxin;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.reelsedition.contents.fluids.AddonFluidType;
import com.hbm.inventory.fluid.FluidType;
import com.reelsedition.contents.fluids.traits.FT_POISION_MEGA;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import static com.hbm.inventory.fluid.trait.FluidTrait.traitList;
import static com.hbm.inventory.fluid.trait.FluidTrait.traitNameMap;
//mega poision is useless, feel free to delete or use as template for something else
public class AddonFluidTraits {

	static {
		registerTrait("mega_poision", FT_POISION_MEGA.class);
	}

	public static final Map<AddonFluidType, FluidType> copyTraits = new HashMap<>();

	public static void preInit() {
		for (Entry<AddonFluidType, FluidType> entry : copyTraits.entrySet()) {
			if (entry.getKey().copyFunction != null)
				entry.getKey().copyTraits(entry.getValue(), entry.getKey().copyFunction);
		}
	}

	private static void registerTrait(String name, Class<? extends FluidTrait> clazz) {
		traitNameMap.put(name, clazz);
		traitList.add(clazz);
	}
}