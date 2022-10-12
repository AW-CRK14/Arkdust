package com.ardc.arkdust.preobject.Block;

import com.ardc.arkdust.preobject.BlockState.RotateBlock;
import com.ardc.arkdust.preobject.BlockState.WaterLoggedRotateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class TombstoneBlock extends WaterLoggedRotateBlock {
    public final boolean isBlack;
    public final VoxelShape voxelShape_NS;
    public final VoxelShape voxelShape_EW;
    public TombstoneBlock(boolean black,VoxelShape shape_ns,VoxelShape shape_ew) {
        super(Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).harvestLevel(1).randomTicks().strength(4,5).requiresCorrectToolForDrops().lightLevel((state)->5).sound(SoundType.STONE).noOcclusion());
        this.isBlack = black;
        this.voxelShape_NS = shape_ns;
        this.voxelShape_EW = shape_ew;
//        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        if(state.getValue(FACING).equals(Direction.NORTH) || state.getValue(FACING).equals(Direction.SOUTH))//如果方向为南北向
            return VoxelShapes.or(VoxelShapes.box(0,0,0,1,0.125F,1), voxelShape_NS);
        return VoxelShapes.or(VoxelShapes.box(0,0,0,1,0.125F,1), voxelShape_EW);
    }

    public void onRemove(BlockState state1, World world, BlockPos pos, BlockState state2, boolean i) {
        super.onRemove(state1,world,pos,state2,i);
        if(!world.isClientSide() && world instanceof ServerWorld && !i){
            ((ServerWorld)world).sendParticles(isBlack ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME,pos.getX()+0.5F,pos.getY()+1,pos.getZ()+0.5F, 32,1,1,1,1);
            PlayerEntity entity = world.getNearestPlayer(pos.getX(),pos.getY(),pos.getZ(),5,true);
            if(entity != null){
                entity.addEffect(new EffectInstance(Effects.BLINDNESS,30,2));
            }
        }
    }

    public void onPlace(BlockState blockState1,World world, BlockPos blockPos, BlockState blockState2, boolean b) {
        world.getBlockTicks().scheduleTick(blockPos,this,20);
    }

    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.tick(state,world,pos,random);
        world.sendParticles(ParticleTypes.ASH,pos.getX()+0.5F,pos.getY()+1,pos.getZ()+0.5F, 64,1,1,1,1);
        world.getBlockTicks().scheduleTick(pos,this,20);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(random.nextFloat() <= 0.01){
            MobEntity entity = random.nextInt(10) == 0 ? new WitherSkeletonEntity(EntityType.WITHER_SKELETON,world) : new SkeletonEntity(EntityType.SKELETON,world);
            entity.setPos(pos.getX(),pos.getY()+1,pos.getZ());
            entity.finalizeSpawn(world,world.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.MOB_SUMMONED,null,null);
            world.addFreshEntity(entity);
        }
    }



//    public enum preVX{
//        STATE3(VoxelShapes.join(,VoxelShapes.join()));
//
//        public final VoxelShape SHAPE_NS;
//        public final VoxelShape SHAPE_EW;
//        preVX(VoxelShape shape_ns,VoxelShape shape_ew){
//            this.SHAPE_NS = shape_ns;
//            this.SHAPE_EW = shape_ew;
//        }
//    }
}
