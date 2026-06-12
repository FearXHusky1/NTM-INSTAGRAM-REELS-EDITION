package com.gommer.contents.fluids;

import com.gommer.contents.registers.AddonBlocks;
import com.gommer.contents.registers.AddonFluids;
import com.hbm.render.misc.EnumSymbol;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import java.awt.*;
import java.util.Locale;

public class FentFluid extends Fluid {

    public static class FentFluidBlock extends BlockFluidClassic {

        public FentFluidBlock(Fluid fluid, Material material, String s) {
            super(fluid, material);
            ((Block)this).setTranslationKey(s);
            this.setRegistryName(s);
            ((Block)this).setCreativeTab(null);
            this.displacements.put(this, false);
            AddonBlocks.ALL_BLOCKS.add(this);
        }

        @Override
        public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
            // Pale blue-white tint, adjust RGB to taste
            return new Vec3d(0.8, 0.9, 1.0);
        }

        @Override
        public boolean canDisplace(IBlockAccess world, BlockPos pos) {
            return super.canDisplace(world, pos);
        }

        @Override
        public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity) {
            // Add whatever effect you want here, e.g. slowness, damage, etc.
            // entity.attackEntityFrom(DamageSource.MAGIC, 2.0F);
        }

        @Override
        public int tickRate(World world) {
            return 20; // how fast it spreads, higher = slower
        }
    }

    public FentFluid(String name) {
        super(
                name,
                new ResourceLocation("gommer", "textures/blocks/forgefluid/fent_still"),
                new ResourceLocation("gommer", "textures/blocks/forgefluid/fent_flow"),
                Color.white
        );
    }
    public AddonFluidType(String name, int color, int p, int f, int r, EnumSymbol symbol, String texFluid) {
        super(name,color,p,f,r,symbol,texFluid.toLowerCase(Locale.US),0xFFFFFF,1121+(id++),null);
        AddonFluids.metaOrderPointer.add(this);
    }

    @Override
    public String getUnlocalizedName() {
        return "hbmfluid.fent"; // swap for your own lang key if you want
    }
}