package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blockstate.Direction6Block;
import com.ardc.arkdust.blockstate.WaterLoggedDirection6Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.HashMap;
import java.util.Map;

public class FracturedOakLog extends WaterLoggedDirection6Block {
    public FracturedOakLog(Properties properties, boolean drop) {
        super(properties, drop);
    }

    private static final Map<Direction,VoxelShape> shape = new HashMap<>();
    static {
        shape.put(Direction.UP,VoxelShapes.box(0,0,0,1,0.6F,1));
        shape.put(Direction.DOWN,VoxelShapes.box(0,0.4F,0,1,1,1));
        shape.put(Direction.SOUTH,VoxelShapes.box(0,0,0,1,1,0.6F));
        shape.put(Direction.NORTH,VoxelShapes.box(0,0,0.4F,1,1,1));
        shape.put(Direction.EAST,VoxelShapes.box(0,0,0,0.6F,1,1));
        shape.put(Direction.WEST,VoxelShapes.box(0.4F,0,0,1,1,1));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return shape.get(state.getValue(Direction6Block.DIRECTION));
    }


}
