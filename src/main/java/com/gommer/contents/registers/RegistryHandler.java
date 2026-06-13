package com.gommer.contents.registers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.gommer.reels;

@Mod.EventBusSubscriber(modid = reels.MODID)
public class RegistryHandler {

    public static final Item PHONE = new Item()
        .setTranslationKey(reels.MODID + ".phone")
        .setRegistryName("phone")
        .setMaxStackSize(1);

    public static final Item FENT_POWDER = new ItemFood(0, 0, false) {
        {
            setTranslationKey(reels.MODID + ".fent_powder");
            setRegistryName("fent_powder");
            setAlwaysEdible();
        }

        @Override
        protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
            if (!world.isRemote) {
                player.attackEntityFrom(
                    new DamageSource("fent_overdose") { // this shit is the funniest thing ever trust me
                        @Override
                        public net.minecraft.util.text.ITextComponent getDeathMessage(net.minecraft.entity.EntityLivingBase victim) {
                            return new TextComponentString(victim.getName() + " convulsed");
                        }
                    },
                    999999f
                );
            }
        }
    };

    public static final Item WHITE_CREATURE = new ItemFood(0, 0, false) {
        {
            setTranslationKey(reels.MODID + ".white_creature");
            setRegistryName("white_creature");
            setAlwaysEdible();
            setMaxStackSize(16);
        }

        @Override
        public EnumAction getItemUseAction(ItemStack stack) {
            return EnumAction.DRINK;
        }

        @Override
        protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
            if (!world.isRemote) {
                if (world.rand.nextInt(10) == 0) {
                    player.attackEntityFrom(
                        new DamageSource("heart_attack") {
                            @Override
                            public net.minecraft.util.text.ITextComponent getDeathMessage(net.minecraft.entity.EntityLivingBase victim) {
                                return new TextComponentString(victim.getName() + " had a heart attack"); //realism
                            }
                        },
                        999999f
                    );
                } else {
                    player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 3600, 2));
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 3600, 9));
                    player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 3600, 1));
                }
                world.playSound(null, player.posX, player.posY, player.posZ,
                    SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 0.5F, 1.0F);
            }
        }
    };

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        for (Block block : AddonBlocks.ALL_BLOCKS) {
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Block block : AddonBlocks.ALL_BLOCKS) {
            ItemBlock itemBlock = new ItemBlock(block);
            itemBlock.setRegistryName(block.getRegistryName());
            event.getRegistry().register(itemBlock);
        }
        event.getRegistry().register(PHONE);
        event.getRegistry().register(FENT_POWDER);
        event.getRegistry().register(WHITE_CREATURE);
    }
}
