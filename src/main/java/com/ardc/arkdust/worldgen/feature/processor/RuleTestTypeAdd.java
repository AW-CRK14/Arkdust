package com.ardc.arkdust.worldgen.feature.processor;

import com.ardc.arkdust.worldgen.feature.processor.rule_entry.RandomBlockExcludeRuleTest;
import net.minecraft.world.gen.feature.template.IRuleTestType;

public interface RuleTestTypeAdd extends IRuleTestType {
    IRuleTestType<RandomBlockExcludeRuleTest> RANDOM_BLOCK_LIST_EXCLUDE_TEST = IRuleTestType.register("random_block_exclude", RandomBlockExcludeRuleTest.CODEC);
}
