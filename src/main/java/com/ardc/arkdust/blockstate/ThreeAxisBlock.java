package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.helper.LootHelper;
import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class ThreeAxisBlock extends PreBlock {
    boolean d;
    public ThreeAxisBlock(Properties properties,boolean dropSelf) {
        super(properties);
        d = dropSelf;
    }

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public BlockState rotate(BlockState state, Rotation rotation) {
        switch(rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                return switch (state.getValue(AXIS)) {
                    case X -> state.setValue(AXIS, Direction.Axis.Z);
                    case Z -> state.setValue(AXIS, Direction.Axis.X);
                    default -> state;
                };
            default:
                return state;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AXIS);
        super.createBlockStateDefinition(p_206840_1_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        return this.defaultBlockState().setValue(AXIS, p_196258_1_.getClickedFace().getAxis());
    }

    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return d ? LootHelper.dropSelfWhenNoLoot(state,builder) : super.getDrops(state,builder);
    }
}
