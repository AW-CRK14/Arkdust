package com.ardc.arkdust.model.modelblock;

import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class LifeBlockModel extends PreBlock {
    public static final IntegerProperty COLOR = IntegerProperty.create("color",1,3);
    public LifeBlockModel(){
        super(Properties.copy(Blocks.AIR).noCollission().strength(-1,114514).air());
        this.registerDefaultState(this.getStateDefinition().any().setValue(COLOR,1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(COLOR);
    }
}
