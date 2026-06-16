package com.reelsedition.render;

import com.reelsedition.contents.registers.entity.YN;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderYN extends RenderBiped<YN> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("reelsedition", "textures/entity/yn.png");

    public RenderYN(RenderManager manager) {
        super(manager, new ModelBiped(0, 0, 64, 64), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(YN entity) {
        System.out.println("YN texture: " + TEXTURE);
        return TEXTURE;
    }
    @Override
    public void doRender(YN entity, double x, double y, double z, float entityYaw, float partialTicks) {
        ModelBiped model = (ModelBiped) this.mainModel;
        model.rightArmPose = entity.isSwingingArms() ? ModelBiped.ArmPose.BOW_AND_ARROW : ModelBiped.ArmPose.ITEM;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}