package com.reelsedition;

import com.reelsedition.contents.machine.TileEntityFentReactor;
import com.reelsedition.contents.registers.*;
import com.reelsedition.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = reelsedition.MODID, name = reelsedition.NAME, version = reelsedition.VERSION, dependencies = "required-after:hbm")
public class reelsedition {

    public static final String MODID = "reels";
    public static final String NAME = "G&G's Instagram reels Addon";
    public static final String VERSION = "1.0.0";

    @SidedProxy(
            clientSide = "com.reelsedition.proxy.ClientProxy",
            serverSide = "com.reelsedition.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    static {
        FluidRegistry.enableUniversalBucket();
        AddonBlocks.preInit(); // move it here
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
        // remove AddonBlocks.preInit() from here
        AddonFluids.preInit();
        GameRegistry.registerTileEntity(TileEntityFentReactor.class, "reelsedition:fent_reactor");
    }

}