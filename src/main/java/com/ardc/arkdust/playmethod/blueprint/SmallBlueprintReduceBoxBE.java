package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SmallBlueprintReduceBoxBE extends BlueprintReduceBoxBE {
    public SmallBlueprintReduceBoxBE(BlockEntityType<? extends BlueprintReduceBoxBE> type, BlockPos pos, BlockState state, int maxCount) {
        super(type,pos,state, maxCount);
    }
}
