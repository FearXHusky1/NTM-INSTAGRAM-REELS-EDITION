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
    static { OBJLoader.INSTANCE.addDomain("reelsedition"); }

    @SubscribeEvent public static void regModels(ModelRegistryEvent e) {
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.PHONE, 0, new ModelResourceLocation("reelsedition:phone_reels", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.FENT_POWDER, 0, new ModelResourceLocation("reelsedition:fent_powder", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.WHITE_CREATURE, 0, new ModelResourceLocation("reelsedition:white_creature", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(AddonBlocks.fent_reactor), 0, new ModelResourceLocation("reelsedition:fent_reactor", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.FENTRIFUGE_ELEMENT, 0, new ModelResourceLocation("reelsedition:fentrifuge_element", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.BUG, 0, new ModelResourceLocation("reelsedition:bug", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.ORBITOCLAST, 0, new ModelResourceLocation("reelsedition:orbitoclast", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.EUPHEMIUM_ORBITOCLAST, 0, new ModelResourceLocation("reelsedition:orbitoclast_euphemium", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.BUG_WAFER, 0, new ModelResourceLocation("reelsedition:bug_wafer", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.ZION_CIRCUIT, 0, new ModelResourceLocation("reelsedition:ziontech_circuit", "inventory"));
        ModelLoader.setCustomModelResourceLocation(RegistryHandler.FLYOD_CIRCUIT, 0, new ModelResourceLocation("reelsedition:floydtech_circuit", "inventory"));
    }
}
