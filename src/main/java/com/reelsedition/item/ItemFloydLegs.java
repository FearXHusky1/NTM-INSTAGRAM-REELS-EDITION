package com.reelsedition.item;

import com.reelsedition.cybernetics.CyberneticProvider;
import com.reelsedition.cybernetics.ICyberneticState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemFloydLegs extends ItemCyberneticPart {

    public ItemFloydLegs() {
        super(CyberSlot.LEGS);
        this.setRegistryName("floyd_legs");
        this.setTranslationKey("reelsedition.floyd_legs");
    }

    @Override
    public void onCyberTick(EntityPlayer player, ItemStack stack) {
        ICyberneticState cyber = player.getCapability(CyberneticProvider.CYBER_CAP, null);

        if (cyber != null && cyber.getFent() > 10) {
            if (player.distanceWalkedModified != player.prevDistanceWalkedModified) {
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10, 1, false, false));
                player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 10, 1, false, false));

                if (!player.world.isRemote) {
                    cyber.consumeFent(5);
                }
            }
        }
    }
}