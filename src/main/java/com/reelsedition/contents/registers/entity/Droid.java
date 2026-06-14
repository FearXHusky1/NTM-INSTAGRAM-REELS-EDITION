package com.reelsedition.contents.registers.entity;

import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.util.DamageResistanceHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import static java.lang.Math.abs;

public class Droid extends EntityMob implements IRangedAttackMob {

    public static final BulletConfig DROID_LASER = new BulletConfig()
            .setBeam()
            .setDamage(2.0F)
            .setLife(4)
            .setImpactsEntities(true)
            .setupDamageClass(DamageResistanceHandler.DamageClass.LASER)
            .setOnBeamImpact(BulletConfig.LAMBDA_STANDARD_BEAM_HIT);

    public Droid(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) { //horrible attempt to fix the laser rendering at correct target i turn left now goodluck everybody else
        double dx = target.posX - this.posX;
        double dy = target.posY + target.getEyeHeight() - (this.posY + this.getEyeHeight());
        double dz = target.posZ - this.posZ;
        double eye = abs(this.posY - this.getEyeHeight());
        double hDist = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90);
        float pitch = (float)Math.toDegrees(Math.atan2(dy, hDist));

        float prevYaw = this.rotationYaw;
        float prevPitch = this.rotationPitch;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;

        //george droid liberal oblitierator hotfix, there might be a better way to fix this, maybe just invert the dy stuff?
        //also this does not fix the rendering, idk if this even effects it at all
        if (eye != 0)
        {
            this.rotationPitch = -pitch;
        }


        EntityBulletBeamBase laser = new EntityBulletBeamBase(this, DROID_LASER, 10.0F, .1F, 0, 0, 0);
        this.world.spawnEntity(laser);

        this.rotationYaw = prevYaw;
        this.rotationPitch = prevPitch;
    }

    @Override
    public void setSwingingArms(boolean swinging) {}

    @Override
    public String getName() {
        return "George Droid";
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.0D, 5, 20, 25.0F));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 25.0F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityMob.class, 25.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityVillager.class, 25.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(15.0D);
    }
}
