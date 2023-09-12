package com.ardc.arkdust.worldgen.feature.faultline;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.helper.BlockStateHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class FaultlineStoneOakTreeFeature extends Feature<NoFeatureConfig> {
    private static final Logger LOGGER = LogManager.getLogger();

    public FaultlineStoneOakTreeFeature(Codec<NoFeatureConfig> p_i231953_1_) {
        super(p_i231953_1_);
    }

    private static final List<BlockPos> list = Arrays.asList(new BlockPos(3,3,4),new BlockPos(3,2,3),new BlockPos(2,2,2),new BlockPos(2,2,2),new BlockPos(3,2,3),new BlockPos(1,1,1),new BlockPos(1,1,1));

    @Override
    public boolean place(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig config) {
        int index = random.nextInt(list.size());
        BlockPos relative = list.get(index);
//        pos = iSeedReader.getHeightmapPos(Heightmap.Type.WORLD_SURFACE,pos.offset(-relative.getX()-6+random.nextInt(13),0,-relative.getZ()-6+random.nextInt(13)));
        pos = iSeedReader.getHeightmapPos(Heightmap.Type.WORLD_SURFACE,pos.offset(-relative.getX(),0,-relative.getZ()));
        Block b = iSeedReader.getBlockState(pos.below()).getBlock();
        if(b.equals(Blocks.GRAVEL) || b.equals(Blocks.COBBLESTONE)){
//            LOGGER.warn("[ArdTempoDebug-WorldGen-Feature-StoneOakTree]Code run.For default pos:{}",pos);
//            LOGGER.warn("[ArdTempoDebug-WorldGen-Feature-StoneOakTree]Check index:{} for relative pos:{}",index,relative);
            pos = pos.offset(0,-relative.getY(),0);
            Template template = iSeedReader.getLevel().getStructureManager().get(new ResourceLocation(Utils.MOD_ID,"biomefeature/faultline/stone_oak_tree_" + index));
            PlacementSettings settings = new PlacementSettings().setRotationPivot(relative).setRotation(Rotation.getRandom(random)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR);
            if(template != null){
//                LOGGER.warn("[ArdTempoDebug-WorldGen-Feature-StoneOakTree]Relative pos fall to pos:{}",pos);
                template.placeInWorld(iSeedReader,pos,settings,random);
                for (Template.BlockInfo info : template.filterBlocks(pos,settings,Blocks.COARSE_DIRT)){
                    if (random.nextFloat() <= 0.6F)
                        iSeedReader.setBlock(info.pos,Blocks.COARSE_DIRT.defaultBlockState(),2);
                }
                for (Template.BlockInfo info : template.filterBlocks(pos,settings,Blocks.OAK_LOG)){
                    if(random.nextFloat() <= 0.15F)
                        iSeedReader.setBlock(info.pos, BlockRegistry.stone_oak_log.get().defaultBlockState().setValue(BlockStateProperties.AXIS,info.state.getValue(BlockStateProperties.AXIS)),2);
                }
                for (Template.BlockInfo info : template.filterBlocks(pos,settings,Blocks.OAK_WOOD)){
                    if(random.nextFloat() <= 0.15F)
                        iSeedReader.setBlock(info.pos, BlockRegistry.stone_oak_wood.get().defaultBlockState().setValue(BlockStateProperties.AXIS,info.state.getValue(BlockStateProperties.AXIS)),2);
                }
                return true;
            }

        }
        return false;
    }
}
