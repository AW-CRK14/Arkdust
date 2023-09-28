package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.blocks.infac.IConnectRenderInfo;
import com.ardc.arkdust.blockstate.DropSelfBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class C7LabStructureBlock extends DropSelfBlock implements IConnectRenderInfo {
    private final boolean isGlass;
    public C7LabStructureBlock(Properties properties, boolean unbroken, boolean glass) {
        super(properties.strength(unbroken ? -1 : 30,720).requiresCorrectToolForDrops());
        isGlass = glass;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState block1, BlockState block2, Direction p_200122_3_) {
        return isGlass && IConnectRenderInfo.ignoreOrNot(block1.getBlock(),block2.getBlock()) || super.skipRendering(block1, block2, p_200122_3_);
    }

    public VoxelShape getVisualShape(BlockState p_230322_1_, BlockGetter p_230322_2_, BlockPos p_230322_3_, CollisionContext p_230322_4_) {
        return isGlass ? Shapes.empty() : super.getVisualShape(p_230322_1_,p_230322_2_,p_230322_3_,p_230322_4_);
    }

    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(BlockState p_220080_1_, BlockGetter p_220080_2_, BlockPos p_220080_3_) {
        return isGlass ? 1.0F : super.getShadeBrightness(p_220080_1_,p_220080_2_,p_220080_3_);
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, BlockGetter p_200123_2_, BlockPos p_200123_3_) {
        return isGlass || super.propagatesSkylightDown(p_200123_1_, p_200123_2_, p_200123_3_);
    }

    @Override
    public ConnectRenderInfo getInfo() {
        return new ConnectRenderInfo(ConnectRenderType.IGNORE,0);
    }
}
