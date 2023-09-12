package com.ardc.arkdust.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListNBTFeatureConfig implements IFeatureConfig {
    public static final Codec<ListNBTFeatureConfig> CODEC = RecordCodecBuilder.create((codec)->
        codec.group(
                Codec.STRING.fieldOf("head").forGetter((i)->i.head),
                Codec.STRING.listOf().fieldOf("id").forGetter((i)->i.id),
                Codec.INT.fieldOf("y").forGetter((i)->i.yOffset),
                Codec.BOOL.fieldOf("cen").forGetter((i)->i.moveToCenter),
                BlockState.CODEC.listOf().fieldOf("on").forGetter((i)->i.allowOn.stream().map(Block::defaultBlockState).collect(Collectors.toList())),
                Codec.FLOAT.fieldOf("cpt").forGetter((i)->i.complete)
        ).apply(codec, ListNBTFeatureConfig::new)
    );

    public final String head;
    public final List<String> id;
    public final int yOffset;
    public final boolean moveToCenter;
    public final float complete;
    public final List<Block> allowOn;

    public ListNBTFeatureConfig(String resLocation , List<String> ids, List<Block> allowOn, int yOffset, boolean toCenter,float complete){
        this.head = resLocation;
        this.id = ids;
        this.yOffset = yOffset;
        this.moveToCenter = toCenter;
        this.allowOn = allowOn;
        this.complete = complete;
    }

    public ListNBTFeatureConfig(String resLocation , int idIndex, List<Block> allowOn, int yOffset, boolean toCenter,float complete){
        this.head = resLocation;
        this.id = IntStream.range(0,idIndex).boxed().map(Object::toString).collect(Collectors.toList());
        this.yOffset = yOffset;
        this.moveToCenter = toCenter;
        this.allowOn = allowOn;
        this.complete = complete;
    }

    public ListNBTFeatureConfig(String resLocation , List<String> ids, int yOffset, boolean toCenter, Block allow,float complete){
        this.head = resLocation;
        this.id = ids;
        this.yOffset = yOffset;
        this.moveToCenter = toCenter;
        this.allowOn = Collections.singletonList(allow);
        this.complete = complete;
    }


    public ListNBTFeatureConfig(String resLocation , List<String> ids, int yOffset, boolean toCenter, List<BlockState> states,float complete){
        this.head = resLocation;
        this.id = ids;
        this.yOffset = yOffset;
        this.moveToCenter = toCenter;
        this.allowOn = states.stream().map(BlockState::getBlock).collect(Collectors.toList());
        this.complete = complete;
    }

    public ResourceLocation getRandomResource(Random r){
        return new ResourceLocation(head+id.get(r.nextInt(id.size())));
    }
}
