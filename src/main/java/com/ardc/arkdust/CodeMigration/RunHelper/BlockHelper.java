package com.ardc.arkdust.CodeMigration.RunHelper;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.Random;

public class BlockHelper {
    public static class BlockAndRegistryObjectList{
        public final List<Block> blockList;
        public final List<RegistryObject<Block>> registryBlockList;

        public BlockAndRegistryObjectList(List<Block> blocks,List<RegistryObject<Block>> registryObjects){
            blockList = blocks;
            registryBlockList = registryObjects;
        }

        public Block getRandomBlock(Random random){
            int allLength = blockList.size() + registryBlockList.size();
            if(allLength <= 0)
                return Blocks.AIR;
            int nextInt = random.nextInt(allLength);
            if(nextInt <= blockList.size() - 1)
                return blockList.get(nextInt);
            else
                return registryBlockList.get(nextInt - blockList.size()).get();
        }
    }
}
