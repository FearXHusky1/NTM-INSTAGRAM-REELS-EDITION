package com.reelsedition.contents.recipes;

import com.reelsedition.contents.registers.AddonBlocks;
import com.reelsedition.contents.registers.RegistryHandler;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.fluid.*;
import com.hbm.inventory.recipes.*;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AddonRecipes {
    public static void register() {
        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.white_creature")
            .setupNamed(400, 2000).setNameWrapper("White Creature Production")
            .setIcon(RegistryHandler.WHITE_CREATURE)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.can_empty))
            .inputFluids(new FluidStack(Fluids.WATER, 3000), new FluidStack(Fluids.DIESEL, 5000))
            .outputItems(new ItemStack(RegistryHandler.WHITE_CREATURE)));

        AssemblyMachineRecipes.INSTANCE.register(new GenericRecipe("reelsedition.phone")
            .setup(400, 250).setIcon(RegistryHandler.PHONE)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.board_copper, 32),
                new RecipesCommon.ComparableStack(ModItems.ingot_polymer, 8),
                new RecipesCommon.ComparableStack(ModItems.ducttape, 32),
                new RecipesCommon.ComparableStack(ModItems.battery_sc))
            .outputItems(new ItemStack(RegistryHandler.PHONE)));

        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.fent_powder")
            .setupNamed(600, 15000).setNameWrapper("Fentanyl Synthesis")
            .setIcon(RegistryHandler.FENT_POWDER)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.bottle_mercury, 8),
                new RecipesCommon.ComparableStack(ModItems.ingot_arsenic, 4),
                new RecipesCommon.ComparableStack(ModItems.ingot_iodine, 4))
            .inputFluids(new FluidStack(Fluids.WATER, 2000))
            .outputItems(new ItemStack(RegistryHandler.FENT_POWDER)));

        AssemblyMachineRecipes.INSTANCE.register(new GenericRecipe("reelsedition.fentrifuge_element")
            .setup(400, 5000).setIcon(RegistryHandler.FENTRIFUGE_ELEMENT)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.centrifuge_element, 1),
                new RecipesCommon.ComparableStack(ModItems.motor_desh, 1),
                new RecipesCommon.ComparableStack(ModItems.ingot_technetium, 4),
                new RecipesCommon.ComparableStack(ModItems.bottle_mercury, 4))
            .outputItems(new ItemStack(RegistryHandler.FENTRIFUGE_ELEMENT)));

        AssemblyMachineRecipes.INSTANCE.register(new GenericRecipe("reelsedition.fent_reactor")
            .setup(600, 100000).setIcon(Item.getItemFromBlock(AddonBlocks.fent_reactor))
            .inputItems(new RecipesCommon.ComparableStack(RegistryHandler.FENTRIFUGE_ELEMENT, 4),
                new RecipesCommon.ComparableStack(ModItems.plate_desh, 12),
                new RecipesCommon.ComparableStack(ModItems.ingot_tungsten, 8),
                new RecipesCommon.ComparableStack(ModItems.ingot_polymer, 8),
                new RecipesCommon.ComparableStack(ModItems.motor_desh, 2))
            .outputItems(new ItemStack(AddonBlocks.fent_reactor)));
    }
}
