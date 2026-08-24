package com.reelsedition.tileentity;
import com.hbm.tileentity.machine.TileEntityCrate;
import net.minecraft.util.ResourceLocation;

public class TileEntityCrateAddon extends TileEntityCrate {
    public TileEntityCrateAddon() {

        super(
                72,                     // slotcount
                "colorCrate",           // name
                8,                      // crateColumns
                9,                      // crateRows
                8,                      // crateX
                18,                     // crateY
                8,                      // playerInventoryX
                140,                    // playerInventoryY
                198,                    // hotbarY
                176,                    // guiWidth
                222,                    // guiHeight
                8,                      // inventoryLabelX
                0x1C1C1C,               // titleColor
                0x1C1C1C,               // inventoryLabelColor
                new ResourceLocation("reelsedition", "textures/gui/gui_crate_addon.png") // gui texture
        );

    }
}