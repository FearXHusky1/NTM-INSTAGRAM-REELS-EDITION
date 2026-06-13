package com.gommer.contents.effects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class CommunismEffect extends Potion {

    public static final CommunismEffect INSTANCE = new CommunismEffect();

    protected CommunismEffect() {
        super(false, 0xCC0000);
        setRegistryName(new ResourceLocation("reels", "communism"));
        setPotionName("effect.reels.communism");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.world.isRemote) return;
        entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 100, 255)); //realism
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
