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
import net.minecraft.util.Rotation;

import java.util.Collections;
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
                switch(state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(AXIS);
    }

    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        return this.defaultBlockState().setValue(AXIS, p_196258_1_.getClickedFace().getAxis());
    }

    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return d ? Collections.singletonList(new ItemStack(this)) : super.getDrops(state,builder);
    }
}
