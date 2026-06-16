package com.reelsedition.contents.fluids;

import com.hbm.inventory.fluid.trait.*;


import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.fluid.trait.FluidTrait;
import net.minecraft.world.World;


public class AddonFluidTraits extends FluidTrait{

    private static void registerTrait(String name, Class<? extends FluidTrait> clazz) {
        traitNameMap.put(name, clazz);
        traitList.add(clazz);

    }
    public void addInfo(List<String> info) {
    }

    public void addInfoHidden(List<String> info) {
    }

    public void onFluidRelease(World world, int x, int y, int z, FluidTankNTM tank, int overflowAmount, FluidReleaseType type) {
    }

    public void serializeJSON(JsonWriter writer) throws IOException {
    }

    public void deserializeJSON(JsonObject obj) {
    }
static
{
    registerTrait("mega_poison", FT_Poison.class);
}


    }

