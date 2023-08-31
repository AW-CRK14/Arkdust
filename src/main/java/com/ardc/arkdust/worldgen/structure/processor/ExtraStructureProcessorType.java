package com.ardc.arkdust.worldgen.structure.processor;

import com.ardc.arkdust.worldgen.structure.processor.structure_processor.WastelandMineshaftProcessor;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public interface ExtraStructureProcessorType extends IStructureProcessorType {

    IStructureProcessorType<WastelandMineshaftProcessor> WASTELAND_MINESHAFT_PROCESSOR = IStructureProcessorType.register("wasteland_mineshaft_processor", WastelandMineshaftProcessor.CODEC);
}
