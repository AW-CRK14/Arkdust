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

    //��������Χ����ʱ����ȡ����
    @Override
    public void neighborChanged(BlockState blockstate, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        lightChanging(blockstate, world, pos);
        super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
    }

//    @Override
//    public BlockState updateShape(BlockState blockState, Direction p_196271_2_, BlockState p_196271_3_, IWorld world, BlockPos pos, BlockPos p_196271_6_) {
//        int lightin = world.getMaxLocalRawBrightness(pos) / 2;//����lightֻ��ȡ�����14������+2�Ա�֤���Ի��State4
//        return blockState.setValue(LIGHT_LEVEL,lightin);
//    }

    //���������ʱ����ȡ����
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
        int light = world.getMaxLocalRawBrightness(pos);//��ȡlightΪ��ǰ������ȼ�
        int lightin = light / 2;//����lightֻ��ȡ�����14������+2�Ա�֤���Ի��State4
        world.setBlock(new BlockPos(pos),blockstate.setValue(LIGHT_LEVEL,lightin),3);//ˢ�·����blockstate


//        System.out.println("light:" + light);//������ߵȼ��Ĳ���
//        System.out.println("lightin:" + lightin);//������ߵȼ��Ĳ���
//        System.out.println("pos:" + pos.getX() + "," + pos.getY() + "," + pos.getZ() + ";" + pos);//�������λ�õĲ���
//        System.out.println("light_level:" + blockstate.getBlockState().getValue(LIGHT_LEVEL));//���light level����

    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block,BlockState> builder){
        builder.add(LIGHT_LEVEL);
        super.createBlockStateDefinition(builder);
    }


    public int getLevel() {//����״̬���ȵȼ�
        return this.defaultBlockState().getBlockState().getValue(LIGHT_LEVEL);
    }

    @OnlyIn(Dist.CLIENT)//��д�������ڱ�
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }
}