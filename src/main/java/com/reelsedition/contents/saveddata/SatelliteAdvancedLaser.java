package com.reelsedition.contents.saveddata;

import com.hbm.entity.logic.EntityDeathBlast;

import com.hbm.saveddata.satellites.SatelliteLaser;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


import static com.hbm.uninos.NodeNet.rand;

public class SatelliteAdvancedLaser extends SatelliteLaser {


    public long lastOp;

    public SatelliteAdvancedLaser() {
        this.ifaceAcs.add(InterfaceActions.HAS_MAP);
        this.ifaceAcs.add(InterfaceActions.SHOW_COORDS);
        this.ifaceAcs.add(InterfaceActions.CAN_CLICK);
        this.satIface = Interfaces.SAT_PANEL;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("lastOp", lastOp);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        lastOp = nbt.getLong("lastOp");
    }

    public void onClick(World world, EntityPlayerMP player, int x, int z) {

        if(lastOp + 10000 < System.currentTimeMillis()) {
            lastOp = System.currentTimeMillis();

            int y = world.getHeight(x, z);

            EntityDeathBlast blast = new EntityDeathBlast(world);
            int times = 15 + rand.nextInt(5);
            for (int i = 0; i < times; i++)
            {

                blast.posX = x+rand.nextInt(15);;
                blast.posY = y;
                blast.posZ = z+rand.nextInt(15);;
                blast.detonator = player;
                world.spawnEntity(blast);
            }
        }
    }

    @Override
    public float[] getColor() {
        return new float[] { 0.221F, 0.663F, 1.0F };
    }
}

