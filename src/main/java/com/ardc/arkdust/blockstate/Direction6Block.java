package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DIRECTION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(DIRECTION, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty() || !dropSelf)
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this));
    }
}
