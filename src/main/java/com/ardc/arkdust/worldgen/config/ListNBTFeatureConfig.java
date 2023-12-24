package com.ardc.arkdust.worldgen.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListNBTFeatureConfig implements FeatureConfiguration {
    public static final Codec<ListNBTFeatureConfig> CODEC = RecordCodecBuilder.create((codec)->
        codec.group(
                Codec.STRING.fieldOf("head").forGetter((i)->i.head),
                Codec.STRING.listOf().fieldOf("id").forGetter((i)->i.id),
                Codec.BOOL.fieldOf("cen").forGetter((i)->i.moveToCenter),
                Codec.FLOAT.fieldOf("cpt").forGetter((i)->i.complete)
        ).apply(codec, ListNBTFeatureConfig::new)
    );

    public final String head;
    public final List<String> id;
    public final boolean moveToCenter;
    public final float complete;

    public ListNBTFeatureConfig(String resLocation , int idIndex, boolean toCenter,float complete){
        this(resLocation,IntStream.range(0,idIndex).boxed().map(Object::toString).collect(Collectors.toList()),toCenter,complete);
    }

    public ListNBTFeatureConfig(String resLocation , List<String> ids, boolean toCenter,float complete){
        this.head = resLocation;
        this.id = ids;
        this.moveToCenter = toCenter;
        this.complete = complete;
    }

    public ResourceLocation getRandomResource(RandomSource r){
        return new ResourceLocation(head+id.get(r.nextInt(id.size())));
    }
}
