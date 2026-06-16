package com.reelsedition.cybernetics;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface ICyberneticState {
    boolean isCybernetic();
    void setCybernetic(boolean active);

    int getFent();
    void setFent(int fent);
    void consumeFent(int amount);
    void receiveFent(int amount);
    int getMaxFent();

    NonNullList<ItemStack> getInstalledParts();
    void setPart(int slot, ItemStack stack);
    ItemStack getPart(int slot);
}
