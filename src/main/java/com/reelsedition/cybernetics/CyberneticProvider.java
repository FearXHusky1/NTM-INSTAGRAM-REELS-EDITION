package com.reelsedition.cybernetics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CyberneticProvider implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(ICyberneticState.class)
    public static Capability<ICyberneticState> CYBER_CAP = null;

    private final ICyberneticState instance = CYBER_CAP.getDefaultInstance();
    public static final ResourceLocation KEY = new ResourceLocation("reelsedition", "cybernetic");

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CYBER_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CYBER_CAP ? CYBER_CAP.cast(this.instance) : null;
    }



    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setBoolean("isCybernetic", instance.isCybernetic());
        nbt.setInteger("cyberFent", instance.getFent());
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < instance.getInstalledParts().size(); i++) {
            ItemStack stack = instance.getPart(i);
            if (!stack.isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", i);
                stack.writeToNBT(itemTag);
                itemList.appendTag(itemTag);
            }
        }
        return nbt;


    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        instance.setCybernetic(nbt.getBoolean("isCybernetic"));
        instance.setFent(nbt.getInteger("cyberFent"));
        NBTTagList itemList = nbt.getTagList("InstalledParts", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < itemList.tagCount(); i++) {
            NBTTagCompound itemTag = itemList.getCompoundTagAt(i);
            int slot = itemTag.getInteger("Slot");
            if (slot >= 0 && slot < instance.getInstalledParts().size()) {
                instance.setPart(slot, new ItemStack(itemTag));
            }
        }
    }


}

