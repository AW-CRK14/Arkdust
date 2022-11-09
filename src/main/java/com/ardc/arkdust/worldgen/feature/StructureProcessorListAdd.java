package com.ardc.arkdust.worldgen.feature;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.feature.processor.rule_entry.RandomBlockExcludeRuleTest;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.template.*;

public class StructureProcessorListAdd{
    public static final StructureProcessorList INTEGRITY_95 = register("integrity_95", ImmutableList.of(new IntegrityProcessor(0.95F)));
    public static final StructureProcessorList SPIDER_WEB_25 = register("spider_web_25", ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockExcludeRuleTest(Blocks.DIRT, 0.025F, true, true), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState())))));
    public static final StructureProcessorList SPIDER_WEB_75 = register("spider_web_75", ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockExcludeRuleTest(Blocks.DIRT, 0.075F, true, true), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState())))));

//    public static final BlockIgnoreStructureProcessor STRUCTURE_BLOCK_AND_ARD_IGNBLOCK = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_BLOCK, BlockRegistry.structure_ignore_block.get()));
    public static final LazyValue<BlockIgnoreStructureProcessor> STRUCTURE_BLOCK_AND_ARD_IGNBLOCK = new LazyValue<>(()->new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_BLOCK, BlockRegistry.structure_ignore_block.get()))) ;


    private static StructureProcessorList register(String p_244127_0_, ImmutableList<StructureProcessor> p_244127_1_) {
        ResourceLocation resourcelocation = new ResourceLocation(Utils.MOD_ID,p_244127_0_);
        StructureProcessorList structureprocessorlist = new StructureProcessorList(p_244127_1_);
        return WorldGenRegistries.register(WorldGenRegistries.PROCESSOR_LIST, resourcelocation, structureprocessorlist);
    }
}
