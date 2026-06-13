package com.gommer.contents.recipes;

import com.gommer.contents.registers.RegistryHandler;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;

public class AddonRecipes {

    public static void register() {
        ChemicalPlantRecipes.INSTANCE.register( //monsta recipe
            new GenericRecipe("reels.white_creature")
                .setupNamed(400, 2000)
                .setNameWrapper("White Creature Production")
                .setIcon(RegistryHandler.WHITE_CREATURE)
                .inputItems(
                    new RecipesCommon.ComparableStack(ModItems.can_empty)
                )
                .inputFluids(
                    new FluidStack(Fluids.WATER, 3000),
                    new FluidStack(Fluids.DIESEL, 5000)
                )
                .outputItems(
                    new ItemStack(RegistryHandler.WHITE_CREATURE)
                )
        );

        AssemblyMachineRecipes.INSTANCE.register( //phone recipe
            new GenericRecipe("reels.phone")
                .setup(400, 250)
                .setIcon(RegistryHandler.PHONE)
                .inputItems(
                    new RecipesCommon.ComparableStack(ModItems.board_copper, 32),
                    new RecipesCommon.ComparableStack(ModItems.ingot_polymer, 8),
                    new RecipesCommon.ComparableStack(ModItems.ducttape, 32),
                    new RecipesCommon.ComparableStack(ModItems.battery_sc)
                )
                .outputItems(
                    new ItemStack(RegistryHandler.PHONE)
                )
        );
    }
}
