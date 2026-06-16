package com.reelsedition.event;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.reelsedition.contents.registers.RegistryHandler.currentRaid;

@Mod.EventBusSubscriber(modid = "reelsedition")
public class RaidEventHandler {
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.world.isRemote) return;

        if (currentRaid != null) {
            currentRaid.update();
            if (currentRaid.isFinished()) {
                currentRaid.end();
                currentRaid = null;
            }
        }
    }
}
