package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.WaterLoggedRotateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Random;

public class SkeletonBlock extends WaterLoggedRotateBlock {
    public final State blockVoxelState;
    public final boolean isBlack;
    public SkeletonBlock(State state,boolean black) {
        super(Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).instabreak().noOcclusion());
        this.blockVoxelState = state;
        this.isBlack = black;
//        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return this.blockVoxelState.getShape();
//        return VoxelShapes.box(0.125F,0,0.125F,0.875F,0.75F,0.875F);
    }

    public enum State {
        LAY(VoxelShapes.box(0.125F,0,0.125F,0.875F,0.6F,0.875F),2),
        STAND(VoxelShapes.box(0.25F,0,0.25F,0.75F,1,0.75F),4);

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

    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

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
