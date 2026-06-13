package com.gommer.render;

import com.gommer.contents.registers.entity.Droid;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDroid extends RenderBiped<Droid> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("gommer", "textures/entity/droid.png");

    public RenderDroid(RenderManager manager) {
        super(manager, new ModelBiped(0, 0, 64, 64), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Droid entity) {
        return TEXTURE;
    }
    }