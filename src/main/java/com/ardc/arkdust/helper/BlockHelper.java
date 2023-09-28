package com.ardc.arkdust.helper;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

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

        public Block getRandomBlock(RandomSource random){
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

    public static Block getRandomBlock(Random random,Block... blocks){
        return blocks[random.nextInt(blocks.length)];
    }



}
