package com.ardc.arkdust.worldgen.feature.faultline;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class FaultlineStoneOakTreeFeature extends Feature<NoneFeatureConfiguration> {
    private static final Logger LOGGER = LogManager.getLogger();

    public FaultlineStoneOakTreeFeature(Codec<NoneFeatureConfiguration> p_i231953_1_) {
        super(p_i231953_1_);
    }

    private static final List<BlockPos> list = Arrays.asList(new BlockPos(3,3,4),new BlockPos(3,2,3),new BlockPos(2,2,2),new BlockPos(2,2,2),new BlockPos(3,2,3),new BlockPos(1,1,1),new BlockPos(1,1,1));

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        int index = context.random().nextInt(list.size());
        BlockPos relative = list.get(index);
        BlockPos pos = context.origin().offset(-relative.getX(),0,-relative.getZ());
//        pos = iSeedReader.getHeightmapPos(Heightmap.Type.WORLD_SURFACE,pos.offset(-relative.getX()-6+random.nextInt(13),0,-relative.getZ()-6+random.nextInt(13)));
//        pos = context.level().getHeightmapPos(Heightmap.Types.WORLD_SURFACE,pos.offset(-relative.getX(),0,-relative.getZ()));
        Block b = context.level().getBlockState(pos.below()).getBlock();
        if(b.equals(Blocks.GRAVEL) || b.equals(Blocks.COBBLESTONE)){
//            LOGGER.warn("[ArdTempoDebug-WorldGen-Feature-StoneOakTree]Code run.For default pos:{}",pos);
//            LOGGER.warn("[ArdTempoDebug-WorldGen-Feature-StoneOakTree]Check index:{} for relative pos:{}",index,relative);
            pos = pos.offset(0,-relative.getY(),0);
            StructureTemplate template = context.level().getLevel().getStructureManager().getOrCreate(new ResourceLocation(Utils.MOD_ID,"biomefeature/faultline/stone_oak_tree_" + index));
            StructurePlaceSettings settings = new StructurePlaceSettings().setRotationPivot(relative).setRotation(Rotation.getRandom(context.random())).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
            //                LOGGER.warn("[ArdTempoDebug-WorldGen-Feature-StoneOakTree]Relative pos fall to pos:{}",pos);
            template.placeInWorld(context.level(),pos,pos,settings,context.random(),2);
            for (StructureTemplate.StructureBlockInfo info : template.filterBlocks(pos,settings,Blocks.COARSE_DIRT)){
                if (context.random().nextFloat() <= 0.6F)
                    context.level().setBlock(info.pos(),Blocks.COARSE_DIRT.defaultBlockState(),2);
            }
            for (StructureTemplate.StructureBlockInfo info : template.filterBlocks(pos,settings,Blocks.OAK_LOG)){
                if(context.random().nextFloat() <= 0.15F)
                    context.level().setBlock(info.pos(), BlockRegistry.stone_oak_log.get().defaultBlockState().setValue(BlockStateProperties.AXIS,info.state().getValue(BlockStateProperties.AXIS)),2);
            }
            for (StructureTemplate.StructureBlockInfo info : template.filterBlocks(pos,settings,Blocks.OAK_WOOD)){
                if(context.random().nextFloat() <= 0.15F)
                    context.level().setBlock(info.pos(), BlockRegistry.stone_oak_wood.get().defaultBlockState().setValue(BlockStateProperties.AXIS,info.state().getValue(BlockStateProperties.AXIS)),2);
            }
            return true;

        }
        return false;
    }
}
