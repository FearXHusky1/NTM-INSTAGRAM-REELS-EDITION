package com.reelsedition.contents.machine;

import com.reelsedition.contents.registers.RegistryHandler;
import com.hbm.api.energymk2.IEnergyProviderMK2;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityMachineBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.*;

public class TileEntityFentReactor extends TileEntityMachineBase implements ITickable, IEnergyProviderMK2 {

    public long power;
    public static final long maxPower = 50000000;
    public int burnTime;
    public static final int maxBurnTime = 200;
    public long fuelEnergy;
    public static final long minFuelHE = 500_000_000;
    public static final long maxFuelHE = 5_000_000_000L;

    public TileEntityFentReactor() { super(1); }

    @Override public void update() {
        if (world.isRemote) return;
        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
            tryProvide(world, pos.getX() + d.offsetX, pos.getY() + d.offsetY, pos.getZ() + d.offsetZ, d);
        if (burnTime <= 0) {
            ItemStack s = inventory.getStackInSlot(0);
            if (s.getItem() == RegistryHandler.FENT_POWDER) {
                inventory.extractItem(0, 1, false);
                burnTime = maxBurnTime;
                fuelEnergy = minFuelHE + (long)(world.rand.nextDouble() * (maxFuelHE - minFuelHE));
                if (world.rand.nextInt(1000) == 0)
                    ExplosionNukeSmall.explode(world, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, ExplosionNukeSmall.PARAMS_MEDIUM);
                markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
            }
        }
        if (burnTime > 0 && fuelEnergy > 0) {
            long targetThisTick = fuelEnergy / burnTime;
            if (burnTime == 1) targetThisTick = fuelEnergy;
            long room = maxPower - power;
            long actual = Math.min(targetThisTick, room);
            fuelEnergy -= targetThisTick;
            power += actual;
            burnTime--;
            markDirty();
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    @Override public boolean isItemValidForSlot(int i, ItemStack s) { return s.getItem() == RegistryHandler.FENT_POWDER; }
    @Override public int[] getAccessibleSlotsFromSide(net.minecraft.util.EnumFacing e) { return new int[]{0}; }
    @Override public boolean canExtractItem(int s, ItemStack st, int a) { return false; }
    @Override public long getPower() { return power; }
    @Override public void setPower(long p) { power = p; }
    @Override public long getMaxPower() { return maxPower; }
    @Override public String getDefaultName() { return "Fent Reactor"; }

    @Override public NBTTagCompound writeToNBT(NBTTagCompound n) { n.setLong("p", power); n.setInteger("b", burnTime); n.setLong("fe", fuelEnergy); n.setTag("inv", inventory.serializeNBT()); return super.writeToNBT(n); }
    @Override public void readFromNBT(NBTTagCompound n) { power = n.getLong("p"); burnTime = n.getInteger("b"); fuelEnergy = n.getLong("fe"); if (n.hasKey("inv")) inventory.deserializeNBT(n.getCompoundTag("inv")); super.readFromNBT(n); }
    @Override public void serialize(io.netty.buffer.ByteBuf b) { b.writeLong(power); b.writeInt(burnTime); b.writeLong(fuelEnergy); ByteBufUtils.writeTag(b, inventory.serializeNBT()); super.serialize(b); }
    @Override public void deserialize(io.netty.buffer.ByteBuf b) { power = b.readLong(); burnTime = b.readInt(); fuelEnergy = b.readLong(); inventory.deserializeNBT(ByteBufUtils.readTag(b)); super.deserialize(b); }
    @Override @SideOnly(Side.CLIENT) public AxisAlignedBB getRenderBoundingBox() { return new AxisAlignedBB(pos.add(-1,0,-1), pos.add(2,2,2)); }
}
