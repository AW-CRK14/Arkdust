package com.ardc.arkdust.BlockEntity;

import com.ardc.arkdust.BlockRegistry;
import com.ardc.arkdust.CodeMigration.RunHelper.CampHelper;
import com.ardc.arkdust.Enums.Camp;
import com.ardc.arkdust.Items.blocks.BlackstoneMedicalPoint;
import com.ardc.arkdust.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.ParticleArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PhonographBE extends TileEntity implements ITickableTileEntity {
//    public boolean working;//是否处于工作状态
    public int workingRange;//工作有效范围
    public boolean activation;//激活与否
    public boolean attackNeutrality;//是否攻击中立单位
    public boolean attackUnfriendly;//是否攻击敌方单位
    public boolean treatFriendly;//是否治愈我方单位
    public int damage;//机械耐久
    public boolean canBeHurt;//可以被打……就是吸引敌方仇恨
    public List<Camp> campBelong;//属于的阵营
    public List<Camp> campHate;//敌对的阵营
    public List<Float> funcValue = Arrays.asList(new Float[2]);
    public List<Integer> funcTick = Arrays.asList(new Integer[2]);
    public boolean belongUs;
    public boolean belonging;
    public enum mode{
        PHONOGRAPH,BSMP
    }
    public mode setMode;

    public int attackTimer = 0;
    public int treatTimer = 0;

    public PhonographBE() {
        super(TileEntityTypeRegistry.aPhonographBE.get());
    }

    public boolean ifDefaultSet() {
        return activation;
    }

    public boolean ifCanBeHurt(){
        return canBeHurt;
    }
    
//    public boolean ifWorking(){
//        return working;
//    }

    public void setDefaultValue(float attackValue, float treatValue, int attackTick, int treatTick, int damage, boolean attackNeutrality, boolean attackUnfriendly, boolean treatFriendly, boolean canBeHurt,int workingRange,mode mode) {
        funcValue.set(0, attackValue);
        funcValue.set(1, treatValue);
        funcTick.set(0, Math.max(attackTick, 1));
        funcTick.set(1, Math.max(treatTick, 1));
        this.damage = damage;
        this.activation = true;
        this.attackNeutrality = attackNeutrality;
        this.attackUnfriendly = attackUnfriendly;
        if(attackNeutrality) this.attackUnfriendly = true;
        this.treatFriendly = treatFriendly;
        this.canBeHurt = canBeHurt;
        this.setMode = mode;
        this.workingRange = workingRange;
        setChanged();
    }

    public void setCampBelong(List<Camp> campList){
        this.campBelong = campList;
        setChanged();
    }

    public void setCampHate(List<Camp> campList){
        this.campHate = campList;
        setChanged();
    }

    public void setBelongUs(boolean belongUs) {
        this.belongUs = belongUs;
        this.belonging = true;
        setChanged();
    }

//    public void setWorking(boolean working){
//        this.working = working;
//    }
    
    

    //TODO 战术更替模块
    //TODO 耐久损失模块

    public void tick() {
        if (level != null && !level.isClientSide()) {
            BlockState blockState = level.getBlockState(worldPosition);
            if (!(blockState.getValue(BlackstoneMedicalPoint.RUNNING_STATE) == 3) || !activation) return;
            AxisAlignedBB rangeAABB = new AxisAlignedBB(worldPosition.getX() - workingRange, worldPosition.getY() - workingRange, worldPosition.getZ() - workingRange, worldPosition.getX() + workingRange, worldPosition.getY() + workingRange, worldPosition.getZ() + workingRange);
            if (setMode == mode.BSMP && level.getBlockState(worldPosition.below()).getBlock() != BlockRegistry.pau_block.get()) {
//            working = false;
                level.setBlock(worldPosition, blockState.setValue(BlackstoneMedicalPoint.RUNNING_STATE, 2), 1);
                return;
            }

            if (treatFriendly) {
                if (treatTimer < funcTick.get(1)) treatTimer++;
                else {
                    List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, rangeAABB);
                    List<LivingEntity> goalEntityList = new ArrayList<>();
                    for (LivingEntity entity : entityList) {
                        if (CampHelper.ifEntityBelongToCamp(campBelong, entity) && entity.getHealth() < entity.getMaxHealth()) { //TODO 阵营切换
//                            System.out.printf(entity+"%n  heal before:" + entity.getHealth());
                            goalEntityList.add(entity);
                            entity.heal(funcValue.get(1));
//                            System.out.printf("%n  heal after:" + entity.getHealth());
                            addHappyVillagerParticle(entity.getX(),entity.getY(),entity.getZ());
                        }
                    }
                    if(!goalEntityList.isEmpty()) treatTimer = 0;
                    pauBlockChange(goalEntityList.size());
                }
            }
            if (attackUnfriendly) {
                if (attackTimer <= funcTick.get(0)) attackTimer++;
            }
        }
    }

    private void addHappyVillagerParticle(double x,double y,double z){
        if(level instanceof ServerWorld) ((ServerWorld)level).sendParticles(ParticleTypes.HAPPY_VILLAGER,x,y,z,32,3,3,3,1);
    }

    private void pauBlockChange(int size){
        if(setMode == mode.BSMP && level!= null && size > 0) {
            Random r = new Random();
            int i = r.nextInt(damage);
            if(i <= size) level.setBlock(worldPosition.below(), Blocks.GOLD_BLOCK.defaultBlockState(),1);
        }
    }

}