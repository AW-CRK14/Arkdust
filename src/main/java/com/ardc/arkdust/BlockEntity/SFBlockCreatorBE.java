package com.ardc.arkdust.BlockEntity;

import com.ardc.arkdust.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class SFBlockCreatorBE extends TileEntity {
    public int count = 0;
    public SFBlockCreatorBE() {
        super(TileEntityTypeRegistry.aSFBlockCreatorBE.get());
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
    public void load(BlockState state, CompoundNBT nbt) {
        count = nbt.getInt("counter");
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("counter", count);
        return super.save(compound);
    }
}
