package com.ardc.arkdust.worldgen.feature.pre;

import com.ardc.arkdust.worldgen.config.ListNBTFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.*;

import java.util.Random;

public class ListNBTGenerateFeature extends Feature<ListNBTFeatureConfig> {
    public ListNBTGenerateFeature(Codec<ListNBTFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, ListNBTFeatureConfig config) {
        pos = world.getHeightmapPos(Heightmap.Type.WORLD_SURFACE,pos);
        if(config.allowOn.isEmpty() || config.allowOn.contains(world.getBlockState(pos.below()).getBlock())){

            Template template = world.getLevel().getStructureManager().get(config.getRandomResource(random));
            PlacementSettings settings = new PlacementSettings().setRotation(Rotation.getRandom(random)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).addProcessor(new IntegrityProcessor(config.complete));
            if(template!=null) {
                if (config.moveToCenter)
                    settings.setRotationPivot(template.getSize().offset(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2));
                pos = pos.offset(-template.getSize().getX() / 2, config.yOffset, -template.getSize().getZ() / 2);
                return template.placeInWorld(world,pos,pos,settings,random,2);
            }
        }
        return false;
    }
}
