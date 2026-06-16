package com.reelsedition.contents.registers.entity;
import com.reelsedition.contents.registers.RegistryHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.*;
import com.hbm.items.ModItems;
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.entity.IEntityLivingData;
import static java.lang.Math.abs;

public class YN extends EntityMob implements IRangedAttackMob {


    public YN(World world) {
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
        if (1 != 0)
        {
            this.rotationPitch = -pitch;
        }


    }

    private boolean swingingArms = false;

    @Override
    public void setSwingingArms(boolean swinging) {
        this.swingingArms = swinging;
    }

    public boolean isSwingingArms() {
        return swingingArms;
    }
    @Override
    public String getName() {
        return "YN";
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.0D, 5, 5, 15.0F));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
    }
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModItems.gun_uzi));
        return super.onInitialSpawn(difficulty, livingdata);
    }
}


