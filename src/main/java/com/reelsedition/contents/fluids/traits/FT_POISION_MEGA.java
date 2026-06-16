package com.reelsedition.contents.fluids.traits;


import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.inventory.fluid.trait.FT_Toxin;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.util.I18nUtil;
import net.minecraft.util.text.TextFormatting;
import com.hbm.handler.pollution.PollutionHandler;
import java.util.List;

public class FT_POISION_MEGA extends FluidTrait {


    float modifier;
    public FT_POISION_MEGA() { }
    public FT_POISION_MEGA(float modifier) {
        this.modifier = modifier;
    }
    @Override
    public void addInfoHidden(List<String> info) {
        info.add(TextFormatting.GOLD+"["+I18nUtil.resolveKey("test")+"]");
    }

}