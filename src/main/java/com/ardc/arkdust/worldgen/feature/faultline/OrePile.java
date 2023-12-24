package com.ardc.arkdust.worldgen.feature.faultline;

import com.ardc.arkdust.helper.TagHelper;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class OrePile extends Feature<NoneFeatureConfiguration> {

    private final TagKey<Block> tag;
    public OrePile(Codec<NoneFeatureConfiguration> p_i231953_1_, TagKey<Block> oreTag) {
        super(p_i231953_1_);
        this.tag = oreTag;
    }
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        int dia = context.random().nextInt(6)+2;
        BlockPos pos = context.origin().below();
        Block b = context.level().getBlockState(pos).getBlock();
        if(b!= Blocks.MYCELIUM && b!=Blocks.COBBLESTONE && b!= Blocks.STONE && b!=Blocks.GRAVEL)
            return false;
        pos = pos.offset(-dia/2,-1,-dia/2);
        for(int y = 0 ; y <= 2 ; y++){
            for(int x = y ; x < dia - y ; x++){
                for(int z = y ; z < dia - y ; z++){
                    if(y==2 || (x!=0 && x!=dia-y-1) || (z!=0 && z!=dia-y-1)){
                        context.level().setBlock(pos.offset(x,y,z),getBlock(context.random()).defaultBlockState(),2);
                        if(context.random().nextFloat() <= 0.15F){
                            context.level().setBlock(pos.offset(x,y+1,z),getBlock(context.random()).defaultBlockState(),2);
                        }
                    }
                }
            }
        }
        return true;
    }

    private Block getBlock(RandomSource random){
        float f = random.nextFloat();
        if(f<=0.3F){
            return Blocks.STONE;
        }else if (f<=0.55F){
            return Blocks.COBBLESTONE;
        }else if (f<=0.95F){
            return Blocks.GRAVEL;
        }else {
            return TagHelper.getRandomBlockElement(tag,random);
        }
    }
}
