package com.reelsedition.init.recipes;

import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.SolderingRecipes;
import com.hbm.inventory.recipes.SolderingRecipes.SolderingRecipe;
import com.hbm.items.ItemEnums.EnumCircuitType;
import com.hbm.items.ModItems;

import com.reelsedition.contents.AddonFluids;
import com.reelsedition.contents.registers.RegistryHandler;
import net.minecraft.item.ItemStack;

import java.util.List;

import static com.hbm.inventory.OreDictManager.*;

public class AddonSolderingRecipes {
    public static List<SolderingRecipe> recipes = SolderingRecipes.recipes;

    public static void register() {

        recipes.add(new SolderingRecipe(
                new ItemStack(RegistryHandler.PHONE),

                60,
                250,
                new FluidStack(Fluids.PEROXIDE, 250),
                new AStack[]{

                        new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CHIP),
                        new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CAPACITOR),
                        new OreDictStack(DIAMOND.dust())
                },
                new AStack[]{new ComparableStack(ModItems.circuit, 4, EnumCircuitType.PCB)},
                new AStack[]{new OreDictStack(PB.wireFine(), 4)}


        ));
        recipes.add(new SolderingRecipe(
                new ItemStack(RegistryHandler.ZION_CIRCUIT),

                350,
                5000,
                new FluidStack(AddonFluids.STEM_CELLS, 1000),
                new AStack[]{

                        new ComparableStack(ModItems.circuit, 16, EnumCircuitType.CHIP),
                        new ComparableStack(ModItems.circuit, 32, EnumCircuitType.CAPACITOR),
                        new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CHIP_BISMOID),
                },
                new AStack[]{
                        new ComparableStack(ModItems.circuit, 32, EnumCircuitType.PCB),
                        new ComparableStack(ModItems.ingot_pvc, 2)
                },
                new AStack[] {new ComparableStack(RegistryHandler.FENT_LACED_COPPER_WIRE, 12)}
        ));

    }
}
