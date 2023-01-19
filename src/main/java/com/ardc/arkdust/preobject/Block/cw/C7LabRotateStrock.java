package com.ardc.arkdust.preobject.Block.cw;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

public class C7LabRotateStrock extends C7LabStructureBlock {
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public C7LabRotateStrock(Properties properties, boolean unbroken, boolean glass) {
        super(properties, unbroken, glass);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block,BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(HORIZONTAL_FACING);
    }
}
