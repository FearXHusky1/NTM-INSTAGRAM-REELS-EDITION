package com.reelsedition.contents.registers.entity;

import com.hbm.lib.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDroidLaser extends Entity {

    public EntityLivingBase shooter;
    public EntityLivingBase target;
    public float damage = 10.0F;

    public EntityDroidLaser(World world) {
        super(world);
        this.ignoreFrustumCheck = true;
    }

    public EntityDroidLaser(World world, EntityLivingBase shooter, EntityLivingBase target, float damage) {
        super(world);
        this.ignoreFrustumCheck = true;
        this.shooter = shooter;
        this.target = target;
        this.damage = damage;
        this.setPosition(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ);
    }

    @Override
    protected void entityInit() {}

    @Override
    public void onUpdate() {
        if (this.ticksExisted > 1) {
            this.setDead();
            return;
        }

        if (shooter == null || target == null) {
            this.setDead();
            return;
        }

        // aim at target's eye level
        Vec3d start = new Vec3d(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ);
        Vec3d end = new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ);

        // spawn particles along the beam
        double steps = 10;
        for (int i = 0; i <= steps; i++) {
            double t = i / steps;
            world.spawnParticle(EnumParticleTypes.REDSTONE,
                    start.x + (end.x - start.x) * t,
                    start.y + (end.y - start.y) * t,
                    start.z + (end.z - start.z) * t,
                    0, 0, 0);
        }

        // deal damage
        target.attackEntityFrom(ModDamageSource.radiation, damage);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {}

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() { return 15728880; }

    @Override
    public float getBrightness() { return 1.0F; }
}