package com.ardc.arkdust.CodeMigration.Block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArkCrystalBlock extends AbstractGlassBlock{

    private static IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light",0,7);

    public ArkCrystalBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().getBlockState().setValue(LIGHT_LEVEL,2));
    }

    //当方块周围更新时，获取内容
    @Override
    public void neighborChanged(BlockState blockstate, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        lightChanging(blockstate, world, pos);
        super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
    }

//    @Override
//    public BlockState updateShape(BlockState blockState, Direction p_196271_2_, BlockState p_196271_3_, IWorld world, BlockPos pos, BlockPos p_196271_6_) {
//        int lightin = world.getMaxLocalRawBrightness(pos) / 2;//由于light只能取到最高14，这里+2以保证可以获得State4
//        return blockState.setValue(LIGHT_LEVEL,lightin);
//    }

    //当方块放置时，获取内容
    public void onPlace(BlockState blockstate, World world, BlockPos pos, BlockState neighborBlock, boolean moving) {
        lightChanging(blockstate, world, pos);
        super.onPlace(blockstate, world, pos, neighborBlock, moving);
    }

//    @OnlyIn(Dist.CLIENT)
//    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
//        lightChanging(p_180655_1_,p_180655_2_,p_180655_3_);
//        super.animateTick(p_180655_1_,p_180655_2_,p_180655_3_,p_180655_4_);
//    }

    private void lightChanging(BlockState blockstate, World world, BlockPos pos) {
        int light = world.getMaxLocalRawBrightness(pos);//获取light为当前环境光等级
        int lightin = light / 2;//由于light只能取到最高14，这里+2以保证可以获得State4
        world.setBlock(new BlockPos(pos),blockstate.setValue(LIGHT_LEVEL,lightin),3);//刷新方块的blockstate


//        System.out.println("light:" + light);//输出光线等级的测试
//        System.out.println("lightin:" + lightin);//输出光线等级的测试
//        System.out.println("pos:" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ";" + pos);//输出方块位置的测试
//        System.out.println("light_level:" + blockstate.getBlockState().getValue(LIGHT_LEVEL));//输出light level测试

    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block,BlockState> builder){
        builder.add(LIGHT_LEVEL);
        super.createBlockStateDefinition(builder);
    }


    public int getLevel() {//返回状态亮度等级
        return this.defaultBlockState().getBlockState().getValue(LIGHT_LEVEL);
    }

    @OnlyIn(Dist.CLIENT)//覆写环境光遮蔽
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }
}