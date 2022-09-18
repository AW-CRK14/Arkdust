package com.ardc.arkdust.model.modelblock;

import com.ardc.arkdust.preobject.pre.PreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;

public class LifeBlockModel extends PreBlock {
    public static final IntegerProperty COLOR = IntegerProperty.create("color",1,3);
    public LifeBlockModel(){
        super(Properties.of(Material.AIR).noCollission().strength(-1,114514).air());
        this.registerDefaultState(this.getStateDefinition().any().setValue(COLOR,1));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(COLOR);
    }
}
