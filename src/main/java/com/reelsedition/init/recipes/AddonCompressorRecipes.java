package com.reelsedition.init.recipes;

import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.CompressorRecipes;
import com.hbm.inventory.recipes.CompressorRecipes.CompressorRecipe;
import com.hbm.util.Tuple.Pair;


import java.util.HashMap;

public class AddonCompressorRecipes {
    public static HashMap<Pair<FluidType, Integer>,CompressorRecipe> recipePtr = CompressorRecipes.recipes;
    public static void register() {
        recipePtr.put(new Pair<>(Fluids.WATER, 0), new CompressorRecipe(1_000, new FluidStack(Fluids.PEROXIDE, 1_000, 1), 50));

    }
}
