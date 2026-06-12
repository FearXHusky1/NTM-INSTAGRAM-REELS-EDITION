package com.gommer.contents.registers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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
                    Float.MAX_VALUE
                );
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
    }
}
