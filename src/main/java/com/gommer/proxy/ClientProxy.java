package com.gommer.proxy;

import com.gommer.contents.registers.entity.Droid;
import com.gommer.render.RenderDroid;
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
    }
}