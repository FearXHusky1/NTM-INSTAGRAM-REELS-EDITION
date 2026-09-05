package com.reelsedition.proxy;

import com.reelsedition.contents.registers.AddonBlocks;
import com.reelsedition.contents.registers.entity.Droid;
import com.reelsedition.contents.registers.entity.Dresden;
import com.reelsedition.contents.registers.entity.Fauci;
import com.reelsedition.contents.registers.entity.YN;
import com.reelsedition.render.RenderDresden;
import com.reelsedition.render.RenderDroid;
import com.hbm.items.weapon.sedna.factory.LegoClient;
import com.reelsedition.render.RenderFauci;
import com.reelsedition.render.RenderYN;
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

        RenderingRegistry.registerEntityRenderingHandler(
                Dresden.class,
                manager -> new RenderDresden(manager)
        );
        RenderingRegistry.registerEntityRenderingHandler(
                YN.class,
                manager -> new RenderYN(manager)
        );
        RenderingRegistry.registerEntityRenderingHandler(
                Fauci.class,
                manager -> new RenderFauci(manager)
        );
        Droid.DROID_LASER.setRendererBeam(LegoClient.RENDER_LASER_RED);

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(AddonBlocks.fent_reactor),
                0,
                new ModelResourceLocation("reelsedition:fent_reactor", "normal")
        );
    }
}