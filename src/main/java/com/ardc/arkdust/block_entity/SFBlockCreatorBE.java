package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SFBlockCreatorBE extends BlockEntity {
    public int count = 0;
    public SFBlockCreatorBE(BlockPos pos, BlockState state) {
        super(TileEntityTypeRegistry.SF_BLOCK_CREATOR_BE.get(),pos,state);
    }
    
    public int increaseCount(int number){
        count += number;
        setChanged();
        return count;
    }

    public void clean(){
        count = 0;
        setChanged();
    }


    @Override
    public void load(CompoundTag nbt) {
        count = nbt.getInt("counter");
        super.load(nbt);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("counter", count);
    }
}
