package com.ardc.arkdust.worldgen.structure.processor.structure_processor;

import com.ardc.arkdust.worldgen.structure.processor.ExtraStructureProcessorType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class TagRandomChangeProcessor extends StructureProcessor {
    public static final Codec<TagRandomChangeProcessor> CODEC = RecordCodecBuilder.create((ins) -> ins.group(
            Codec.STRING.fieldOf("tag")         .forGetter((i)->i.tag.location().toString()),
            Codec.BOOL.fieldOf("ignore")        .forGetter((i)->i.ignore),
            Codec.BOOL.fieldOf("air")           .forGetter((i)->i.changeAir),
            Codec.FLOAT.fieldOf("air")          .forGetter((i)->i.chance),
            BlockState.CODEC.fieldOf("state")   .forGetter((i)->i.block.get())
    ).apply(ins, TagRandomChangeProcessor::new));

    public final TagKey<Block> tag;
    public final boolean ignore;
    public final boolean changeAir;
    public final Supplier<BlockState> block;
    float chance;

    public TagRandomChangeProcessor(TagKey<Block> tag, boolean ignore, boolean changeAir, float chanceProbability, Supplier<BlockState> block){
        this.tag = tag;
        this.ignore = ignore;
        this.changeAir = changeAir;
        this.block = block;
        this.chance = chanceProbability;
    }

    public TagRandomChangeProcessor(String tagName, boolean ignoreElements, boolean changeAir, float chanceProbability, BlockState block){
        this.tag = BlockTags.create(new ResourceLocation(tagName));
        this.ignore = ignoreElements;
        this.changeAir = changeAir;
        this.block = ()->block;
        this.chance = chanceProbability;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(LevelReader reader, BlockPos pos, BlockPos pos2, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo info2, StructurePlaceSettings settings, StructureTemplate template) {
        if((ignore != (info2.state().is(tag) || (changeAir && info2.state().isAir()))) && RandomSource.create(pos2.asLong()).nextFloat() <= chance){
            return new StructureTemplate.StructureBlockInfo(pos,block.get(),new CompoundTag());
        }
        return info2;
    }

    @Override
    protected net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType<?> getType() {
        return ExtraStructureProcessorType.TAG_RANDOM_CHANGE_PROCESSOR;
    }
}
