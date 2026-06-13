package com.gommer.contents.registers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.gommer.contents.effects.CommunismEffect;
import com.gommer.reels;

@Mod.EventBusSubscriber(modid = reels.MODID)
public class RegistryHandler {

    public static final Item PHONE = new Item().setTranslationKey(reels.MODID + ".phone").setRegistryName("phone").setMaxStackSize(1);

    public static final Item FENT_POWDER = new ItemFood(0, 0, false) {{
        setTranslationKey(reels.MODID + ".fent_powder"); setRegistryName("fent_powder"); setAlwaysEdible();
    } @Override protected void onFoodEaten(ItemStack s, World w, EntityPlayer p) {
        if (!w.isRemote) p.attackEntityFrom(new DamageSource("fent_overdose") {
            @Override public ITextComponent getDeathMessage(net.minecraft.entity.EntityLivingBase v) { return new TextComponentString(v.getName() + " convulsed"); }
        }, 999999f);
    }};

    public static final Item WHITE_CREATURE = new ItemFood(0, 0, false) {{
        setTranslationKey(reels.MODID + ".white_creature"); setRegistryName("white_creature"); setAlwaysEdible(); setMaxStackSize(16);
    } @Override public EnumAction getItemUseAction(ItemStack s) { return EnumAction.DRINK; }
    @Override protected void onFoodEaten(ItemStack s, World w, EntityPlayer p) {
        if (w.isRemote) return;
        if (w.rand.nextInt(10) == 0) p.attackEntityFrom(new DamageSource("heart_attack") {
            @Override public ITextComponent getDeathMessage(net.minecraft.entity.EntityLivingBase v) { return new TextComponentString(v.getName() + " had a heart attack"); }
        }, 999999f);
        else { p.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 3600, 2)); p.addPotionEffect(new PotionEffect(MobEffects.SPEED, 3600, 9)); p.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3600, 1)); }
        w.playSound(null, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, .5f, 1f);
    }};

    public static final SoundEvent RUSIA = new SoundEvent(new ResourceLocation("gommer", "rusia")).setRegistryName(new ResourceLocation("gommer", "rusia"));

    public static final Item FENTRIFUGE_ELEMENT = new Item().setTranslationKey(reels.MODID + ".fentrifuge_element").setRegistryName("fentrifuge_element");

    @SubscribeEvent public static void registerBlocks(RegistryEvent.Register<Block> e) { for (Block b : AddonBlocks.ALL_BLOCKS) e.getRegistry().register(b); }
    @SubscribeEvent public static void registerItems(RegistryEvent.Register<Item> e) {
        for (Block b : AddonBlocks.ALL_BLOCKS) {
            ItemBlock ib = b == AddonBlocks.fent_reactor ? new ItemBlock(b) {
                @Override public void addInformation(ItemStack s, net.minecraft.world.World w, java.util.List<String> t, net.minecraft.client.util.ITooltipFlag f) {
                    t.add(TextFormatting.GRAY + "Converts pure fentanyl into HE through");
                    t.add(TextFormatting.GRAY + "the power of bullshit and centrifental force");
                }
            } : new ItemBlock(b);
            ib.setRegistryName(b.getRegistryName()); e.getRegistry().register(ib);
        }
        e.getRegistry().register(PHONE); e.getRegistry().register(FENT_POWDER); e.getRegistry().register(WHITE_CREATURE); e.getRegistry().register(FENTRIFUGE_ELEMENT);
    }
    @SubscribeEvent public static void registerPotions(RegistryEvent.Register<Potion> e) { e.getRegistry().register(CommunismEffect.INSTANCE); }
    @SubscribeEvent public static void registerSounds(RegistryEvent.Register<SoundEvent> e) { e.getRegistry().register(RUSIA); }
}
