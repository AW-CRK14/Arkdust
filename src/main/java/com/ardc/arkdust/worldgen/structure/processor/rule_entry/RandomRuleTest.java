package com.ardc.arkdust.worldgen.structure.processor.rule_entry;

import com.ardc.arkdust.worldgen.structure.processor.ExtraRuleTestType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

import java.util.Random;

public class RandomRuleTest extends RuleTest {
    public static final Codec<RandomRuleTest> CODEC =
            RecordCodecBuilder.
                    create((p_237118_0_) ->
                            p_237118_0_.group(
                                    Codec.FLOAT.fieldOf("probability").
                                            forGetter((x) -> x.probability)
                            ).apply(p_237118_0_, RandomRuleTest::new));
    private final float probability;
    public RandomRuleTest(float probability) {
        this.probability = probability;
    }

    public boolean test(BlockState state, Random random) {
        return random.nextFloat() <= probability;
    }

    protected IRuleTestType<?> getType() {
        return ExtraRuleTestType.RANDOM_BLOCK_LIST_EXCLUDE_TEST;
    }
}
