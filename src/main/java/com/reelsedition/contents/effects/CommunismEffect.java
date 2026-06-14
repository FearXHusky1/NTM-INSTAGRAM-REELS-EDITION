package com.reelsedition.contents.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;

public class CommunismEffect extends Potion {

    public static final CommunismEffect INSTANCE = new CommunismEffect();

    protected CommunismEffect() {
        super(false, 0xCC0000);
        setRegistryName(new ResourceLocation("reels", "communism"));
        setPotionName("effect.reelsedition.communism");
    }

    @Override public void performEffect(EntityLivingBase e, int a) {
        if (!e.world.isRemote) e.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 100, 255));
    }
    @Override public boolean isReady(int d, int a) { return d % 20 == 0; }
}
