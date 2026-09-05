package com.reelsedition.contents.registers;

import com.hbm.items.block.ItemBlockStorageCrate;
import com.hbm.items.special.ItemModRecord;
import com.hbm.lib.HBMSoundHandler;
import com.reelsedition.contents.effects.vaccine.Vaccinated;
import com.reelsedition.contents.effects.vaccine.VaccinatedEffect;
import com.reelsedition.contents.registers.entity.Dresden;
import com.reelsedition.contents.registers.entity.Fauci;
import com.reelsedition.contents.registers.entity.YN;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.reelsedition.contents.effects.communism.CommunismEffect;
import com.reelsedition.contents.effects.lobotomy.LobotomisedEffect;
import com.reelsedition.contents.effects.lobotomy.EuphemiumLobotomy;
import com.reelsedition.reelsedition;
import com.reelsedition.contents.registers.entity.Droid;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.hbm.world.biome.BiomeGenCraterBase;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import com.reelsedition.contents.events.RaidData;

@Mod.EventBusSubscriber(modid = reelsedition.MODID)
public class RegistryHandler {
    public static final SoundEvent HHSound = new SoundEvent(new ResourceLocation("reelsedition", "hh")).setRegistryName(new ResourceLocation("reelsedition", "hh"));
    public static final Item HH = new ItemModRecord("HH",HHSound, "HH") {}.setCreativeTab(CreativeTabs.MISC);


    public static final Item PHONE = new Item().setTranslationKey(reelsedition.MODID + ".phone").setRegistryName("phone").setMaxStackSize(1);
    public static final Item VACCINE = new ItemFood(0, 0, false) {
        {
            setTranslationKey(reelsedition.MODID + ".vaccine");
            setRegistryName("vaccine");
            setAlwaysEdible();
        }
        @Override
        public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        @Override
        protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
            if (!worldIn.isRemote) {
                Vaccinated.mark(player);
                PotionEffect eff = new PotionEffect(VaccinatedEffect.INSTANCE, Integer.MAX_VALUE, 0, false, false);
                eff.setPotionDurationMax(true);
                eff.getCurativeItems().clear();
                player.addPotionEffect(eff);
            }
        }
    };



    public static final CreativeTabs TAB_REELS = new CreativeTabs("reelsedition") {
        @Override public ItemStack createIcon() { return new ItemStack(RegistryHandler.PHONE); }
        @Override public void displayAllRelevantItems(NonNullList<ItemStack> items) {
            for (Item item : Item.REGISTRY) {
                if (item.getRegistryName() != null && item.getRegistryName().getNamespace().equals("reelsedition")) {
                    items.add(new ItemStack(item));
                }
            }
        }
    };

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
    public static final Item BUG_WAFER = new ItemFood(1, 9f, false) {{ setTranslationKey(reelsedition.MODID + ".bug_wafer"); setRegistryName("bug_wafer"); setAlwaysEdible(); setMaxStackSize(64); }};

    public static final SoundEvent HWAA = new SoundEvent(new ResourceLocation("reelsedition", "hwaa")).setRegistryName(new ResourceLocation("reelsedition", "hwaa"));
    public static final SoundEvent HWAA_HIGH = new SoundEvent(new ResourceLocation("reelsedition", "hwaa_high")).setRegistryName(new ResourceLocation("reelsedition", "hwaa_high"));

    public static final Item FENT_LACED_COPPER_WIRE = new Item() {
        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add(TextFormatting.GRAY + "Covered in 99% Fentanyl!");
        }
    }.setTranslationKey(reelsedition.MODID + ".fent_laced_copper_wire").setRegistryName("fent_laced_copper_wire");

    public static final Item ZION_CIRCUIT = new Item() {
        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add(TextFormatting.GRAY + "The latest and greatest");
            tooltip.add(TextFormatting.GRAY + "from Tel-Aviv's top scientists.");
            tooltip.add(TextFormatting.UNDERLINE + "(Preinstalled with SystemD)");
        }
    }.setTranslationKey(reelsedition.MODID + ".ziontech_circuit").setRegistryName("ziontech_circuit").setMaxStackSize(64);
    public static final Item FLYOD_CIRCUIT = new Item() {

        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add(TextFormatting.GRAY + "'This is our only solution");
            tooltip.add(TextFormatting.GRAY + "to solving the fentanyl crisis");
            tooltip.add(TextFormatting.GRAY + "in the United States'");
            tooltip.add(TextFormatting.RED + "- Donald J. Trump");
        }
    }.setTranslationKey(reelsedition.MODID + ".floydtech_circuit").setRegistryName("floydtech_circuit").setMaxStackSize(64);
    public static final Item ORBITOCLAST = new Item() {{ setTranslationKey(reelsedition.MODID + ".orbitoclast"); setRegistryName("orbitoclast"); setMaxStackSize(1); setFull3D();
    } @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + "Quick DIY lobotomy, no medical license required :D");
        tooltip.add(TextFormatting.GRAY + "What could go wrong?");
    } @Override public EnumAction getItemUseAction(ItemStack stack) { return EnumAction.BOW; }
    @Override public int getMaxItemUseDuration(ItemStack stack) { return 72000; }
    @Override public boolean hitEntity(ItemStack s, EntityLivingBase target, EntityLivingBase attacker) {
        if (!attacker.world.isRemote) {
            PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
            eff.getCurativeItems().clear();
            target.addPotionEffect(eff);
            s.shrink(1);
        }
        bloodEffect(target);
        return true;
    } @Override public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer p, EnumHand h) {
        p.setActiveHand(h);
        return new ActionResult<>(EnumActionResult.SUCCESS, p.getHeldItem(h));
    } @Override public void onPlayerStoppedUsing(ItemStack stack, World w, EntityLivingBase entity, int timeLeft) {
        if (!w.isRemote && entity instanceof EntityPlayer) {
            if (this.getMaxItemUseDuration(stack) - timeLeft >= 20) {
                PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false);
                eff.getCurativeItems().clear();
                ((EntityPlayer) entity).addPotionEffect(eff);
                stack.shrink(1);
            }
        }
        if (entity instanceof EntityPlayer && this.getMaxItemUseDuration(stack) - timeLeft >= 20) {
            bloodEffect(entity);
        }
    }};

    public static final Item EUPHEMIUM_ORBITOCLAST = new Item() {{ setTranslationKey(reelsedition.MODID + ".euphemium_orbitoclast"); setRegistryName("euphemium_orbitoclast"); setMaxStackSize(1); setFull3D();
    } @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + "Euphemium-plated for a premium lobotomy!");
        tooltip.add(TextFormatting.RED + "Completely permanent, handle with care");
    } @Override public EnumAction getItemUseAction(ItemStack stack) { return EnumAction.BOW; }
    @Override public int getMaxItemUseDuration(ItemStack stack) { return 72000; }
    @Override public boolean hitEntity(ItemStack s, EntityLivingBase target, EntityLivingBase attacker) {
        if (!attacker.world.isRemote) {
            if (target instanceof EntityPlayer) EuphemiumLobotomy.mark((EntityPlayer) target);
            else { PotionEffect eff = new PotionEffect(LobotomisedEffect.INSTANCE, 32767, 0, false, false); eff.getCurativeItems().clear(); target.addPotionEffect(eff); }
            s.shrink(1);
        }
        bloodEffect(target);
        return true;
    } @Override public ActionResult<ItemStack> onItemRightClick(World w, EntityPlayer p, EnumHand h) {
        p.setActiveHand(h);
        return new ActionResult<>(EnumActionResult.SUCCESS, p.getHeldItem(h));
    } @Override public void onPlayerStoppedUsing(ItemStack stack, World w, EntityLivingBase entity, int timeLeft) {
        if (!w.isRemote && entity instanceof EntityPlayer) {
            if (this.getMaxItemUseDuration(stack) - timeLeft >= 20) {
                EuphemiumLobotomy.mark((EntityPlayer) entity);
                stack.shrink(1);
            }
        }
        if (entity instanceof EntityPlayer && this.getMaxItemUseDuration(stack) - timeLeft >= 20) {
            bloodEffect(entity);
        }
    }};



    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        System.out.println("Registering blocks: " + AddonBlocks.ALL_BLOCKS.size());
        for (Block b : AddonBlocks.ALL_BLOCKS) {
            System.out.println("Registering: " + b.getRegistryName());
            e.getRegistry().register(b);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        for (Block b : AddonBlocks.ALL_BLOCKS) {
            ItemBlock ib = new ItemBlock(b);
            if (b == AddonBlocks.fent_reactor) {
                ib = tooltipItemBlock(b,
                        "Converts pure fentanyl into HE through",
                        "the power of bullshit and centrifugal force"
                );
                ib.setRegistryName(b.getRegistryName());
            } else if (b == AddonBlocks.crate_addon) {
                ib = new ItemBlockStorageCrate(b, b.getRegistryName()); // already sets its own registry name
            } else {
                ib.setRegistryName(b.getRegistryName());
            }
            e.getRegistry().register(ib);
        }



        e.getRegistry().register(PHONE);
        e.getRegistry().register(FENT_POWDER);
        e.getRegistry().register(WHITE_CREATURE);
        e.getRegistry().register(FENTRIFUGE_ELEMENT);
        e.getRegistry().register(BUG);
        e.getRegistry().register(ORBITOCLAST);
        e.getRegistry().register(EUPHEMIUM_ORBITOCLAST);
        e.getRegistry().register(ZION_CIRCUIT);
        e.getRegistry().register(BUG_WAFER);
        e.getRegistry().register(FLYOD_CIRCUIT);
        e.getRegistry().register(FENT_LACED_COPPER_WIRE);
        e.getRegistry().register(MIXTAPE);
        ///e.getRegistry().register(FLAG);
        e.getRegistry().register(VACCINE);
    }

    private static ItemBlock tooltipItemBlock(Block b, String... lines) {
        return new ItemBlock(b) {
            @Override
            public void addInformation(ItemStack s, net.minecraft.world.World w, java.util.List<String> t, net.minecraft.client.util.ITooltipFlag f) {
                for (String line : lines) {
                    t.add(TextFormatting.GRAY + line);
                }
            }
        };
    }
    @SubscribeEvent public static void registerPotions(RegistryEvent.Register<Potion> e) { e.getRegistry().register(CommunismEffect.INSTANCE); e.getRegistry().register(LobotomisedEffect.INSTANCE); e.getRegistry().register(VaccinatedEffect.INSTANCE); }
    @SubscribeEvent public static void registerSounds(RegistryEvent.Register<SoundEvent> e) { e.getRegistry().register(RUSIA); e.getRegistry().register(HWAA); e.getRegistry().register(HWAA_HIGH); }


    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(Droid.class)
                        .id(new ResourceLocation("reelsedition", "droid"), 1)
                        .name("droid")
                        .tracker(80, 3, true)
                        .egg(0x1a1a2e, 0xff0000)
                        .spawn(EnumCreatureType.MONSTER, 1, 1, 1,
                                BiomeGenCraterBase.craterBiome,
                                BiomeGenCraterBase.craterInnerBiome,
                                BiomeGenCraterBase.craterOuterBiome
                        )
                        .build()
        );

        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(YN.class)
                        .id(new ResourceLocation("reelsedition", "yn"), 2)
                        .name("yn")
                        .tracker(80, 3, true)
                        .egg(0x1a1a3e, 0xff0000)
                        .spawn(EnumCreatureType.MONSTER, 1, 1, 1,
                                BiomeGenCraterBase.craterBiome,
                                BiomeGenCraterBase.craterInnerBiome,
                                BiomeGenCraterBase.craterOuterBiome
                        )
                        .build()
        );
        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(Dresden.class)
                        .id(new ResourceLocation("reelsedition", "dresden"), 3)
                        .name("dresden")
                        .tracker(80, 3, true)
                        .build()
        );
        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(Fauci.class)
                        .id(new ResourceLocation("reelsedition", "fauci_skin"), 2)
                        .name("dr.fauci")
                        .tracker(80, 3, true)
                        .egg(0x1a1a3e, 0xff0000)
                        .build()
        );
    }
    public static final Item MIXTAPE = new Item() {

        {
            setTranslationKey(reelsedition.MODID + ".mixtape");
            setRegistryName("mixtape");
            setMaxStackSize(1);
            setFull3D();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, @Nullable World worldIn,
                                   List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add(TextFormatting.GRAY +
                    "The latest and greatest hits from Dequan Oaheeks and Swichyion O'Block");
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(World world,
                                                        EntityPlayer player,
                                                        EnumHand hand) {

            ItemStack stack = player.getHeldItem(hand);

            if (!world.isRemote) {
                startRaid(world, player);
                    stack.shrink(1);
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
    };


    public static RaidData currentRaid;

    private static void startRaid(World world, EntityPlayer player) {

        currentRaid = new RaidData();

        Random rand = world.rand;

        for (int i = 0; i < 5; i++) {

            YN yn = new YN(world);

            double angle = rand.nextDouble() * Math.PI * 2;
            double dist = 10 + rand.nextDouble() * 10;

            double x = player.posX + Math.cos(angle) * dist;
            double z = player.posZ + Math.sin(angle) * dist;

            yn.setLocationAndAngles(
                    x,
                    player.posY,
                    z,
                    rand.nextFloat() * 360F,
                    0F
            );
            yn.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(yn)), null);
            world.spawnEntity(yn);

            currentRaid.addRaider(yn);
        }

        currentRaid.setInitialSize(currentRaid.getRaiders().size());

        if (player instanceof EntityPlayerMP) {
            currentRaid.addPlayer((EntityPlayerMP) player);
        }
    }

    private static void bloodEffect(EntityLivingBase entity) {
        World w = entity.world;
        if (!w.isRemote) {
            w.playSound(null, entity.posX, entity.posY, entity.posZ,
                    com.hbm.lib.HBMSoundHandler.blood_splat, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        for (int i = 0; i < 30; i++) {
            double dx = (w.rand.nextDouble() - 0.5) * 0.8;
            double dy = w.rand.nextDouble() * 0.5 + 0.3;
            double dz = (w.rand.nextDouble() - 0.5) * 0.8;
            w.spawnParticle(EnumParticleTypes.REDSTONE,
                    entity.posX + dx, entity.posY + entity.height * 0.6 + dy, entity.posZ + dz,
                    0.8, 0.0, 0.0);
        }
    }

}
