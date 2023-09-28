package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.Direction6Block;
import com.ardc.arkdust.blockstate.WaterLoggedDirection6Block;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FracturedOakLog extends WaterLoggedDirection6Block {
    public FracturedOakLog(Properties properties, boolean drop) {
        super(properties, drop);
    }

    private static VoxelShape fromDirection(Direction direction){
        return switch (direction){
            case UP -> Shapes.box(0,0,0,1,0.6F,1);
            case DOWN -> Shapes.box(0,0.4F,0,1,1,1);
            case SOUTH -> Shapes.box(0,0,0,1,1,0.6F);
            case NORTH -> Shapes.box(0,0,0.4F,1,1,1);
            case EAST -> Shapes.box(0,0,0,0.6F,1,1);
            case WEST -> Shapes.box(0.4F,0,0,1,1,1);
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return fromDirection(state.getValue(Direction6Block.DIRECTION));
    }


}
