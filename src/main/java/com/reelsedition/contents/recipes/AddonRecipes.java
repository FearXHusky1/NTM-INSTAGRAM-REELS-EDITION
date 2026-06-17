package com.reelsedition.contents.recipes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.reelsedition.contents.AddonFluids;
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

        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.piperidone")
            .setupNamed(400, 15000).setNameWrapper("Piperidone Synthesis")
            .setIcon(RegistryHandler.FENT_POWDER)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.ingot_aluminium, 2))
            .inputFluids(new FluidStack(Fluids.NAPHTHA, 500),
                         new FluidStack(Fluids.HYDROGEN, 250),
                         new FluidStack(Fluids.NITRIC_ACID, 250))
            .outputFluids(new FluidStack(AddonFluids.PIPERIDONE, 250)));

        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.propionyl_chloride")
            .setupNamed(300, 10000).setNameWrapper("Propionyl Chloride Production")
            .setIcon(RegistryHandler.FENT_POWDER)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.ingot_phosphorus, 1))
            .inputFluids(new FluidStack(Fluids.ETHANOL, 250),
                         new FluidStack(Fluids.CHLORINE, 500))
            .outputFluids(new FluidStack(AddonFluids.PROPIONYL_CHLORIDE, 250)));

        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.fentanyl_crude")
            .setupNamed(400, 20000).setNameWrapper("Fentanyl Coupling")
            .setIcon(RegistryHandler.FENT_POWDER)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.powder_sodium, 4))
            .inputFluids(new FluidStack(AddonFluids.PIPERIDONE, 250),
                         new FluidStack(AddonFluids.PROPIONYL_CHLORIDE, 250))
            .outputFluids(new FluidStack(AddonFluids.FENTANYL_CRUDE, 200)));

        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.fent_powder")
            .setupNamed(600, 30000).setNameWrapper("Fentanyl Purification")
            .setIcon(RegistryHandler.FENT_POWDER)
            .inputItems(new RecipesCommon.ComparableStack(ModItems.bottle_mercury, 8),
                new RecipesCommon.ComparableStack(ModItems.ingot_arsenic, 4),
                new RecipesCommon.ComparableStack(ModItems.ingot_iodine, 4))
            .inputFluids(new FluidStack(AddonFluids.FENTANYL_CRUDE, 200),
                         new FluidStack(Fluids.XYLENE, 100),
                         new FluidStack(Fluids.SULFURIC_ACID, 100))
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

//YOU VILL EAT ZE BUGS
        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.bug_paste")
                .setupNamed(60, 15000).setNameWrapper("Bug Paste")
                .setIcon(RegistryHandler.FENT_POWDER)
                .inputItems(new RecipesCommon.ComparableStack(ModItems.glyphid_meat, 1))
                .inputFluids(new FluidStack(Fluids.WATER, 10))
                .outputFluids(new FluidStack(AddonFluids.BUG_PASTE, 100)));

        net.minecraftforge.fml.common.registry.GameRegistry.addShapedRecipe(
            new net.minecraft.util.ResourceLocation("reelsedition", "orbitoclast"),
            null,
            new ItemStack(RegistryHandler.ORBITOCLAST),
            "T", "I", "P",
            'T', ModItems.ingot_technetium,
            'I', ModItems.ingot_titanium,
            'P', ModItems.ingot_polymer);

        net.minecraftforge.fml.common.registry.GameRegistry.addShapedRecipe(
            new net.minecraft.util.ResourceLocation("reelsedition", "orbitoclast_euphemium"),
            null,
            new ItemStack(RegistryHandler.EUPHEMIUM_ORBITOCLAST),
            "E", "V", "P",
            'E', ModItems.ingot_euphemium,
            'V', ModItems.ingot_verticium,
            'P', ModItems.plate_euphemium);

        ChemicalPlantRecipes.INSTANCE.register(new GenericRecipe("reelsedition.bug_paste_2")
                .setupNamed(60, 15000).setNameWrapper("Bug Paste_2")
                .setIcon(RegistryHandler.BUG)
                .inputFluids(new FluidStack(AddonFluids.BUG_PASTE, 10))
                .outputItems(new ItemStack(RegistryHandler.BUG, 1)));


        ChemicalPlantRecipes.INSTANCE.register(
                new GenericRecipe("reelsedition.fent_laced_copper_wire")
                        .setupNamed(20, 200L)
                        .setNameWrapper("Fent-laced Copper Wire")
                        .setIcon(RegistryHandler.FENT_LACED_COPPER_WIRE)
                        .inputFluids(new FluidStack(AddonFluids.FENT, 50))

                        .inputItems(new RecipesCommon.ComparableStack(ModItems.wire_fine, 12, Mats.MAT_COPPER.id))
                        .outputItems(new ItemStack(RegistryHandler.FENT_LACED_COPPER_WIRE, 12))
        );

        //fent to liquid
        ChemicalPlantRecipes.INSTANCE.register(
                new GenericRecipe("reelsedition.fent_liquefy")
                        .setupNamed(200, 5000L)
                        .setNameWrapper("Fentanyl Dissolution")
                        .setIcon(RegistryHandler.FENT_POWDER)
                        .inputItems(new RecipesCommon.ComparableStack(RegistryHandler.FENT_POWDER, 1))
                        .inputFluids(new FluidStack(Fluids.WATER, 500))
                        .outputFluids(new FluidStack(AddonFluids.FENT, 200))
        );

        //stem cell
        ChemicalPlantRecipes.INSTANCE.register(
                new GenericRecipe("reelsedition.stem_cells")
                        .setupNamed(600, 20000L)
                        .setNameWrapper("Stem Cell Culture")
                        .setIcon(RegistryHandler.BUG)
                        .inputFluids(new FluidStack(AddonFluids.FENT, 100),
                                     new FluidStack(Fluids.BLOOD, 500),
                                     new FluidStack(AddonFluids.BUG_PASTE, 250))
                        .outputFluids(new FluidStack(AddonFluids.STEM_CELLS, 500))
        );

        //irrad fent
        OutgasserRecipes.recipes.put(
                new RecipesCommon.ComparableStack(ModItems.fluid_icon, 1, AddonFluids.FENT.getID()),
                new OutgasserRecipes.OutgasserRecipe(null, new FluidStack(AddonFluids.FENT_RAD, 1000))
        );

        //schrab fent
        ChemicalPlantRecipes.INSTANCE.register(
                new GenericRecipe("reelsedition.fent_schrabatic")
                        .setupNamed(1200, 100000L)
                        .setNameWrapper("Schrabatic Fentanyl Synthesis")
                        .setIcon(RegistryHandler.FENT_POWDER)
                        .inputFluids(new FluidStack(AddonFluids.FENT_RAD, 1000),
                                     new FluidStack(Fluids.SCHRABIDIC, 1000))
                        .outputFluids(new FluidStack(AddonFluids.FENT_SCHRAB, 500))
        );



    }




}
