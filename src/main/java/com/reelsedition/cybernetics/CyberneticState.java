package com.reelsedition.cybernetics;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class CyberneticState implements ICyberneticState {
    private boolean isCybernetic = false;
    private int energy = 0;
    private final int maxEnergy = 50000;
    private final NonNullList<ItemStack> installedParts = NonNullList.withSize(3, ItemStack.EMPTY);

    @Override
    public boolean isCybernetic() { return this.isCybernetic; }

    @Override
    public void setCybernetic(boolean active) { this.isCybernetic = active; }

    @Override
    public int getFent() { return this.energy; }

    @Override
    public void setFent(int energy) {
        this.energy = Math.max(0, Math.min(energy, maxEnergy));
    }

    @Override
    public void consumeFent(int amount) {
        this.energy = Math.max(0, this.energy - amount);
    }

    @Override
    public void receiveFent(int amount) {
        this.energy = Math.min(this.maxEnergy, this.energy + amount);
    }

    @Override
    public int getMaxFent() { return this.maxEnergy; }


    @Override
    public NonNullList<ItemStack> getInstalledParts() { return this.installedParts; }

    @Override
    public void setPart(int slot, ItemStack stack) { this.installedParts.set(slot, stack); }

    @Override
    public ItemStack getPart(int slot) { return this.installedParts.get(slot); }
}