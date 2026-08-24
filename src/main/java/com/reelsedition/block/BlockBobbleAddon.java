package com.reelsedition.block;
import com.hbm.blocks.ICustomBlockItem;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.IModelRegister;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.main.MainRegistry;
import com.hbm.main.client.NTMClientRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.ShadyUtil;
import com.hbm.world.gen.nbt.INBTBlockTransformable;
import com.hbm.world.gen.nbt.INBTTileEntityTransformable;
import com.reelsedition.inventory.gui.GUIScreenBobble;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BlockBobbleAddon extends BlockContainer implements INBTBlockTransformable, ICustomBlockItem {

    public static final PropertyInteger META = PropertyInteger.create("rot", 0, 15);
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(5.5D / 16D, 0.0D, 5.5D / 16D, 1.0D - 5.5D / 16D,
            0.625D, 1.0D - 5.5D / 16D);

    public BlockBobbleAddon(String name) {
        super(Material.IRON);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(META, 0));
        this.setLightOpacity(0);
        this.setHardness(0.0F);
        this.setResistance(0.0F);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
                                  EntityPlayer player) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityBobble entity) {
            return new ItemStack(this, 1, entity.type.ordinal());
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te,
                             ItemStack tool) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);

        if (!world.isRemote && !player.capabilities.isCreativeMode) {
            if (te instanceof BlockBobbleAddon.TileEntityBobble entity) {
                ItemStack drop = new ItemStack(this, 1, entity.type.ordinal());
                spawnAsEntity(world, pos, drop);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote && !player.isSneaking()) {
            FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tabs, NonNullList<ItemStack> list) {
        for (int i = 1; i < BobbleType.VALUES.length; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer) {
        int rotation = MathHelper.floor((double) ((placer.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
        return this.getDefaultState().withProperty(META, rotation);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityBobble bobble) {
            bobble.type = BobbleType.VALUES[Math.abs(stack.getItemDamage()) % BobbleType.VALUES.length];
            bobble.markDirty();
        }
    }

    @Override
    public int transformMeta(int meta, int coordBaseMode) {
        return (meta + coordBaseMode * 4) % 16;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, META);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(META, meta & 15);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(META);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBobble();
    }

    @Override
    public void registerItem() {
        ItemBlock itemBlock = new BlockBobbleItem(this);
        itemBlock.setRegistryName(this.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
    }

    public enum BobbleType {
        NONE("null", "null", null, null, false, ScrapType.BOARD_BLANK),
        NORWOOD("Norwood", "Norwood", "Ported NTM to 1.12 ", "get back to work gommer", true, ScrapType.MEM_16K_A);
        public static final BobbleType[] VALUES = values();

        public final String name;
        public final String label;
        public final String contribution;
        public final String inscription;
        public final boolean skinLayers;
        public final ScrapType scrap;
        /** For skins loaded at runtime */
        public final UUID skinUuid;

        BobbleType(String name, String label, String contribution, String inscription, boolean layers,
                   ScrapType scrap) {
            this(name, label, contribution, inscription, layers, scrap, null);
        }

        BobbleType(String name, String label, String contribution, String inscription, boolean layers,
                   ScrapType scrap, UUID skinUuid) {
            this.name = name;
            this.label = label;
            this.contribution = contribution;
            this.inscription = inscription;
            this.skinLayers = layers;
            this.scrap = scrap;
            this.skinUuid = skinUuid;
        }
    }

    private static class BlockBobbleItem extends CustomBlockItem implements IModelRegister {
        private BlockBobbleItem(Block block) {
            super(block);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void registerModels() {
            ModelResourceLocation syntheticLocation = NTMClientRegistry.getSyntheticTeisrModelLocation(this);
            for (int meta = 0; meta < BobbleType.VALUES.length; meta++) {
                ModelLoader.setCustomModelResourceLocation(this, meta, syntheticLocation);
            }
        }
    }


    public static class TileEntityBobble extends TileEntity implements IGUIProvider, INBTTileEntityTransformable {
        public BobbleType type = BobbleType.NONE;
        private AxisAlignedBB bb;

        @Override
        public NBTTagCompound getUpdateTag() {
            return writeToNBT(super.getUpdateTag());
        }

        @Override
        public void handleUpdateTag(NBTTagCompound tag) {
            readFromNBT(tag);
        }

        @Nullable
        @Override
        public SPacketUpdateTileEntity getUpdatePacket() {
            NBTTagCompound nbt = new NBTTagCompound();
            writeToNBT(nbt);
            return new SPacketUpdateTileEntity(this.pos, 0, nbt);
        }

        @Override
        public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
            readFromNBT(pkt.getNbtCompound());
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            super.readFromNBT(nbt);
            this.type = BobbleType.VALUES[Math.abs(nbt.getByte("type")) % BobbleType.VALUES.length];
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
            super.writeToNBT(nbt);
            nbt.setByte("type", (byte) type.ordinal());
            return nbt;
        }

        @Override
        public void transformTE(World world, int coordBaseMode) {
            type = BobbleType.VALUES[world.rand.nextInt(BobbleType.VALUES.length - 1) + 1];
        }

        @Override
        public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
            return new GUIScreenBobble(this);
        }

        @Override
        public AxisAlignedBB getRenderBoundingBox() {
            if (bb == null) bb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            return bb;
        }
    }
}