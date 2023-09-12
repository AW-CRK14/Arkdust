package com.ardc.arkdust.blocks.terra_energy;


import com.ardc.arkdust.blockstate.RotateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraftforge.common.ToolType;


public class EOirReactorControlBoard extends RotateBlock {
    private static BooleanProperty WORKING_STATE = BooleanProperty.create("working");

    public EOirReactorControlBoard(){
        super(Properties.of(Material.HEAVY_METAL).harvestTool(ToolType.PICKAXE).strength(8,30).lightLevel((level)->(level.getBlockState().getValue(WORKING_STATE) ? 7 : 0)).noOcclusion());
        //strength是强度，等价于非官方版中的hardnessAndResistance
        this.registerDefaultState(this.defaultBlockState().getBlockState().setValue(WORKING_STATE,false));

    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(WORKING_STATE);
        super.createBlockStateDefinition(builder);
    }
}
