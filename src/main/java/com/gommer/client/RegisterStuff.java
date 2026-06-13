package com.gommer.client;

import com.gommer.contents.registers.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class RegisterStuff {
    @SubscribeEvent public static void regModels(ModelRegistryEvent e) {
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.PHONE, 0, new ModelResourceLocation("gommer:phone_reels", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.FENT_POWDER, 0, new ModelResourceLocation("gommer:fent_powder", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.WHITE_CREATURE, 0, new ModelResourceLocation("gommer:white_creature", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(AddonBlocks.fent_reactor), 0, new ModelResourceLocation("gommer:fent_reactor", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.FENTRIFUGE_ELEMENT, 0, new ModelResourceLocation("gommer:fentrifuge_element", "inventory"));
    }
}
