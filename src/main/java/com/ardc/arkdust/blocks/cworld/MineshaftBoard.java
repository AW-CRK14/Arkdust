package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.Ard8WayConnectBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MineshaftBoard extends Ard8WayConnectBlock {

    public MineshaftBoard(Properties properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState().setValue(WEST,false).setValue(EAST,false).setValue(NORTH,false).setValue(SOUTH,false)
        .setValue(WN,false).setValue(WS,false).setValue(EN,false).setValue(ES,false).setValue(WATERLOGGED,false));
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return Shapes.box(0,0,0,1,0.0625F,1);
    }

    @Override
    public ConnectRenderInfo getInfo() {
        return new ConnectRenderInfo(ConnectRenderType.DIRECTION_STATE,0);
    }

    public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
        return false;
    }
}
