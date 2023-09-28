package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.WaterLoggedRotateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class TombstoneBlock extends WaterLoggedRotateBlock {
    public final boolean isBlack;
    public final VoxelShape voxelShape_NS;
    public final VoxelShape voxelShape_EW;
    public TombstoneBlock(boolean black,VoxelShape shape_ns,VoxelShape shape_ew) {
        super(Properties.copy(Blocks.BLACKSTONE).randomTicks().strength(4,5).requiresCorrectToolForDrops().lightLevel((state)->5).noOcclusion());
        this.isBlack = black;
        this.voxelShape_NS = shape_ns;
        this.voxelShape_EW = shape_ew;
//        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        if(state.getValue(HORIZONTAL_FACING).equals(Direction.NORTH) || state.getValue(HORIZONTAL_FACING).equals(Direction.SOUTH))//如果方向为南北向
            return Shapes.or(Shapes.box(0,0,0,1,0.125F,1), voxelShape_NS);
        return Shapes.or(Shapes.box(0,0,0,1,0.125F,1), voxelShape_EW);
    }

    public void onRemove(BlockState state1, Level world, BlockPos pos, BlockState state2, boolean i) {
        super.onRemove(state1,world,pos,state2,i);
        if(!world.isClientSide() && world instanceof ServerLevel && !i){
            ((ServerLevel)world).sendParticles(isBlack ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME,pos.getX()+0.5F,pos.getY()+1,pos.getZ()+0.5F, 32,1,1,1,1);
            Player entity = world.getNearestPlayer(pos.getX(),pos.getY(),pos.getZ(),5,true);
            if(entity != null){
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,30,2));
            }
        }
    }

    public void onPlace(BlockState blockState1,Level world, BlockPos blockPos, BlockState blockState2, boolean b) {
        world.scheduleTick(blockPos,this,20);
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state,world,pos,random);
        world.sendParticles(ParticleTypes.ASH,pos.getX()+0.5F,pos.getY()+1,pos.getZ()+0.5F, 64,1,1,1,1);
        world.scheduleTick(pos,this,20);
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if(random.nextFloat() <= 0.01){
            Mob entity = random.nextInt(10) == 0 ? new WitherSkeleton(EntityType.WITHER_SKELETON,world) : new Skeleton(EntityType.SKELETON,world);
            entity.setPos(pos.getX(),pos.getY()+1,pos.getZ());
            ForgeEventFactory.onFinalizeSpawn(entity,world,world.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.SPAWNER,null,null);
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
