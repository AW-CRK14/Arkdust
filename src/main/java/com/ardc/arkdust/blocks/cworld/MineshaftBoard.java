package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.Ard8WayConnectBlock;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class MineshaftBoard extends Ard8WayConnectBlock {

    public MineshaftBoard(Properties properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState().setValue(WEST,false).setValue(EAST,false).setValue(NORTH,false).setValue(SOUTH,false)
        .setValue(WN,false).setValue(WS,false).setValue(EN,false).setValue(ES,false).setValue(WATERLOGGED,false));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return VoxelShapes.box(0,0,0,1,0.0625F,1);
    }

    @Override
    public ConnectRenderInfo getInfo() {
        return new ConnectRenderInfo(ConnectRenderType.DIRECTION_STATE,0);
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        return false;
    }
}
