package com.reelsedition.contents.registers.projectile;

import com.reelsedition.contents.effects.vaccine.VaccinatedEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class VaccineProjectile extends EntityThrowable {

    public VaccineProjectile(World world) {
        super(world);
    }

    public VaccineProjectile(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    public VaccineProjectile(World world, double x, double y, double z) {
        super(world, x, y, z);
    }
    public void aimAt(EntityLivingBase target, float velocity, float inaccuracy) {
        double dx = target.posX - this.posX;
        double dy = target.posY + target.getEyeHeight() - 1.6D - this.posY;
        double dz = target.posZ - this.posZ;
        this.shoot(dx, dy, dz, velocity, inaccuracy);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            if (result.entityHit instanceof EntityLivingBase) {
                EntityLivingBase target = (EntityLivingBase) result.entityHit;

                PotionEffect dose = new PotionEffect(VaccinatedEffect.INSTANCE, 32767, 0, false, false);
                target.addPotionEffect(dose);
            }

            world.playSound(null, getPosition(), SoundEvents.ENTITY_SPLASH_POTION_BREAK,
                    SoundCategory.NEUTRAL, 1.0F, 1.0F);

            world.removeEntity(this);
        }
    }
}