package com.ardc.arkdust.worldgen.structure.processor;

import com.ardc.arkdust.helper.BlockHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.resourcelocation.Tag;
import com.ardc.arkdust.worldgen.structure.processor.rule_entry.RandomRuleTest;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.template.*;

import java.util.Random;

public class PreparedProcessor {
    private static final Random random = new Random();
    public static final StructureProcessor CHANGE_STONE_BRICKS = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueRuleTest.INSTANCE, Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_GROUND.getRandomElement(random).defaultBlockState())));
    public static final StructureProcessor CHANGE_GLOWSTONE = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GLOWSTONE, 0.5F), AlwaysTrueRuleTest.INSTANCE, BlockHelper.getRandomBlock(random,Blocks.POLISHED_ANDESITE,Blocks.ANDESITE,Blocks.ANDESITE,Blocks.COBBLESTONE,Blocks.COBBLESTONE,Blocks.MOSSY_COBBLESTONE).defaultBlockState())));
//    public static final StructureProcessor CHANGE_CONCRETE = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new TagMatchRuleTest(Tag.Blocks.CONCRETE), new RandomRuleTest(0.4F),));
    public static final StructureProcessor CHANGE_LIGHT_GRAY_CONCRETE = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.LIGHT_GRAY_CONCRETE,0.4F),AlwaysTrueRuleTest.INSTANCE,BlockHelper.getRandomBlock(random,Blocks.ANDESITE,Blocks.COBBLESTONE,Blocks.LIGHT_GRAY_CONCRETE_POWDER,Blocks.GRAY_CONCRETE,Blocks.AIR).defaultBlockState())));
    public static final StructureProcessor CHANGE_DIRTY_CONCRETE = new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(BlockRegistry.dirty_concrete.get(),0.4F),AlwaysTrueRuleTest.INSTANCE,Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_DIRTY_CONCRETE.getRandomElement(random).defaultBlockState())));

}
