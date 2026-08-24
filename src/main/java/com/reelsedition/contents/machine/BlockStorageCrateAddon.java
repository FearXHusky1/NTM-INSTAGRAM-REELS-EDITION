package com.reelsedition.contents.machine;

import com.hbm.blocks.generic.BlockStorageCrate;
import com.reelsedition.tileentity.TileEntityCrateAddon;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import org.jetbrains.annotations.NotNull;

public class BlockStorageCrateAddon extends BlockStorageCrate {
    public BlockStorageCrateAddon(Material materialIn, String s) {
        super(materialIn, s);
    }

    @Override
    public TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TileEntityCrateAddon();
    }

    @Override
    public int getSlots() {
        return 72;
    }
}