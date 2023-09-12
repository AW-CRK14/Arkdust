package com.ardc.arkdust.worldgen.feature.pre;

import com.ardc.arkdust.worldgen.config.SingleBlockPlacementConfig;
import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SingleBlockOnTarget extends Feature<SingleBlockPlacementConfig> {
    public SingleBlockOnTarget(Codec<SingleBlockPlacementConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, SingleBlockPlacementConfig config) {
         pos = config.surface ? world.getHeightmapPos(Heightmap.Type.WORLD_SURFACE_WG,pos) : new BlockPos(pos.getX(),config.y,pos.getZ());
         if(config.canPlaceOn.contains(world.getBlockState(pos.below()).getBlock())){
             int yOffset = config.y;
             if(config.yRandomOffset <= 0){ yOffset += random.nextInt(config.yRandomOffset*2+1) - config.yRandomOffset;}
             pos = pos.relative(Direction.UP,yOffset);
             if(pos.getY() <= 0) pos = new BlockPos(pos.getX(),1,pos.getZ());
             world.setBlock(pos, config.setState.get(random.nextInt(config.setState.size())),2);
             return true;
         }
         return false;
    }
}
