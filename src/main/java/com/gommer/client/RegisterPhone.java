package com.gommer.client;

import com.gommer.contents.registers.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

// phoen texture
@Mod.EventBusSubscriber(Side.CLIENT)
public class RegisterPhone {

    @SubscribeEvent
    public static void regModels(ModelRegistryEvent e) {
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.PHONE, 0,
                new ModelResourceLocation("gommer:phone_reels", "inventory"));
    }
}
