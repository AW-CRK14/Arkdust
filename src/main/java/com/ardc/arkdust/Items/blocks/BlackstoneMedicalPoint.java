package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.BlockEntity.BlackstoneMedicalPointBE;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.preobject.BlockState.DropSelfBlock;
import com.ardc.arkdust.playmethod.camp.Camp;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Collections;

public class BlackstoneMedicalPoint extends DropSelfBlock {
    public static final IntegerProperty RUNNING_STATE = IntegerProperty.create("running_state", 0, 3);
    /**
     * RUNNING_STATE中:
     * 0->未激活
     * 1->失去活性
     * 2->缺少反应物
     * 3->工作中
     */
    public BlackstoneMedicalPoint() {
        super(Properties.of(Material.HEAVY_METAL).strength(2, 6).harvestTool(ToolType.PICKAXE).noOcclusion().requiresCorrectToolForDrops(), 1);
        this.registerDefaultState(this.defaultBlockState().setValue(RUNNING_STATE, 0));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlackstoneMedicalPointBE();
    }

    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        BlackstoneMedicalPointBE blockBE = (BlackstoneMedicalPointBE) worldIn.getBlockEntity(pos);//获取方块实体
        if (blockBE == null) return ActionResultType.FAIL;
        ActionResultType reType = ActionResultType.SUCCESS;
        if (!blockBE.ifDefaultSet()) {//检查方块数据是否初始化
            //初始化方块并发送信息
            blockBE.setDefaultValue(0, 4.5F, 1, 60, 100, false, false, true,false,6, BlackstoneMedicalPointBE.mode.BSMP);
            blockBE.setCampBelong(Collections.singletonList(Camp.PLAYER));
            blockBE.setBelongUs(true);
            if(!worldIn.isClientSide()) player.displayClientMessage(new TranslationTextComponent("mes.bs_medical_point.activation").withStyle(TextFormatting.GREEN), false);
            reType = ActionResultType.CONSUME;
        }
        int toState = getState(pos, state, worldIn);//获取新的状态
        worldIn.setBlock(pos, state.setValue(RUNNING_STATE, toState), 1);//设置新的状态
        if ((toState == 1 || toState == 2) && !worldIn.isClientSide())
            player.displayClientMessage(new TranslationTextComponent("mes.bs_medical_point.inactivation"), false);
        return reType;
    }

    private int getState(BlockPos pos, BlockState blockState, World worldIn) {
        int state = blockState.getValue(RUNNING_STATE);//获取方块状态
        boolean b = worldIn.getBlockState(pos.below()).getBlock() == BlockRegistry.pau_block.get();//获取下方的方块是否为赤金块
        if (state == 1 || state == 2) return b ? 3 : 1;
        return b ? 3 : 2;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block,BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(RUNNING_STATE);
    }
}




