package com.reelsedition.event;

import com.reelsedition.contents.effects.lobotomy.LobotomisedEffect;
import com.reelsedition.contents.registers.RegistryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber
public class Brainrot {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side != Side.SERVER || event.phase != TickEvent.Phase.END) return;

        EntityPlayer player = event.player;
        boolean holdingPhone = false;

        for (EnumHand hand : EnumHand.values()) {
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty() && stack.getItem() == RegistryHandler.PHONE) {
                holdingPhone = true;
                break;
            }
        }

        if (holdingPhone) {
            PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 40, 4, false, false);
            eff.getCurativeItems().clear();
            player.addPotionEffect(eff);
        }
    }
}
