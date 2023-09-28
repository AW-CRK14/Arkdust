package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.WaterLoggedRotateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Random;

public class SkeletonBlock extends WaterLoggedRotateBlock {
    public final State blockVoxelState;
    public final boolean isBlack;
    public SkeletonBlock(State state,boolean black) {
        super(Properties.copy(Blocks.SKELETON_SKULL).instabreak().noOcclusion());
        this.blockVoxelState = state;
        this.isBlack = black;
//        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
    }

    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_){
        return this.blockVoxelState.getShape();
//        return VoxelShapes.box(0.125F,0,0.125F,0.875F,0.75F,0.875F);
    }

    public enum State {
        LAY(Shapes.box(0.125F,0,0.125F,0.875F,0.6F,0.875F),2),
        STAND(Shapes.box(0.25F,0,0.25F,0.75F,1,0.75F),4);

        public VoxelShape shape;
        public int dropCount;
        State(VoxelShape shape, int drop){
            this.shape = shape;
            this.dropCount = drop;
        }

        public VoxelShape getShape(){
            return shape;
        }

        public ItemStack getDrop(){
            return new ItemStack(Items.BONE,dropCount);
        }
    }

    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        dropsOriginal.add(this.blockVoxelState.getDrop());
        Random r = new Random();
        if(r.nextBoolean()){
            dropsOriginal.add(new ItemStack((this.isBlack && r.nextFloat() <= 0.3F) ? Items.WITHER_SKELETON_SKULL : Items.SKELETON_SKULL));
        }
        return dropsOriginal;
    }
}
