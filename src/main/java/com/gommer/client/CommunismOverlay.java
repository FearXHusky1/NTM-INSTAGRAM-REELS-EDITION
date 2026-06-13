package com.gommer.client;

import org.lwjgl.opengl.GL11;
import com.gommer.contents.effects.CommunismEffect;
import com.gommer.contents.registers.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class CommunismOverlay {

    private static final ResourceLocation FUNNY = new ResourceLocation("gommer", "textures/funny.png");
    private static final float MAX_A = 0.5f;
    private static int frames;
    private static ISound sound;
    private static PotionEffect stash;

    @SubscribeEvent public static void overlay(RenderGameOverlayEvent.Post e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) return;
        PotionEffect eff = mc.player.getActivePotionEffect(CommunismEffect.INSTANCE);
        if (eff == null) { stopSound(mc); frames = 0; return; }
        startSound(mc);
        float t = Math.min(1f, ++frames / 400f), a = MAX_A * t * t;
        if (a <= 0) return;
        ScaledResolution sr = new ScaledResolution(mc);
        mc.getTextureManager().bindTexture(FUNNY);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1, 1, 1, a);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder b = tess.getBuffer();
        b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        b.pos(0, sr.getScaledHeight(), -90).tex(0, 1).endVertex();
        b.pos(sr.getScaledWidth(), sr.getScaledHeight(), -90).tex(1, 1).endVertex();
        b.pos(sr.getScaledWidth(), 0, -90).tex(1, 0).endVertex();
        b.pos(0, 0, -90).tex(0, 0).endVertex();
        tess.draw();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @SubscribeEvent public static void hudPre(RenderGameOverlayEvent.Pre e)  { if (e.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS) strip(); }
    @SubscribeEvent public static void hudPost(RenderGameOverlayEvent.Post e) { if (e.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS) unstrip(); }
    @SubscribeEvent public static void guiPre(GuiScreenEvent.DrawScreenEvent.Pre e)   { if (e.getGui() instanceof GuiContainer) strip(); }
    @SubscribeEvent public static void guiPost(GuiScreenEvent.DrawScreenEvent.Post e) { if (e.getGui() instanceof GuiContainer) unstrip(); }

    private static void startSound(Minecraft mc) {
        if (sound == null || !mc.getSoundHandler().isSoundPlaying(sound)) {
            sound = new PositionedSoundRecord(RegistryHandler.RUSIA.getSoundName(), SoundCategory.MUSIC, 1, 1, true, 0, ISound.AttenuationType.NONE, 0, 0, 0);
            mc.getSoundHandler().playSound(sound);
        }
    }
    private static void stopSound(Minecraft mc) { if (sound != null) { mc.getSoundHandler().stopSound(sound); sound = null; } }

    private static void strip() {
        if (stash != null) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null || mc.player.getActivePotionEffect(CommunismEffect.INSTANCE) == null) return;
        stash = mc.player.getActivePotionMap().remove(MobEffects.HUNGER);
    }
    private static void unstrip() {
        if (stash == null) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null) { mc.player.getActivePotionMap().put(MobEffects.HUNGER, stash); stash = null; }
    }
}
