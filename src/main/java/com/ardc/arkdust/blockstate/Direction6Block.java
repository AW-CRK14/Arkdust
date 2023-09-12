package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import java.util.Collections;
import java.util.List;

public class Direction6Block extends PreBlock {
    public static final EnumProperty<Direction> DIRECTION = BlockStateProperties.FACING;
    boolean dropSelf;

    public Direction6Block(Properties properties,boolean drop) {
        super(properties);
        dropSelf = drop;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(DIRECTION,rotation.rotate(state.getValue(DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(DIRECTION)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DIRECTION);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(DIRECTION, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty() || !dropSelf)
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this));
    }
}
