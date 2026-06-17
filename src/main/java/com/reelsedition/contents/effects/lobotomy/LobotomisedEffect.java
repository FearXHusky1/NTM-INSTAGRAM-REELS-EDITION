package com.reelsedition.contents.effects.lobotomy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LobotomisedEffect extends Potion {

    public static final LobotomisedEffect INSTANCE = new LobotomisedEffect();
    private static final ResourceLocation ICON = new ResourceLocation("reelsedition", "textures/items/brain.png");

    protected LobotomisedEffect() {
        super(false, 0xFFFFFF);
        setRegistryName(new ResourceLocation("reelsedition", "lobotomised"));
        setPotionName("effect.reelsedition.lobotomised");
    }

    @Override public boolean hasStatusIcon() { return true; }

    @Override public void performEffect(EntityLivingBase e, int a) {
        if (e.world.isRemote) return;
        PotionEffect eff = e.getActivePotionEffect(this);
        if (eff != null) {
            if (a < 2 && eff.getDuration() < 300) {
                PotionEffect fresh = new PotionEffect(this, 32767, 0, false, false);
                fresh.getCurativeItems().clear();
                e.addPotionEffect(fresh);
            }
            if (!eff.getCurativeItems().isEmpty()) eff.getCurativeItems().clear();
        }
        if (a >= 4) {
            e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 4, false, false));
        }
    }
    @Override public boolean isReady(int d, int a) { return a >= 2 ? d % 5 == 0 : d % 200 == 0; }

    @Override public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.getTextureManager().bindTexture(ICON);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder b = tess.getBuffer();
        b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        b.pos(x, y + 18, 0).tex(0, 1).endVertex();
        b.pos(x + 18, y + 18, 0).tex(1, 1).endVertex();
        b.pos(x + 18, y, 0).tex(1, 0).endVertex();
        b.pos(x, y, 0).tex(0, 0).endVertex();
        tess.draw();
        GlStateManager.disableBlend();
    }

    @Override public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1, 1, 1, alpha);
        mc.getTextureManager().bindTexture(ICON);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder b = tess.getBuffer();
        b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        b.pos(x, y + 18, 0).tex(0, 1).endVertex();
        b.pos(x + 18, y + 18, 0).tex(1, 1).endVertex();
        b.pos(x + 18, y, 0).tex(1, 0).endVertex();
        b.pos(x, y, 0).tex(0, 0).endVertex();
        tess.draw();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
    }
}
