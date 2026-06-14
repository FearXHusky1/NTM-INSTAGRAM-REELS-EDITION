package com.reelsedition.contents.machine;

import com.reelsedition.contents.registers.*;
import com.hbm.blocks.ILookOverlay;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import java.util.*;

public class BlockFentReactor extends BlockContainer implements ILookOverlay {

    public BlockFentReactor(Material m, String n) {
        super(m);
        setTranslationKey(n);
        setRegistryName("reelsedition", n); // changed
        setCreativeTab(CreativeTabs.MISC);
        AddonBlocks.ALL_BLOCKS.add(this);
    }
    @Override public TileEntity createNewTileEntity(World w, int meta) { return new TileEntityFentReactor(); }

    @Override public boolean onBlockActivated(World w, BlockPos p, IBlockState s, EntityPlayer pl, EnumHand h, EnumFacing f, float x, float y, float z) {
        if (w.isRemote) return true;
        TileEntity t = w.getTileEntity(p);
        if (!(t instanceof TileEntityFentReactor)) return false;
        TileEntityFentReactor r = (TileEntityFentReactor) t;
        ItemStack held = pl.getHeldItem(h);
        if (held.getItem() == RegistryHandler.FENT_POWDER) {
            ItemStack slot = r.inventory.getStackInSlot(0);
            if (slot.isEmpty()) { r.inventory.setStackInSlot(0, held.splitStack(1)); r.markDirty(); w.notifyBlockUpdate(p, s, s, 3); return true; }
            if (slot.getItem() == RegistryHandler.FENT_POWDER && slot.getCount() < 64) { slot.grow(1); held.shrink(1); r.markDirty(); w.notifyBlockUpdate(p, s, s, 3); return true; }
            return false;
        }
        return false;
    }

    @Override public void printHook(RenderGameOverlayEvent.Pre e, World w, BlockPos p) {
        TileEntity t = w.getTileEntity(p);
        if (!(t instanceof TileEntityFentReactor)) return;
        TileEntityFentReactor r = (TileEntityFentReactor) t;
        int fuel = r.inventory.getStackInSlot(0).getCount();
        int secs = (r.burnTime + fuel * TileEntityFentReactor.maxBurnTime) / 20;
        boolean on = r.burnTime > 0;
        List<String> l = new ArrayList<>();
        l.add(TextFormatting.GREEN + "Burn: " + TextFormatting.WHITE + secs + "s" + (on ? TextFormatting.GREEN + "  10M HE/s" : ""));
        l.add(TextFormatting.GREEN + "Fuel: " + TextFormatting.WHITE + fuel + "x");
        ILookOverlay.printGeneric(e, "Fent Reactor", 0xFFCC00, 0x404040, l);
    }

    @Override public EnumBlockRenderType getRenderType(IBlockState s) { return EnumBlockRenderType.MODEL; }
    @Override public BlockRenderLayer getRenderLayer() { return BlockRenderLayer.CUTOUT; }
    @Override public boolean isOpaqueCube(IBlockState s) { return false; }
    @Override public boolean isFullCube(IBlockState s) { return false; }


}
