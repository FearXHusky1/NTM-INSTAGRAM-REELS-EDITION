package com.reelsedition.contents.effects.vaccine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.reelsedition.contents.effects.vaccine.VaccinatedEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = "reelsedition")
public class VaccinatedEffect extends Potion {
    public static final VaccinatedEffect INSTANCE = new VaccinatedEffect();
    private static final ResourceLocation ICON = new ResourceLocation("reelsedition", "textures/gui/effect/vaccineeffect.png");

    // Foods that cannot be eaten while this effect is active.
    private static final Set<Item> BLOCKED_FOODS = new HashSet<>(Arrays.asList(
            Items.PORKCHOP,
            Items.COOKED_PORKCHOP,
            Items.BEEF,
            Items.COOKED_BEEF,
            Items.CHICKEN,
            Items.COOKED_CHICKEN,
            Items.MUTTON,
            Items.COOKED_MUTTON,
            Items.RABBIT,
            Items.COOKED_RABBIT,
            Items.RABBIT_STEW,
            Items.ROTTEN_FLESH,
            // Fish (each Item covers multiple subtypes via metadata: cod/salmon/clownfish/pufferfish)
            Items.FISH,
            Items.COOKED_FISH,
            // Dairy
            Items.MILK_BUCKET,
            Items.CAKE

    ));

    protected VaccinatedEffect() {
        super(false, 0xFFFFFF);
        setRegistryName(new ResourceLocation("reelsedition", "vaccine"));
        setPotionName("effect.reelsedition.vaccinated");
    }
    @Override
    public boolean hasStatusIcon() {
        return true;
    }
    @Override
    public void performEffect(EntityLivingBase e, int a) {
        PotionEffect initial = new PotionEffect(VaccinatedEffect.INSTANCE, Integer.MAX_VALUE, 0, false, false);
        initial.setPotionDurationMax(true);
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
            e.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 60, 4, false, false));
        }
    }

    @SubscribeEvent
    public static void onUseItemStart(LivingEntityUseItemEvent.Start event) {

        EntityLivingBase living = event.getEntityLiving();
        if (!living.isPotionActive(INSTANCE)) return;

        Item item = event.getItem().getItem();
        if (BLOCKED_FOODS.contains(item)) {
            event.setCanceled(true);
            if (!living.world.isRemote && living instanceof EntityPlayer) {
                ((EntityPlayer) living).sendStatusMessage(
                        new TextComponentString(TextFormatting.RED + "You are allergic to that!"), true);

            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(ICON);
        GlStateManager.color(1F, 1F, 1F, alpha);
        Gui.drawScaledCustomSizeModalRect(x + 3, y + 3, 0, 0, 72, 72, 18, 18, 72, 72);
        GlStateManager.color(1F, 1F, 1F, 1F);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(ICON);
        GlStateManager.color(1F, 1F, 1F, 1F);
        Gui.drawScaledCustomSizeModalRect(x + 6, y + 7, 0, 0, 72, 72, 18, 18, 72, 72);
        GlStateManager.color(1F, 1F, 1F, 1F);
    }
}