package com.reelsedition.contents;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_VentRadiation;
import com.hbm.render.misc.EnumSymbol;
import com.reelsedition.contents.fluids.AddonFluidType;

import java.lang.reflect.Field;
import java.util.List;

import static com.hbm.inventory.fluid.Fluids.*;

public class AddonFluids {
	// metaOrder is not public in Fluids class...
	public static final List<FluidType> metaOrderPointer;
	static {
		// Solution: Brute force into it! FBI OPEN UP
		Field metaField = null;
		try {
			metaField = Fluids.class.getDeclaredField("metaOrder");
			metaField.setAccessible(true);
			metaOrderPointer = (List<FluidType>)metaField.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			// Fuck off if we got denied somehow
			throw new RuntimeException(e);
		}
	}
	public static FluidType FENT;
	public static FluidType FENT_RAD;
	public static FluidType FENT_SCHRAB;
	public static void init() {
		// Basic fluid and trait initialization (Don't code in custom traits here, i think it won't work)
		FENT = new AddonFluidType("fent",0x4CC2A2,0,0,0,EnumSymbol.NONE).addTraits(LIQUID,VISCOUS);
		FENT_RAD = new AddonFluidType("fent_radioactive",0x00ffaa,0,0,0,EnumSymbol.NONE).addTraits(LIQUID,VISCOUS,new FT_VentRadiation(0.01f));
		FENT_SCHRAB = new AddonFluidType("fent_schrabatic",0x00d5ff,0,0,0,EnumSymbol.NONE).addTraits(LIQUID,VISCOUS,new FT_VentRadiation(0.08f));
	}
}
