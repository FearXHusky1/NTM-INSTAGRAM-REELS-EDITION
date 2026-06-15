package com.reelsedition.contents.effects.lobotomy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.*;

@Mod.EventBusSubscriber
public class EuphemiumLobotomy {

    private static final Map<UUID, Boolean> PERMANENT = new HashMap<>();

    public static void mark(EntityPlayer p) {
        PERMANENT.put(p.getUniqueID(), true);
    }

    @SubscribeEvent public static void onClone(PlayerEvent.Clone e) {
        if (PERMANENT.containsKey(e.getOriginal().getUniqueID())) {
            PERMANENT.put(e.getEntityPlayer().getUniqueID(), true);
        }
    }

    @SubscribeEvent public static void onJoin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof EntityPlayer && !e.getWorld().isRemote) {
            EntityPlayer p = (EntityPlayer) e.getEntity();
            if (PERMANENT.getOrDefault(p.getUniqueID(), false) && p.getActivePotionEffect(LobotomisedEffect.INSTANCE) == null) {
                PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
                eff.getCurativeItems().clear();
                p.addPotionEffect(eff);
            }
        }
    }

    @SubscribeEvent public static void onTick(LivingEvent.LivingUpdateEvent e) {
        if (e.getEntityLiving() instanceof EntityPlayer && !e.getEntityLiving().world.isRemote) {
            EntityPlayer p = (EntityPlayer) e.getEntityLiving();
            if (PERMANENT.getOrDefault(p.getUniqueID(), false) && p.getActivePotionEffect(LobotomisedEffect.INSTANCE) == null) {
                PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
                eff.getCurativeItems().clear();
                p.addPotionEffect(eff);
            }
        }
    }
}
