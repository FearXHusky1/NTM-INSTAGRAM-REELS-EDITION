package com.gommer;

import com.gommer.proxy.CommonProxy;
import com.gommer.contents.recipes.AddonRecipes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = reels.MODID, name = reels.NAME, version = reels.VERSION,
        dependencies = "required-after:hbm")
public class reels {

    public static final String MODID = "reels";
    public static final String NAME = "Gommer's Nigga Tech Addon";
    public static final String VERSION = "1.0.0";

    @SidedProxy(
            clientSide = "com.gommer.proxy.ClientProxy",
            serverSide = "com.gommer.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        AddonRecipes.register();
    }
}