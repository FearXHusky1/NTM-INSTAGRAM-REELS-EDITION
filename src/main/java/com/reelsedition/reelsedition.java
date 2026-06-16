package com.reelsedition;

import com.hbm.world.biome.BiomeGenCraterBase;
import com.reelsedition.contents.machine.TileEntityFentReactor;
import com.reelsedition.contents.recipes.AddonRecipes;
import com.reelsedition.contents.registers.*;
import com.reelsedition.contents.registers.entity.Droid;
import com.reelsedition.event.CyberneticEventHandler;
import com.reelsedition.init.AddonFluidTraits;
import com.reelsedition.init.recipes.AddonMixerRecipes;
import com.reelsedition.init.recipes.AddonSolderingRecipes;
import com.reelsedition.proxy.CommonProxy;
import com.reelsedition.init.recipes.AddonCompressorRecipes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.List;

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

        CyberneticEventHandler.registerCapability();



    }

    @EventHandler
    public void init(FMLInitializationEvent e) {

    }
    //telaviv magic

    private void addDroidSpawn(Biome biome) {
        try {
            List<Biome.SpawnListEntry> list = ObfuscationReflectionHelper.getPrivateValue(
                    Biome.class, biome, "spawnableMonsterList"
            );
            list.add(new Biome.SpawnListEntry(Droid.class, 3, 1, 1));
            System.out.println("Droid spawn added to: " + biome.getBiomeName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        AddonRecipes.register();

        //droid spawn in crater
        addDroidSpawn(BiomeGenCraterBase.craterBiome);
        addDroidSpawn(BiomeGenCraterBase.craterInnerBiome);
        addDroidSpawn(BiomeGenCraterBase.craterOuterBiome);
    }

    public static void registerSerializable() {
        // just steal these files from leafia under init/recipes as you need them

         //AddonChemplantRecipes.register();
        //AddonAssemblerRecipes.register();
        //AddonGasCentRecipes.register();
        //AddonElectrolyzerRecipes.register();
        //AddonPyroOvenRecipes.register();
        //AddonAnvilRecipes.registerSmithingRecipes();
        //AddonAnvilRecipes.registerConstructionRecipes();
        //AddonPUREXRecipes.register();
        //AddonWasteDrumRecipes.register();
        //AddonCentrifugeRecipes.register();
        //AddonArcWelderRecipes.register();
        //AddonDFCRecipes.register();
        //AddonSmeltingRecipes.register();
        AddonSolderingRecipes.register();
        //AddonMixerRecipes.register();
        AddonCompressorRecipes.register();
       //AddonPlasmaForgeRecipes.register();
        //AddonShredderRecipes.register();
        //AddonPARecipes.register();
       // AddonExposureChamberRecipes.register();

    }



}