package com.reelsedition.contents;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import com.hbm.inventory.fluid.trait.FT_Toxin;
import com.hbm.inventory.fluid.trait.FT_VentRadiation;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.render.misc.EnumSymbol;
import com.hbm.util.ArmorRegistry;
import com.reelsedition.contents.fluids.AddonFluidType;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import com.hbm.util.ArmorRegistry.HazardClass;
import java.lang.reflect.Field;
import java.util.List;

import static com.hbm.inventory.fluid.Fluids.*;

public class AddonFluids {
	// metaOrder is not public in Fluids class...
	public static final List<FluidType> metaOrderPointer;

	static {
		// Solution: Brute force into it! FBI OPEN UP                //unsafe but ok we ball
		Field metaField = null;
		try {
			metaField = Fluids.class.getDeclaredField("metaOrder");
			metaField.setAccessible(true);
			metaOrderPointer = (List<FluidType>) metaField.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			// Fuck off if we got denied somehow
			throw new RuntimeException(e);
		}
	}

	public static final float MEGA_POISION = PollutionHandler.POISON_PER_SECOND * 0.05F;
	public static FluidType FENT;
	public static FluidType FENT_RAD;
	public static FluidType FENT_SCHRAB;
	public static FluidType BUG_PASTE;
	public static FluidType STEM_CELLS;
	public static FluidType ZYKLON;

	public static FluidType PIPERIDONE;
	public static FluidType PROPIONYL_CHLORIDE;
	public static FluidType FENTANYL_CRUDE;

	public static void init() {
		// Basic fluid and trait initialization (Don't code in custom traits here, i think it won't work)
		FENT = new AddonFluidType("fent", 0x4CC2A2, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);
		FENT_RAD = new AddonFluidType("fent_radioactive", 0x00ffaa, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS, new FT_VentRadiation(50f));
		FENT_SCHRAB = new AddonFluidType("fent_schrabatic", 0x00d5ff, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS, new FT_VentRadiation(350f));
		BUG_PASTE = new AddonFluidType("bug_paste", 0x003d10, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS, DELICIOUS);
		STEM_CELLS = new AddonFluidType("stem_cells", 0xFFF7F7, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);

		//fent related
		PIPERIDONE = new AddonFluidType("piperidone", 0xD4C856, 0, 1, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS, new com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_NoContainer());
		PROPIONYL_CHLORIDE = new AddonFluidType("propionyl_chloride", 0xB5FFB5, 0, 0, 1, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS, new com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_NoContainer());
		FENTANYL_CRUDE = new AddonFluidType("fentanyl_crude", 0x8B6914, 0, 1, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS, new com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_NoContainer());

		((AddonFluidType)FENT).setDisplayName("Fentanyl");
		((AddonFluidType)FENT_RAD).setDisplayName("Radioactive Fentanyl");
		((AddonFluidType)FENT_SCHRAB).setDisplayName("Schrabatic Fentanyl");
		((AddonFluidType)BUG_PASTE).setDisplayName("Bug Paste");
		((AddonFluidType)STEM_CELLS).setDisplayName("Stem Cells");
		((AddonFluidType)PIPERIDONE).setDisplayName("Piperidone");
		((AddonFluidType)PROPIONYL_CHLORIDE).setDisplayName("Propionyl Chloride");
		((AddonFluidType)FENTANYL_CRUDE).setDisplayName("Crude Fentanyl");

	//broken, leafia plz fix
		//ZYKLON = new AddonFluidType("zyklon", 0xFFF7F7, 0, 0, 0, EnumSymbol.NONE).addTraits(GASEOUS, new FT_Toxin(new FT_Toxin.ToxinDirectDamage(ModDamageSource.cloud, 2F, 20, HazardClass.GAS_LUNG, false)));
	}


}


