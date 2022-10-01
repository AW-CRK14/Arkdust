package com.ardc.arkdust.worldgen.feature.processor.rule_entry;

import com.ardc.arkdust.worldgen.feature.processor.RuleTestTypeAdd;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.RuleTest;

import java.util.Random;

public class RandomBlockExcludeRuleTest extends RuleTest {
    public static final Codec<RandomBlockExcludeRuleTest> CODEC =
            RecordCodecBuilder.
                    create((p_237118_0_) ->
                            p_237118_0_.group(
                                    Registry.BLOCK.fieldOf("block").
                                            forGetter((p_237120_0_) -> p_237120_0_.block),
                                    Codec.FLOAT.fieldOf("probability").
                                            forGetter((p_237119_0_) -> p_237119_0_.probability),
                                    Codec.BOOL.fieldOf("exclude").
                                            forGetter((x)->x.exclude),
                                    Codec.BOOL.fieldOf("noair").
                                            forGetter((x)->x.notInAri)
                            ).apply(p_237118_0_, RandomBlockExcludeRuleTest::new));
    private final Block block;
    private final float probability;
    private final boolean exclude;
    private final boolean notInAri;

    public RandomBlockExcludeRuleTest(Block p_i51324_1_, float p_i51324_2_, boolean exclude, boolean noAri) {
        this.block = p_i51324_1_;
        this.probability = p_i51324_2_;
        this.exclude = exclude;
        this.notInAri = noAri;
    }

    public boolean test(BlockState state, Random random) {
        if(notInAri && state.isAir())
            return false;
        if(this.exclude) {
            return (!state.is(this.block)) && random.nextFloat() < this.probability;
        }else {
            return (state.is(this.block)) && random.nextFloat() < this.probability;
        }
    }

    protected IRuleTestType<?> getType() {
        return RuleTestTypeAdd.RANDOM_BLOCK_LIST_EXCLUDE_TEST;
    }
}
