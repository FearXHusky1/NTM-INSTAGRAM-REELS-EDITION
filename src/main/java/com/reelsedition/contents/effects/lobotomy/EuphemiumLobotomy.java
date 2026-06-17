package com.reelsedition.contents.effects.lobotomy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EuphemiumLobotomy {

    private static final String TAG = "reelsedition_lobotomy";

    public static void mark(EntityPlayer p) {
        p.getEntityData().setBoolean(TAG, true);
    }

    public static boolean isMarked(EntityPlayer p) {
        return p.getEntityData().getBoolean(TAG);
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone e) {
        if (e.getOriginal().getEntityData().getBoolean(TAG)) {
            e.getEntityPlayer().getEntityData().setBoolean(TAG, true);
        }
    }

    @SubscribeEvent
    public static void onJoin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof EntityPlayer && !e.getWorld().isRemote) {
            EntityPlayer p = (EntityPlayer) e.getEntity();
            if (p.getEntityData().getBoolean(TAG) && p.getActivePotionEffect(LobotomisedEffect.INSTANCE) == null) {
                PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
                eff.getCurativeItems().clear();
                p.addPotionEffect(eff);
            }
        }
    }

    @SubscribeEvent
    public static void onTick(LivingEvent.LivingUpdateEvent e) {
        if (e.getEntityLiving() instanceof EntityPlayer && !e.getEntityLiving().world.isRemote) {
            EntityPlayer p = (EntityPlayer) e.getEntityLiving();
            if (p.getEntityData().getBoolean(TAG) && p.getActivePotionEffect(LobotomisedEffect.INSTANCE) == null) {
                PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
                eff.getCurativeItems().clear();
                p.addPotionEffect(eff);
            }
        }
    }
}
