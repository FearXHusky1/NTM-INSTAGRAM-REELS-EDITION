package com.reelsedition.block;
import com.reelsedition.block.BlockBobbleAddon;
import com.hbm.main.MainRegistry;
import net.minecraft.block.Block;

public class ModBlocks {
    public static final Block bobblehead = new BlockBobbleAddon("bobblehead").setCreativeTab(MainRegistry.blockTab);
}
