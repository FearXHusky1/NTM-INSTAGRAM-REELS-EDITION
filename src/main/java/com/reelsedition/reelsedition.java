package com.reelsedition;

import com.reelsedition.contents.machine.TileEntityFentReactor;
import com.reelsedition.contents.recipes.AddonRecipes;
import com.reelsedition.contents.registers.*;
import com.reelsedition.init.AddonFluidTraits;
import com.reelsedition.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = reelsedition.MODID, name = reelsedition.NAME, version = reelsedition.VERSION, dependencies = "required-after:hbm")
public class reelsedition {

    public static final String MODID = "reelsedition";
    public static final String NAME = "G&G's Instagram Reels Addon";
    public static final String VERSION = "1.0.0";

    @SidedProxy(
            clientSide = "com.reelsedition.proxy.ClientProxy",
            serverSide = "com.reelsedition.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        AddonBlocks.preInit();

        proxy.preInit(e);
        AddonFluidTraits.preInit();
        GameRegistry.registerTileEntity(TileEntityFentReactor.class, "reelsedition:fent_reactor");
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        AddonRecipes.register();
    }

}