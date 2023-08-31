package com.ardc.arkdust.preobject.Block.cw;

import com.ardc.arkdust.preobject.Block.IConnectRenderInfo;
import com.ardc.arkdust.preobject.BlockState.DropSelfBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class C7LabStructureBlock extends DropSelfBlock implements IConnectRenderInfo {
    private final boolean isGlass;
    public C7LabStructureBlock(Properties properties, boolean unbroken, boolean glass) {
        super(properties.strength(unbroken ? -1 : 30,720).harvestLevel(6).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops());
        isGlass = glass;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState block1, BlockState block2, Direction p_200122_3_) {
        return isGlass && IConnectRenderInfo.ignoreOrNot(block1.getBlock(),block2.getBlock()) || super.skipRendering(block1, block2, p_200122_3_);
    }

    public VoxelShape getVisualShape(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_) {
        return isGlass ? VoxelShapes.empty() : super.getVisualShape(p_230322_1_,p_230322_2_,p_230322_3_,p_230322_4_);
    }

    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return isGlass ? 1.0F : super.getShadeBrightness(p_220080_1_,p_220080_2_,p_220080_3_);
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return isGlass || super.propagatesSkylightDown(p_200123_1_, p_200123_2_, p_200123_3_);
    }

    @Override
    public ConnectRenderInfo getInfo() {
        return new ConnectRenderInfo(ConnectRenderType.IGNORE,0);
    }
}
