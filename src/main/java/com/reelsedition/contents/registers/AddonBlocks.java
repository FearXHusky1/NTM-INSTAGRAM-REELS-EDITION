package com.reelsedition.contents.registers;

import com.reelsedition.contents.machine.BlockFentReactor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class AddonBlocks {
    public static final List<Block> ALL_BLOCKS = new ArrayList<>();

    public static Block fent_fluid_block;
    public static Block fent_reactor;

    public static void preInit() {
        fent_reactor = new BlockFentReactor(Material.IRON, "fent_reactor");
    }
}

