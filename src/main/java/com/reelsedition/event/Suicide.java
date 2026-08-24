package com.reelsedition.event;

import com.hbm.lib.ModDamageSource;
import com.reelsedition.contents.effects.lobotomy.LobotomisedEffect;
import com.reelsedition.contents.registers.RegistryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import com.reelsedition.contents.damage.FlagOveruseClass;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber
public class Suicide {

    private static final int MAX_TRANS = 200;

    // Tracks remaining ticks before lobotomy per player
    private static final Map<UUID, Integer> transMap = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.SERVER || event.phase != TickEvent.Phase.END) return;

        EntityPlayer player = event.player;
        boolean holdingFlag = false;

        for (EnumHand hand : EnumHand.values()) {
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty() && stack.getItem() == RegistryHandler.FLAG) {
                holdingFlag = true;
                break;
            }
        }

        UUID id = player.getUniqueID();

        if (holdingFlag) {
            int flagTime = transMap.getOrDefault(id, MAX_TRANS);
            flagTime--;
            if (flagTime <= 0) {
                player.attackEntityFrom(FlagOveruseClass.FLAG_OVERUSE, 999999.0F);// 4.0F = 2 hearts, adjust as needed
                flagTime = MAX_TRANS; // reset after triggering
            }
            transMap.put(id, flagTime);
        } else {
            transMap.put(id, MAX_TRANS);
        }
    }
}