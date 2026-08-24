package com.reelsedition.contents.registers;

import com.reelsedition.block.BlockBobbleAddon;
import com.reelsedition.contents.machine.BlockFentReactor;
import com.reelsedition.contents.machine.BlockStorageCrateAddon;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class AddonBlocks {

    public static final List<Block> ALL_BLOCKS = new ArrayList<>();

    public static BlockStorageCrateAddon crate_addon;
    public static Block fent_fluid_block;
    public static Block fent_reactor;

    public static BlockBobbleAddon bobblehead;


    public static void preInit() {

        fent_reactor =
                new BlockFentReactor(
                        Material.IRON,
                        "fent_reactor"
                );

        // Constructor already does ALL_BLOCKS for fent reactor


        crate_addon =
                new BlockStorageCrateAddon(
                        Material.IRON,
                        "crate_addon"
                );

        ALL_BLOCKS.add(crate_addon);


        bobblehead =
                new BlockBobbleAddon("bobblehead");

        ALL_BLOCKS.add(bobblehead);
        GameRegistry.registerTileEntity(
                BlockBobbleAddon.TileEntityBobble.class,
                "bobblehead"
        );
    }


    public static void init() {

    }
}