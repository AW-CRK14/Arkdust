package com.ardc.arkdust.worldgen.structure.processor;

import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.structure.processor.structure_processor.TagRandomChangeProcessor;
import com.ardc.arkdust.worldgen.structure.processor.structure_processor.WastelandMineshaftProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.Collections;

public class ExtraStructureProcessorType {

    public static void bootstrap(){}
    public static final StructureProcessorType<WastelandMineshaftProcessor> WASTELAND_MINESHAFT_PROCESSOR = StructureProcessorType.register("wasteland_mineshaft_processor", WastelandMineshaftProcessor.CODEC);
    public static final StructureProcessorType<TagRandomChangeProcessor> TAG_RANDOM_CHANGE_PROCESSOR = StructureProcessorType.register("tag_random_change_processor", TagRandomChangeProcessor.CODEC);



    public static final StructureProcessor IGNORE_STRI = new BlockIgnoreProcessor(Collections.singletonList(BlockRegistry.structure_ignore_block.get()));
}
