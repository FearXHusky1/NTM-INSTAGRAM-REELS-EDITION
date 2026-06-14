package com.reelsedition.client;

import com.reelsedition.contents.registers.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class RegisterStuff {
    static { OBJLoader.INSTANCE.addDomain("reels`"); }

    @SubscribeEvent public static void regModels(ModelRegistryEvent e) {
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.PHONE, 0, new ModelResourceLocation("reels:phone_reelsedition", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.FENT_POWDER, 0, new ModelResourceLocation("reels:fent_powder", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.WHITE_CREATURE, 0, new ModelResourceLocation("reels:white_creature", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(AddonBlocks.fent_reactor), 0, new ModelResourceLocation("reelsedition:fent_reactor", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.FENTRIFUGE_ELEMENT, 0, new ModelResourceLocation("reels:fentrifuge_element", "inventory"));
    }
}
