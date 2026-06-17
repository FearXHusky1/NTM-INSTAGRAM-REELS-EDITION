package com.reelsedition.contents.fluids;

//import com.reelsedition.contents.registers.AddonFluids;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.render.misc.EnumSymbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.function.Function;

import com.reelsedition.contents.AddonFluids;
import com.reelsedition.init.AddonFluidTraits;

public class AddonFluidType extends FluidType {
    public static int id = 0;
    // make it so copying traits doesn't override traits set priorly in AddonFluids
    public List<Class<? extends FluidTrait>> overrideTraits = new ArrayList<>();
    public Function<FluidTrait,Boolean> copyFunction = null;

    /// Regular constructor
    public AddonFluidType(String name,int color,int p,int f,int r,EnumSymbol symbol) {
        this(name,color,p,f,r,symbol,name);
    }

    /// Mimic constructor (Copies all traits of the base fluid)
    public AddonFluidType(String name,FluidType base) {
        this(name,base.getColor(),base.poison,base.flammability,base.reactivity,base.symbol,base.getName());
        temperature = base.temperature;
        copyFunction = (trait)->true;
        AddonFluidTraits.copyTraits.put(this,base);
    }

    /// Mimic constructor with selector (Tests each traits of the base fluid and copies if copyFunction returns true)
    public AddonFluidType(String name,FluidType base,Function<FluidTrait,Boolean> copyFunction) {
        this(name,base.getColor(),base.poison,base.flammability,base.reactivity,base.symbol,base.getName());
        temperature = base.temperature;
        //copyTraits(base,copyFunction);
        this.copyFunction = copyFunction;
        AddonFluidTraits.copyTraits.put(this,base);
    }

    /// Separate texture constructor
    public AddonFluidType(String name,int color,int p,int f,int r,EnumSymbol symbol,String texFluid) {
        super(name,color,p,f,r,symbol,texFluid.toLowerCase(Locale.US),0xFFFFFF,6000+(id++),null);
        AddonFluids.metaOrderPointer.add(this);
    }

    public void setDisplayName(String displayName) {
        try {
            java.lang.reflect.Field f = FluidType.class.getDeclaredField("localizedOverride");
            f.setAccessible(true);
            f.set(this, displayName);
        } catch (Exception ignored) {}
    }

    /// Copies traits of other fluid
    public void copyTraits(FluidType other,Function<FluidTrait,Boolean> copyFunction) {
        for (Entry<Class<? extends FluidTrait>,FluidTrait> entry : other.traits.entrySet()) {
            if (overrideTraits.contains(entry.getKey())) continue;
            if (copyFunction.apply(entry.getValue()))
                this.traits.put(entry.getKey(),entry.getValue());
        }
    }

    /// We override this so any added traits will not be overwritten by copyTraits
    @Override
    public FluidType addTraits(FluidTrait... traits) {
        for (FluidTrait trait : traits)
            overrideTraits.add(trait.getClass());
        return super.addTraits(traits);
    }
}


