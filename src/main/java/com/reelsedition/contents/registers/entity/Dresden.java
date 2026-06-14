package com.reelsedition.contents.registers.entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
public class Dresden extends EntityMob {

    public Dresden(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
    }



    @Override
    public String getName() {
        return "Harry Dresden";
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.2D, true));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }
}
