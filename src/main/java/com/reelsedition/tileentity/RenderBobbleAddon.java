package com.reelsedition.tileentity;

import com.hbm.Tags;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.skinlayer.BobbleSkinModel;
import com.hbm.render.skinlayer.MojangSkinLoader;
import com.hbm.render.tileentity.IItemRendererProvider;
import com.hbm.util.RenderUtil;
import com.reelsedition.block.BlockBobbleAddon;
import com.reelsedition.block.BlockBobbleAddon.BobbleType;
import com.reelsedition.contents.registers.AddonBlocks;
import com.reelsedition.reelsedition;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import java.util.Map;
import java.util.UUID;


public class RenderBobbleAddon
        extends TileEntitySpecialRenderer<BlockBobbleAddon.TileEntityBobble>
        implements IItemRendererProvider {

    public static final RenderBobbleAddon instance = new RenderBobbleAddon();

    public static final ResourceLocation socket =
            new ResourceLocation(Tags.MODID, "textures/models/trinkets/socket.png");

    public static final ResourceLocation glow =
            new ResourceLocation(Tags.MODID, "textures/models/trinkets/glow.png");

    public static final ResourceLocation lamp =
            new ResourceLocation(Tags.MODID, "textures/blocks/fluorescent_lamp.png");

    public static final ResourceLocation bobble_norwood =
            new ResourceLocation(reelsedition.MODID, "textures/models/trinkets/norwood.png");

    private final Map<UUID, BobbleSkinModel> skinModelCache =
            new Object2ObjectOpenHashMap<>();

    private long time;

    @Override
    public void render(
            BlockBobbleAddon.TileEntityBobble te,
            double x,
            double y,
            double z,
            float partialTicks,
            int destroyStage,
            float alpha
    ) {
        time = System.currentTimeMillis();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y, z + 0.5);

        double scale = 0.25D;
        GlStateManager.scale(scale, scale, scale);

        BobbleType type = te.type;
        int rot = te.getBlockMetadata();

        GlStateManager.rotate(22.5F * rot + 90F, 0F, -1F, 0F);

        renderBobble(type);

        GlStateManager.popMatrix();
    }

    public void renderBobble(BobbleType type) {
        RenderUtil.pushAllAttribs();

        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();

        bindTexture(socket);
        ResourceManager.bobble.renderPart("Socket");

        if (type.skinUuid != null) {
            MojangSkinLoader.Result result = MojangSkinLoader.get(type.skinUuid);

            BobbleSkinModel model;
            ResourceLocation tex;

            if (result != null) {
                model = skinModelCache.get(type.skinUuid);

                if (model == null) {
                    model = new BobbleSkinModel(result.image);
                    skinModelCache.put(type.skinUuid, model);
                }

                tex = result.texture;
            } else {
                model = BobbleSkinModel.gray();
                tex = BobbleSkinModel.grayTexture();
            }

            bindTexture(tex);
            renderSkinGuy(type, model);
        } else {
            switch (type) {
                case NORWOOD:
                    bindTexture(bobble_norwood);
                    break;
                default:
                    bindTexture(ResourceManager.universal);
                    break;
            }

            renderGuy(type);
        }

        GlStateManager.pushMatrix();
        renderPost(type);
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        renderSocket(type);
        GlStateManager.popMatrix();

        RenderUtil.popAttrib();
    }

    /* RENDER STANDARD PLAYER MODEL */

    public static double[] rotLeftArm = {0, 0, 0};
    public static double[] rotRightArm = {0, 0, 0};
    public static double[] rotLeftLeg = {0, 0, 0};
    public static double[] rotRightLeg = {0, 0, 0};
    public static double rotBody = 0;
    public static double[] rotHead = {0, 0, 0};

    public void resetFigurineRotation() {
        rotLeftArm = new double[]{0, 0, 0};
        rotRightArm = new double[]{0, 0, 0};
        rotLeftLeg = new double[]{0, 0, 0};
        rotRightLeg = new double[]{0, 0, 0};
        rotBody = 0;
        rotHead = new double[]{0, 0, 0};
    }

    public void setupFigurineRotation(BobbleType type) {
        switch (type) {
            case NORWOOD:
                rotLeftArm = new double[]{0, 80, 90};
                rotRightArm = new double[]{0, -80, 90};
                break;
            default:
                break;
        }
    }

    public void renderGuy(BobbleType type) {
        resetFigurineRotation();
        setupFigurineRotation(type);

        GlStateManager.pushMatrix();
        GlStateManager.rotate((float) rotBody, 0, 1, 0);

        GlStateManager.disableCull();

        String suffix = type.skinLayers ? "" : "17";

        GlStateManager.enableBlend();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
        GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(1F, 1F, 1F, 1F);

        // LEFT LEG
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 1, -0.125);
        GlStateManager.rotate((float) rotLeftLeg[0], 1, 0, 0);
        GlStateManager.rotate((float) rotLeftLeg[1], 0, 1, 0);
        GlStateManager.rotate((float) rotLeftLeg[2], 0, 0, 1);
        GlStateManager.translate(0, -1, 0.125);
        ResourceManager.bobble.renderPart("LL" + suffix);
        GlStateManager.popMatrix();

        // RIGHT LEG
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 1, 0.125);
        GlStateManager.rotate((float) rotRightLeg[0], 1, 0, 0);
        GlStateManager.rotate((float) rotRightLeg[1], 0, 1, 0);
        GlStateManager.rotate((float) rotRightLeg[2], 0, 0, 1);
        GlStateManager.translate(0, -1, -0.125);
        ResourceManager.bobble.renderPart("RL" + suffix);
        GlStateManager.popMatrix();

        // LEFT ARM
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 1.625, -0.25);
        GlStateManager.rotate((float) rotLeftArm[0], 1, 0, 0);
        GlStateManager.rotate((float) rotLeftArm[1], 0, 1, 0);
        GlStateManager.rotate((float) rotLeftArm[2], 0, 0, 1);
        GlStateManager.translate(0, -1.625, 0.25);
        ResourceManager.bobble.renderPart("LA" + suffix);
        GlStateManager.popMatrix();

        // RIGHT ARM
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 1.625, 0.25);
        GlStateManager.rotate((float) rotRightArm[0], 1, 0, 0);
        GlStateManager.rotate((float) rotRightArm[1], 0, 1, 0);
        GlStateManager.rotate((float) rotRightArm[2], 0, 0, 1);
        GlStateManager.translate(0, -1.625, -0.25);
        ResourceManager.bobble.renderPart("RA" + suffix);
        GlStateManager.popMatrix();

        // BODY
        ResourceManager.bobble.renderPart("Body" + suffix);

        // HEAD (light bobble)
        double speed = 0.005;
        double amplitude = 1;

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 1.75, 0);
        GlStateManager.rotate((float) (Math.sin(time * speed) * amplitude), 1, 0, 0);
        GlStateManager.rotate(
                (float) (Math.sin(time * speed + (Math.PI * 0.5)) * amplitude), 0, 0, 1);

        GlStateManager.rotate((float) rotHead[0], 1, 0, 0);
        GlStateManager.rotate((float) rotHead[1], 0, 1, 0);
        GlStateManager.rotate((float) rotHead[2], 0, 0, 1);

        GlStateManager.translate(0, -1.75, 0);
        ResourceManager.bobble.renderPart("Head" + suffix);
        GlStateManager.popMatrix();

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    public void renderSkinGuy(BobbleType type, BobbleSkinModel model) {
        resetFigurineRotation();
        setupFigurineRotation(type);
        model.render(time, rotLeftArm, rotRightArm, rotLeftLeg, rotRightLeg, rotBody, rotHead);
    }

    /* RENDER ADDITIONAL ITEMS */

    public void renderPost(BobbleType type) {
        switch (type) {

            }

    }

    private void renderItem(ItemStack stack) {
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft()
                .getRenderItem()
                .renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }

    public void renderOrigin() {
        GlStateManager.disableCull();
        GlStateManager.disableTexture2D();

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();

        buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);

        double d = 0.125D;
        float r = 1F, g = 0F, b = 0F, a = 1F;

        buf.pos(0, d, 0).color(r, g, b, a).endVertex();
        buf.pos(d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, d).color(r, g, b, a).endVertex();

        buf.pos(0, d, 0).color(r, g, b, a).endVertex();
        buf.pos(-d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, d).color(r, g, b, a).endVertex();

        buf.pos(0, d, 0).color(r, g, b, a).endVertex();
        buf.pos(d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, -d).color(r, g, b, a).endVertex();

        buf.pos(0, d, 0).color(r, g, b, a).endVertex();
        buf.pos(-d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, -d).color(r, g, b, a).endVertex();

        buf.pos(0, -d, 0).color(r, g, b, a).endVertex();
        buf.pos(d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, d).color(r, g, b, a).endVertex();

        buf.pos(0, -d, 0).color(r, g, b, a).endVertex();
        buf.pos(d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, -d).color(r, g, b, a).endVertex();

        buf.pos(0, -d, 0).color(r, g, b, a).endVertex();
        buf.pos(-d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, d).color(r, g, b, a).endVertex();

        buf.pos(0, -d, 0).color(r, g, b, a).endVertex();
        buf.pos(-d, 0, 0).color(r, g, b, a).endVertex();
        buf.pos(0, 0, -d).color(r, g, b, a).endVertex();

        tess.draw();

        GlStateManager.enableTexture2D();
    }

    /* RENDER BASE PEDESTAL */

    public void renderSocket(BobbleType type) {
        GlStateManager.disableLighting();

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        float f3 = 0.01F;

        GlStateManager.translate(0.63, 0.175F, 0.0);
        GlStateManager.scale(f3, -f3, f3);
        GlStateManager.translate(0, 0, font.getStringWidth(type.label) * 0.5D);
        GlStateManager.rotate(90, 0, 1, 0);

        GlStateManager.depthMask(false);
        GlStateManager.translate(0, 1, 0);
        // NOTE: original code set up this matrix but never actually drew the
        // label. Restored the draw call so the socket text shows, matching
        // the base NTM implementation.
        font.drawString(type.label, 0, 0, 0xffffff);
        GlStateManager.depthMask(true);

        GlStateManager.enableLighting();
    }

    @Override
    protected void bindTexture(ResourceLocation loc) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
    }

    @Override
    public Item getItemForRenderer() {
        return Item.getItemFromBlock(AddonBlocks.bobblehead);
    }

    @Override
    public ItemRenderBase getRenderer(Item item) {
        return new ItemRenderBase() {

            @Override
            public void renderInventory() {
                GlStateManager.translate(0, -3.5, 0);
                GlStateManager.scale(10, 10, 10);
            }

            @Override
            public void renderCommon(ItemStack stack) {
                GlStateManager.scale(0.5, 0.5, 0.5);

                RenderBobbleAddon.instance.renderBobble(
                        BobbleType.VALUES[
                                Math.floorMod(stack.getItemDamage(), BobbleType.VALUES.length)
                                ]
                );
            }
        };
    }
}