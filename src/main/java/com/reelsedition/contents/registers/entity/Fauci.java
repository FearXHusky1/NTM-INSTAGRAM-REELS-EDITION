package com.reelsedition.contents.registers.entity;

import com.reelsedition.contents.registers.projectile.VaccineProjectile;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class Fauci extends EntityMob {

    public Fauci(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
    }

    public class EntityAIVaccineAttack extends EntityAIBase {
        private final EntityLiving attacker;
        private EntityLivingBase target;
        private final double moveSpeed;
        private final int maxCooldown;
        private final float attackRange;
        private int attackCooldown;

        public EntityAIVaccineAttack(EntityLiving attacker, double moveSpeed, int maxCooldown, float attackRange) {
            this.attacker = attacker;
            this.moveSpeed = moveSpeed;
            this.maxCooldown = maxCooldown;
            this.attackRange = attackRange;
            this.setMutexBits(3);
        }

        @Override
        public boolean shouldExecute() {
            EntityLivingBase t = this.attacker.getAttackTarget();
            if (t == null || !t.isEntityAlive()) return false;
            this.target = t;
            return true;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.target != null && this.target.isEntityAlive()
                    && this.attacker.getAttackTarget() == this.target;
        }

        @Override
        public void resetTask() {
            this.target = null;
            this.attackCooldown = 0;
        }

        @Override
        public void updateTask() {
            double distSq = this.attacker.getDistanceSq(this.target);
            boolean canSee = this.attacker.getEntitySenses().canSee(this.target);

            if (!canSee) {
                this.attacker.getNavigator().tryMoveToEntityLiving(this.target, this.moveSpeed);
            } else {
                this.attacker.getNavigator().clearPath();
            }

            this.attacker.getLookHelper().setLookPositionWithEntity(this.target, 30.0F, 30.0F);

            if (this.attackCooldown > 0) this.attackCooldown--;

            if (canSee && distSq <= (double) (this.attackRange * this.attackRange) && this.attackCooldown <= 0) {
                this.attackCooldown = this.maxCooldown;
                this.launchProjectile();
            }
        }

        private void launchProjectile() {
            World world = this.attacker.world;
            VaccineProjectile dart = new VaccineProjectile(world, this.attacker);
            dart.aimAt(this.target, 1.5F, 4.0F);
            world.spawnEntity(dart);

            world.playSound(null, this.attacker.posX, this.attacker.posY, this.attacker.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
    }


    @Override
    public String getName() {
        return "dr.fauci";
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIVaccineAttack(this, 1.0D, 40, 15.0F));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 25.0F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityMob.class, 25.0F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityVillager.class, 25.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
    }
}