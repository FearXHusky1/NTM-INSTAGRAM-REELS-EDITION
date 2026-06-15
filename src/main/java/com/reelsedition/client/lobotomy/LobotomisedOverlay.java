package com.reelsedition.client.lobotomy;

import com.reelsedition.contents.effects.lobotomy.LobotomisedEffect;
import com.reelsedition.contents.registers.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;
import java.util.Random;

@Mod.EventBusSubscriber(Side.CLIENT)
public class LobotomisedOverlay { //this was not as simple as i thought it was going to be chatgpt my beloved

    private static final Random RNG = new Random();
    private static final int FLASH = 60, SHAKE = 240, FIRST_FLASH = 600;
    private static int ft, st, fmax;
    private static long last;
    private static float yaw, pitch;
    private static boolean wasActive;

    @SubscribeEvent public static void tick(RenderGameOverlayEvent.Post e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) { wasActive = false; return; }

        boolean active = mc.player.getActivePotionEffect(LobotomisedEffect.INSTANCE) != null;
        if (!active) { ft = st = 0; wasActive = false; return; }

        if (!wasActive) {
            ft = fmax = FIRST_FLASH; st = SHAKE;
            yaw = mc.player.rotationYaw; pitch = -90f;
            mc.getSoundHandler().playSound(new PositionedSoundRecord(
                RegistryHandler.HWAA_HIGH.getSoundName(), SoundCategory.MASTER, 1, 1, false, 0,
                ISound.AttenuationType.NONE, 0, 0, 0));
        } else {
            long now = System.currentTimeMillis();
            if (now - last >= 10000 && (last = now) > 0 && RNG.nextFloat() < 0.4f) {
                ft = fmax = FLASH; st = SHAKE;
                yaw = mc.player.rotationYaw + RNG.nextFloat() * 360f - 180f;
                pitch = mc.player.rotationPitch + RNG.nextFloat() * 180f - 90f;
                mc.getSoundHandler().playSound(new PositionedSoundRecord(
                    RegistryHandler.HWAA.getSoundName(), SoundCategory.MASTER, 1, 1, false, 0,
                    ISound.AttenuationType.NONE, 0, 0, 0));
            }
        }
        wasActive = true;

        if (st > 0) {
            st--;
            mc.player.rotationYaw = yaw + (RNG.nextFloat() - 0.5f) * 30f;
            mc.player.rotationPitch = pitch + (RNG.nextFloat() - 0.5f) * 20f;
        }

        if (ft <= 0) return;
        ft--;
        float a = (float) ft / fmax;
        ScaledResolution sr = new ScaledResolution(mc);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1, 1, 1, a * 0.6f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder b = tess.getBuffer();
        b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        b.pos(0, sr.getScaledHeight(), -90).endVertex();
        b.pos(sr.getScaledWidth(), sr.getScaledHeight(), -90).endVertex();
        b.pos(sr.getScaledWidth(), 0, -90).endVertex();
        b.pos(0, 0, -90).endVertex();
        tess.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
    }
}
