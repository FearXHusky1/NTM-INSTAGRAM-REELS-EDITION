package com.reelsedition;

import com.hbm.world.biome.BiomeGenCraterBase;
import com.reelsedition.block.BlockBobbleAddon;
import com.reelsedition.contents.AddonFluids;
import com.reelsedition.contents.machine.BlockStorageCrateAddon;
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
import com.reelsedition.tileentity.TileEntityCrateAddon;
import net.minecraft.block.material.Material;
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

import static com.reelsedition.contents.registers.AddonBlocks.ALL_BLOCKS;
import static com.reelsedition.contents.registers.AddonBlocks.crate_addon;

@Mod(modid = reelsedition.MODID, name = reelsedition.NAME, version = reelsedition.VERSION, dependencies = "required-after:hbm")
public class reelsedition {

    public static final String MODID = "reelsedition";
    public static final String NAME = "Instagram Reels Edition";
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
        GameRegistry.registerTileEntity(TileEntityCrateAddon.class, "reelsedition:crate_addon");
        CyberneticEventHandler.registerCapability();



    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        AddonBlocks.init();
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

        //rad fentt
        registerFluidHazard(AddonFluids.FENT_RAD, 50d);
        registerFluidHazard(AddonFluids.FENT_SCHRAB, 350d);

        //droid spawn in crater
        addDroidSpawn(BiomeGenCraterBase.craterBiome);
        addDroidSpawn(BiomeGenCraterBase.craterInnerBiome);
        addDroidSpawn(BiomeGenCraterBase.craterOuterBiome);
    }

    private static void registerFluidHazard(com.hbm.inventory.fluid.FluidType ft, double rads) {
        try {
            net.minecraftforge.fluids.Fluid ff = ft.getFF();
            if (ff == null) return;
            String name = ff.getName();
            it.unimi.dsi.fastutil.objects.ObjectArrayList<com.hbm.hazard.HazardEntry> list =
                new it.unimi.dsi.fastutil.objects.ObjectArrayList<>();
            list.add(new com.hbm.hazard.HazardEntry(com.hbm.hazard.HazardRegistry.RADIATION, rads));
            com.hbm.hazard.transformer.HazardTransformerForgeFluid.FLUID_HAZARDS.put(name, list);
        } catch (Exception ex) {
            System.err.println("[G&G] Failed to register fluid hazard: " + ex.getMessage());
        }
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
