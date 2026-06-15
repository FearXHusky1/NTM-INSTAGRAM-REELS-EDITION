package com.reelsedition.contents.registers;

import com.reelsedition.contents.registers.entity.Dresden;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
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
import com.reelsedition.contents.effects.communism.CommunismEffect;
import com.reelsedition.contents.effects.lobotomy.LobotomisedEffect;
import com.reelsedition.contents.effects.lobotomy.EuphemiumLobotomy;
import com.reelsedition.reelsedition;
import com.reelsedition.contents.registers.entity.Droid;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = reelsedition.MODID)
public class RegistryHandler {

    public static final Item PHONE = new Item().setTranslationKey(reelsedition.MODID + ".phone").setRegistryName("phone").setMaxStackSize(1);

    public static final Item FENT_POWDER = new ItemFood(0, 0, false) {{
        setTranslationKey(reelsedition.MODID + ".fent_powder"); setRegistryName("fent_powder"); setAlwaysEdible();
    } @Override protected void onFoodEaten(ItemStack s, World w, EntityPlayer p) {
        if (!w.isRemote) p.attackEntityFrom(new DamageSource("fent_overdose") {
            @Override public ITextComponent getDeathMessage(net.minecraft.entity.EntityLivingBase v) { return new TextComponentString(v.getName() + " convulsed"); }
        }, 999999f);
    }};

    public static final Item WHITE_CREATURE = new ItemFood(0, 0, false) {{
        setTranslationKey(reelsedition.MODID + ".white_creature"); setRegistryName("white_creature"); setAlwaysEdible(); setMaxStackSize(16);
    } @Override public EnumAction getItemUseAction(ItemStack s) { return EnumAction.DRINK; }
    @Override protected void onFoodEaten(ItemStack s, World w, EntityPlayer p) {
        if (w.isRemote) return;
        if (w.rand.nextInt(10) == 0) p.attackEntityFrom(new DamageSource("heart_attack") {
            @Override public ITextComponent getDeathMessage(net.minecraft.entity.EntityLivingBase v) { return new TextComponentString(v.getName() + " had a heart attack"); }
        }, 999999f);
        else { p.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 3600, 2)); p.addPotionEffect(new PotionEffect(MobEffects.SPEED, 3600, 9)); p.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3600, 1)); }
        w.playSound(null, p.posX, p.posY, p.posZ, SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, .5f, 1f);
    }};

    public static final SoundEvent RUSIA = new SoundEvent(new ResourceLocation("reelsedition", "rusia")).setRegistryName(new ResourceLocation("reelsedition", "rusia"));

    public static final Item FENTRIFUGE_ELEMENT = new Item().setTranslationKey(reelsedition.MODID + ".fentrifuge_element").setRegistryName("fentrifuge_element");

    public static final Item BUG = new ItemFood(1, 4f, false) {{ setTranslationKey(reelsedition.MODID + ".bug"); setRegistryName("bug"); setAlwaysEdible(); setMaxStackSize(64); }};

    public static final SoundEvent HWAA = new SoundEvent(new ResourceLocation("reelsedition", "hwaa")).setRegistryName(new ResourceLocation("reelsedition", "hwaa"));
    public static final SoundEvent HWAA_HIGH = new SoundEvent(new ResourceLocation("reelsedition", "hwaa_high")).setRegistryName(new ResourceLocation("reelsedition", "hwaa_high"));

    public static final Item ORBITOCLAST = new Item() {{ setTranslationKey(reelsedition.MODID + ".orbitoclast"); setRegistryName("orbitoclast"); setMaxStackSize(1); setFull3D();
    } @Override public boolean hitEntity(ItemStack s, EntityLivingBase target, EntityLivingBase attacker) {
        if (!attacker.world.isRemote) {
            PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
            eff.getCurativeItems().clear();
            target.addPotionEffect(eff);
            s.shrink(1);
        }
        return true;
    } @Override public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer p, EnumHand h) {
        if (!w.isRemote) {
            PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
            eff.getCurativeItems().clear();
            p.addPotionEffect(eff);
            p.getHeldItem(h).shrink(1);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, p.getHeldItem(h));
    }};

    public static final Item EUPHEMIUM_ORBITOCLAST = new Item() {{ setTranslationKey(reelsedition.MODID + ".orbitoclast_euphemium"); setRegistryName("orbitoclast_euphemium"); setMaxStackSize(1); setFull3D();
    } @Override public boolean hitEntity(ItemStack s, EntityLivingBase target, EntityLivingBase attacker) {
        if (!attacker.world.isRemote) {
            if (target instanceof EntityPlayer) EuphemiumLobotomy.mark((EntityPlayer) target);
            else { PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false); eff.getCurativeItems().clear(); target.addPotionEffect(eff); }
            s.shrink(1);
        }
        return true;
    } @Override public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer p, EnumHand h) {
        if (!w.isRemote) { EuphemiumLobotomy.mark(p); p.getHeldItem(h).shrink(1); }
        return new ActionResult<>(EnumActionResult.SUCCESS, p.getHeldItem(h));
    }};

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        System.out.println("Registering blocks: " + AddonBlocks.ALL_BLOCKS.size());
        for (Block b : AddonBlocks.ALL_BLOCKS) {
            System.out.println("Registering: " + b.getRegistryName());
            e.getRegistry().register(b);
        }
    }

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
        e.getRegistry().register(PHONE); e.getRegistry().register(FENT_POWDER); e.getRegistry().register(WHITE_CREATURE); e.getRegistry().register(FENTRIFUGE_ELEMENT); e.getRegistry().register(BUG); e.getRegistry().register(ORBITOCLAST); e.getRegistry().register(EUPHEMIUM_ORBITOCLAST);
    }
    @SubscribeEvent public static void registerPotions(RegistryEvent.Register<Potion> e) { e.getRegistry().register(CommunismEffect.INSTANCE); e.getRegistry().register(LobotomisedEffect.INSTANCE); }
    @SubscribeEvent public static void registerSounds(RegistryEvent.Register<SoundEvent> e) { e.getRegistry().register(RUSIA); e.getRegistry().register(HWAA); e.getRegistry().register(HWAA_HIGH); }


    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(Droid.class)
                        .id(new ResourceLocation("reelsedition", "droid"), 1)
                        .name("droid")
                        .tracker(80, 3, true)
                        .build()
        );
        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(Dresden.class)
                        .id(new ResourceLocation("reelsedition", "dresden"), 1)
                        .name("dresden")
                        .tracker(80, 3, true)
                        .build()
        );
    }
}
