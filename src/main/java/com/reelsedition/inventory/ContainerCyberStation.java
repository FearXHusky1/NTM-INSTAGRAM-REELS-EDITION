package com.reelsedition.inventory;

import com.reelsedition.tileentity.TileEntityCyberStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCyberStation extends Container {

    private final TileEntityCyberStation tileEntity;

    public ContainerCyberStation(IInventory playerInventory, TileEntityCyberStation tileEntity) {
        this.tileEntity = tileEntity;

        // Slot 0: Center Modification target (e.g., Cybernetic Item)
        this.addSlotToContainer(new Slot(tileEntity, 0, 80, 35));

        // Slots 1-3: Surrounding upgrade modifier slots
        this.addSlotToContainer(new Slot(tileEntity, 1, 44, 35));
        this.addSlotToContainer(new Slot(tileEntity, 2, 80, 11));
        this.addSlotToContainer(new Slot(tileEntity, 3, 116, 35));

        // Player Inventory Slots (Standard Minecraft Layout)
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        // Player Hotbar
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 4) { // From Machine to Player
                if (!this.mergeItemStack(itemstack1, 4, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // From Player to Machine
                if (!this.mergeItemStack(itemstack1, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return itemstack;
    }
}
