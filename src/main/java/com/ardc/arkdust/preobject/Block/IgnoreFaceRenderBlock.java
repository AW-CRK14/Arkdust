package com.ardc.arkdust.preobject.Block;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.LazyOptional;

public interface IgnoreFaceRenderBlock {
    FalseOrIntPack getIndex();

    static boolean ignoreOrNot(Block block1, Block block2){
        if(block1 instanceof IgnoreFaceRenderBlock && block2 instanceof IgnoreFaceRenderBlock){
            FalseOrIntPack a = ((IgnoreFaceRenderBlock) block1).getIndex();
            FalseOrIntPack b = ((IgnoreFaceRenderBlock) block2).getIndex();
            return a.i && b.i && a.num == b.num;
        }
        return false;
    }

    class FalseOrIntPack{
        public final boolean i;
        public final int num;
        public FalseOrIntPack(boolean i,int num){
            this.i = i;
            this.num = num;
        }
    }
}
