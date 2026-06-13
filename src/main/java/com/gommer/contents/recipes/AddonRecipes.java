package com.gommer.contents.recipes;

import com.gommer.contents.registers.RegistryHandler;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.fluid.*;
import com.hbm.inventory.recipes.*;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import net.minecraft.item.ItemStack;

public class AddonRecipes {
    public static void register() {
        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reels.white_creature")
            .setupNamed(400, 2000).setNameWrapper("White Creature Production")
            .setIcon(RegistryHandler.WHITE_CREATURE)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.can_empty))
            .inputFluids(new FluidStack(Fluids.WATER, 3000), new FluidStack(Fluids.DIESEL, 5000))
            .outputItems(new ItemStack(RegistryHandler.WHITE_CREATURE)));

        AssemblyMachineRecipes.INSTANCE.register(new GenericRecipe("reels.phone")
            .setup(400, 250).setIcon(RegistryHandler.PHONE)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.board_copper, 32),
                new RecipesCommon.ComparableStack(ModItems.ingot_polymer, 8),
                new RecipesCommon.ComparableStack(ModItems.ducttape, 32),
                new RecipesCommon.ComparableStack(ModItems.battery_sc))
            .outputItems(new ItemStack(RegistryHandler.PHONE)));

        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reels.fent_powder")
            .setupNamed(600, 15000).setNameWrapper("Fentanyl Synthesis")
            .setIcon(RegistryHandler.FENT_POWDER)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.bottle_mercury, 8),
                new RecipesCommon.ComparableStack(ModItems.ingot_arsenic, 4),
                new RecipesCommon.ComparableStack(ModItems.ingot_iodine, 4))
            .inputFluids(new FluidStack(Fluids.WATER, 2000))
            .outputItems(new ItemStack(RegistryHandler.FENT_POWDER)));
    }
}
