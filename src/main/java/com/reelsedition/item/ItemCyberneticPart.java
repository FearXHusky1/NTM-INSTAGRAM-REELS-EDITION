package com.reelsedition.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCyberneticPart extends Item {

    public enum CyberSlot { EYES, ARMS, LEGS }

    private final CyberSlot targetSlot;

    public ItemCyberneticPart(CyberSlot slot) {
        this.targetSlot = slot;
        this.setMaxStackSize(1); // Cybernetics don't stack
    }

    public CyberSlot getTargetSlot() {
        return this.targetSlot;
    }


    public void onCyberTick(EntityPlayer player, ItemStack stack) {
    }
}
