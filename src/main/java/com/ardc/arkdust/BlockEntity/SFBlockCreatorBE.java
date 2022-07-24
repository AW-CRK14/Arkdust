package com.ardc.arkdust.BlockEntity;

import com.ardc.arkdust.TileEntityTypeRegistry;
import net.minecraft.tileentity.TileEntity;

public class SFBlockCreatorBE extends TileEntity {
    public int count = 0;
    public SFBlockCreatorBE() {
        super(TileEntityTypeRegistry.aSFBlockCreatorBE.get());
    }
    
    public int increaseCount(int number){
        count += number;
        return count;
    }

    public void clean(){
        count = 0;
    }


}
