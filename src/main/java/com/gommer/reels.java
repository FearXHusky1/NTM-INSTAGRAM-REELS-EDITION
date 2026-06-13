package com.gommer;

import com.gommer.contents.recipes.AddonRecipes;
import com.gommer.contents.registers.AddonBlocks;
import com.gommer.contents.registers.AddonFluids;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = reels.MODID, name = reels.NAME, version = reels.VERSION,
        dependencies = "required-after:hbm")
public class reels {

    public static final String MODID = "reels";
    public static final String NAME = "Gommer's Nigga Tech Addon";
    public static final String VERSION = "1.0.0";

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        AddonFluids.preInit();
        AddonBlocks.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        AddonFluids.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        AddonRecipes.register();
    }
}
