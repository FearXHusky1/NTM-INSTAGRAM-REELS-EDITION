package com.gommer;

import com.gommer.contents.machine.TileEntityFentReactor;
import com.gommer.contents.recipes.AddonRecipes;
import com.gommer.contents.registers.*;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = reels.MODID, name = reels.NAME, version = reels.VERSION, dependencies = "required-after:hbm")
public class reels {
    public static final String MODID = "reels";
    public static final String NAME = "G&G's Instagram Reels Addon";
    public static final String VERSION = "1.0.0";

    static { FluidRegistry.enableUniversalBucket(); }

    @EventHandler public void preInit(FMLPreInitializationEvent e) { AddonFluids.preInit(); AddonBlocks.preInit(); GameRegistry.registerTileEntity(TileEntityFentReactor.class, "reels:fent_reactor"); }
    @EventHandler public void init(FMLInitializationEvent e) { AddonFluids.init(); }
    @EventHandler public void postInit(FMLPostInitializationEvent e) { AddonRecipes.register(); }
}
