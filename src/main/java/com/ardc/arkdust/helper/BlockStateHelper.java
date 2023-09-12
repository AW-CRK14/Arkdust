package com.ardc.arkdust.helper;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.Template;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class BlockStateHelper {
    public static BlockState waterloggedRotateBlock(Block t, BlockItemUseContext context){
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);
        if(state.is(t)){
            return t.defaultBlockState().setValue(WATERLOGGED,false).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }else{
            FluidState fluid = context.getLevel().getFluidState(pos);
            return t.defaultBlockState().setValue(WATERLOGGED,fluid.getType() == Fluids.WATER).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
        }
    }

    public interface ISetBlockFromInfoState{
        void call(ISeedReader world, Template.BlockInfo info,BlockState state);
    }
}
