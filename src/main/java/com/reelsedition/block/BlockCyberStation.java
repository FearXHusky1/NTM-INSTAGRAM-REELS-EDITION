package com.reelsedition.block;

import com.hbm.main.MainRegistry;
import com.reelsedition.tileentity.TileEntityCyberStation;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCyberStation extends BlockContainer {

    public BlockCyberStation() {
        super(Material.IRON);
        this.setRegistryName("cyber_station");
        this.setTranslationKey("reelsedition.cyber_station");
        this.setHardness(5.0F);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            // "ID_NUMBER" represents whatever arbitrary ID you assign to this GUI in your GuiHandler
            playerIn.openGui(MainRegistry.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCyberStation();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL; // Natively handles rendering regular 3D json block files
    }
}
