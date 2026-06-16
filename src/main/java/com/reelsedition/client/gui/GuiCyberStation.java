package com.reelsedition.client.gui;

import com.reelsedition.inventory.ContainerCyberStation;
import com.reelsedition.tileentity.TileEntityCyberStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCyberStation extends GuiContainer {

    // Make a 256x256 texture mimicking HBM's industrial UI grid layout
    private static final ResourceLocation TEXTURE = new ResourceLocation("reelsedition", "textures/gui/cyber_station.png");
    private final InventoryPlayer playerInv;
    private final TileEntityCyberStation tileEntity;

    public GuiCyberStation(InventoryPlayer playerInv, TileEntityCyberStation tileEntity) {
        super(new ContainerCyberStation(playerInv, tileEntity));
        this.playerInv = playerInv;
        this.tileEntity = tileEntity;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = "Cybernetic Station";
        this.fontRenderer.drawString(title, this.xSize / 2 - this.fontRenderer.getStringWidth(title) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
}