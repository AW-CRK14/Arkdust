package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.blocks.cworld.BlackstoneMedicalPoint;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import com.ardc.arkdust.resource.Tag;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BlackstoneMedicalPointBE extends BlockEntity {


    public BlackstoneMedicalPointBE(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.BLACKSTONE_MEDICAL_POINT_BE.get(),pos,state);
    }
    
//    public boolean ifWorking(){
//        return working;
//    }


//    public void setWorking(boolean working){
//        this.working = working;
//    }
    
    public boolean blackstoneMedicalPointState(){
        return level.getBlockState(worldPosition).getValue(BlackstoneMedicalPoint.WORKING);
    }

    public void tick() {
        if (!level.isClientSide() && level.getBlockState(worldPosition.below()).getBlock().equals(BlockRegistry.blackstone_medical_point.get())) {//前置判断 h
            //创建治疗范围aabb盒
            AABB rangeAABB = new AABB(worldPosition.getX() - 5, worldPosition.getY() - 3, worldPosition.getZ() - 5, worldPosition.getX() + 5, worldPosition.getY() + 3, worldPosition.getZ() + 5);
            List<LivingEntity> entityList = level.getEntitiesOfClass(LivingEntity.class, rangeAABB);
            for (LivingEntity entity : entityList) {//遍历实体
                if (entity.getType().is(Tag.LivingEntities.STRUCTURE$WASTELAND_MINESHAFT_DIRTY_CONCRETE) && entity.getHealth() != entity.getMaxHealth()) {
                    entity.heal(3);//治疗实体
//                            System.out.printf("%n  heal after:" + entity.getHealth());
                    addHappyVillagerParticle(entity.getX(),entity.getY(),entity.getZ());//添加粒子
                }
            }
            setChanged();
        }
    }


    private void addHappyVillagerParticle(double x,double y,double z){
        if(level instanceof ServerLevel) ((ServerLevel)level).sendParticles(ParticleTypes.HAPPY_VILLAGER,x,y,z,32,2,2,2,1);
    }

}