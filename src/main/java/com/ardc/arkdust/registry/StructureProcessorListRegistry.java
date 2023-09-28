package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.processor.structure_processor.TagRandomChangeProcessor;
import com.ardc.arkdust.worldgen.structure.processor.structure_processor.WastelandMineshaftProcessor;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

import static com.ardc.arkdust.resource.Tag.Blocks.STRUCTURE$TAG_CHANGE_BLACKLIST;

public class StructureProcessorListRegistry {

    private static void register(BootstapContext<StructureProcessorList> context, ResourceKey<StructureProcessorList> key, List<StructureProcessor> processors) {
        context.register(key, new StructureProcessorList(processors));
    }

    public static void bootstrap(BootstapContext<StructureProcessorList> context) {
        HolderGetter<Block> blockHolderGetter = context.lookup(Registries.BLOCK);
        register(context, INTEGRITY_95, ImmutableList.of(new BlockRotProcessor(0.95F)));
        register(context, SPIDER_WEB_25, ImmutableList.of(new TagRandomChangeProcessor(STRUCTURE$TAG_CHANGE_BLACKLIST,true,true,0.025F,()->BlockRegistry.dirty_concrete.get().defaultBlockState())));
        register(context, SPIDER_WEB_25, ImmutableList.of(new TagRandomChangeProcessor(STRUCTURE$TAG_CHANGE_BLACKLIST,true,true,0.075F,()->BlockRegistry.dirty_concrete.get().defaultBlockState())));
        register(context, STRUCTURE_BLOCK_AND_ARD_IGNBLOCK, ImmutableList.of(WastelandMineshaftProcessor.INSTANCE));
    }

    public static final ResourceKey<StructureProcessorList> INTEGRITY_95 = createKey("integrity_95");
    public static final ResourceKey<StructureProcessorList> SPIDER_WEB_25 = createKey("spider_web_25");
    public static final ResourceKey<StructureProcessorList> SPIDER_WEB_75 = createKey("spider_web_75");
    public static final ResourceKey<StructureProcessorList> STRUCTURE_BLOCK_AND_ARD_IGNBLOCK = createKey("structure_block_and_ard_ignblock");

//    public static final net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList INTEGRITY_95 = register("integrity_95", ImmutableList.of(new IntegrityProcessor(0.95F)));
//
//    public static final net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList SPIDER_WEB_25 = register("spider_web_25", ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockExcludeRuleTest(Blocks.DIRT, 0.025F, true, true), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState())))));
//    public static final net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList SPIDER_WEB_75 = register("spider_web_75", ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockExcludeRuleTest(Blocks.DIRT, 0.075F, true, true), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.defaultBlockState())))));
////    public static final BlockIgnoreStructureProcessor STRUCTURE_BLOCK_AND_ARD_IGNBLOCK = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_BLOCK, BlockRegistry.structure_ignore_block.get()));
//
//    public static final Lazy<BlockIgnoreProcessor> STRUCTURE_BLOCK_AND_ARD_IGNBLOCK = Lazy.of(()->new BlockIgnoreProcessor(ImmutableList.of(Blocks.STRUCTURE_BLOCK, BlockRegistry.structure_ignore_block.get()))) ;
//
//    private static net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList register(String p_244127_0_, ImmutableList<StructureProcessor> p_244127_1_) {
//        ResourceLocation resourcelocation = new ResourceLocation(Utils.MOD_ID,p_244127_0_);
//        net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList structureprocessorlist = new net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList(p_244127_1_);
//        return Registry.register(Registries.PROCESSOR_LIST, resourcelocation, structureprocessorlist);
//    }

    private static ResourceKey<StructureProcessorList> createKey(String name) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation(Utils.MOD_ID,name));
    }
}
