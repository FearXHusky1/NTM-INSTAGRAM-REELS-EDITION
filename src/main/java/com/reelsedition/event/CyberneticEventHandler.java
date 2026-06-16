package com.reelsedition.event;

import com.reelsedition.cybernetics.CyberneticProvider;
import com.reelsedition.cybernetics.CyberneticState;
import com.reelsedition.cybernetics.ICyberneticState;
import com.reelsedition.item.ItemCyberneticPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

@Mod.EventBusSubscriber(modid = "reelsedition")
public class CyberneticEventHandler {

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(ICyberneticState.class, new Capability.IStorage<ICyberneticState>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<ICyberneticState> capability, ICyberneticState instance, EnumFacing side) {
                return null; // Handled directly inside CyberneticProvider
            }
            @Override
            public void readNBT(Capability<ICyberneticState> capability, ICyberneticState instance, EnumFacing side, NBTBase nbt) {
                // Handled directly inside CyberneticProvider
            }
        }, new Callable<ICyberneticState>() {
            @Override
            public ICyberneticState call() {
                return new CyberneticState();
            }
        });
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(CyberneticProvider.KEY, new CyberneticProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer oldPlayer = event.getOriginal();
        EntityPlayer newPlayer = event.getEntityPlayer();

        ICyberneticState oldCap = oldPlayer.getCapability(CyberneticProvider.CYBER_CAP, null);
        ICyberneticState newCap = newPlayer.getCapability(CyberneticProvider.CYBER_CAP, null);

        if (oldCap != null && newCap != null) {
            newCap.setCybernetic(oldCap.isCybernetic());
            newCap.setFent(oldCap.getFent());
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        EntityPlayer player = event.player;
        ICyberneticState cyber = player.getCapability(CyberneticProvider.CYBER_CAP, null);

        if (cyber != null && cyber.isCybernetic()) {
            // Loop through all installed part slots
            for (ItemStack stack : cyber.getInstalledParts()) {
                if (!stack.isEmpty() && stack.getItem() instanceof ItemCyberneticPart) {
                    // Execute the item's special cyber capability tick
                    ((ItemCyberneticPart) stack.getItem()).onCyberTick(player, stack);
                }
            }
        }
    }
}
