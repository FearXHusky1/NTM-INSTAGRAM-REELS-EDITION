package com.reelsedition.proxy;

import com.reelsedition.contents.registers.AddonBlocks;
import com.reelsedition.contents.registers.entity.Droid;
import com.reelsedition.render.RenderDroid;
import com.hbm.items.weapon.sedna.factory.LegoClient;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        RenderingRegistry.registerEntityRenderingHandler(
                Droid.class,
                manager -> new RenderDroid(manager)
        );

        Droid.DROID_LASER.setRendererBeam(LegoClient.RENDER_LASER_RED);

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(AddonBlocks.fent_reactor),
                0,
                new ModelResourceLocation("reelsedition:fent_reactor", "normal")
        );
    }
}