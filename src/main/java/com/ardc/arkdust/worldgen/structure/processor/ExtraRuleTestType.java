package com.ardc.arkdust.worldgen.structure.processor;

import com.ardc.arkdust.worldgen.structure.processor.rule_entry.RandomBlockExcludeRuleTest;
import net.minecraft.world.gen.feature.template.IRuleTestType;

public interface ExtraRuleTestType extends IRuleTestType {
    IRuleTestType<RandomBlockExcludeRuleTest> RANDOM_BLOCK_LIST_EXCLUDE_TEST = IRuleTestType.register("random_block_exclude", RandomBlockExcludeRuleTest.CODEC);
}
