package com.ardc.arkdust.worldgen.feature.faultline;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrePile extends Feature<NoFeatureConfig> {

    private final Tags.IOptionalNamedTag<Block> tag;
    public OrePile(Codec<NoFeatureConfig> p_i231953_1_, Tags.IOptionalNamedTag<Block> oreTag) {
        super(p_i231953_1_);
        this.tag = oreTag;
    }

    private static final List<BlockPos> list = Arrays.asList(new BlockPos(3,3,4),new BlockPos(3,2,3),new BlockPos(2,2,2),new BlockPos(2,2,2),new BlockPos(3,2,3),new BlockPos(1,1,1),new BlockPos(1,1,1));

    @Override
    public boolean place(ISeedReader iSeedReader, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig config) {
        int dia = random.nextInt(6)+2;
        pos = iSeedReader.getHeightmapPos(Heightmap.Type.WORLD_SURFACE_WG, pos).below();
        Block b = iSeedReader.getBlockState(pos).getBlock();
        if(b!=Blocks.MYCELIUM && b!=Blocks.COBBLESTONE && b!= Blocks.STONE && b!=Blocks.GRAVEL)
            return false;
        pos = pos.offset(-dia/2,-1,-dia/2);
        for(int y = 0 ; y <= 2 ; y++){
            for(int x = y ; x < dia - y ; x++){
                for(int z = y ; z < dia - y ; z++){
                    if(y==2 || (x!=0 && x!=dia-y-1) || (z!=0 && z!=dia-y-1)){
                        iSeedReader.setBlock(pos.offset(x,y,z),getBlock(random).defaultBlockState(),2);
                        if(random.nextFloat() <= 0.15F){
                            iSeedReader.setBlock(pos.offset(x,y+1,z),getBlock(random).defaultBlockState(),2);
                        }
                    }
                }
            }
        }
        return true;
    }

    private Block getBlock(Random random){
        float f = random.nextFloat();
        if(f<=0.3F){
            return Blocks.STONE;
        }else if (f<=0.55F){
            return Blocks.COBBLESTONE;
        }else if (f<=0.95F){
            return Blocks.GRAVEL;
        }else {
            return tag.getRandomElement(random);
        }
    }
}
