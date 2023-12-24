package com.ardc.arkdust.worldgen.biome;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.BlockRegistry;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.Nullable;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.Random;
import java.util.function.Consumer;

public class CommonWorldRegion extends Region {
    public CommonWorldRegion(int weight) {
        super(new ResourceLocation(Utils.MOD_ID,"cw_region_provider"), RegionType.OVERWORLD,weight);//weight������Ⱥϵ����Լ���� Ҳ������mod���������
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        super.addBiomes(registry, mapper);
        this.addBiome(mapper,
                Climate.Parameter.span(0.95F, 1.0F),//�¶�
                Climate.Parameter.span(-2.0F, -1.55F),//ʪ��
                Climate.Parameter.span(0.165F, 1.8F),//��½��
                Climate.Parameter.span(1.75F,1.8F),//��ʴ��(���)
                Climate.Parameter.span(0.45F, 0.65F),//�����
                Climate.Parameter.span(0.25F, 0.8F),//���
                0.99F,BiomeKey.CW$FAULT_LINE);
    }

    public static final SurfaceRules.RuleSource COBBLESTONE = makeStateRule(Blocks.COBBLESTONE);
    public static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.COBBLESTONE);
    public static final SurfaceRules.RuleSource DIORITE = makeStateRule(Blocks.DIORITE);
    public static final SurfaceRules.RuleSource ANDESITE = makeStateRule(Blocks.ANDESITE);
    public static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    public static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    public static SurfaceRules.RuleSource build(){
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(BiomeKey.CW$FAULT_LINE),
                SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.hole()),
                        SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 3, CaveSurface.FLOOR),new WastelandSurfaceLayer())
        )));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
//        Registry.register(BuiltInRegistries.MATERIAL_RULE,)
    }

    public static void ruleSourceRegBootstrap(){
        Registry.register(BuiltInRegistries.MATERIAL_RULE,new ResourceLocation(Utils.MOD_ID,"wasteland_surface"),WastelandSurfaceLayer.CODEC);
    }

    public static class WastelandSurfaceLayer implements SurfaceRules.RuleSource{
        public static final Codec<WastelandSurfaceLayer> CODEC = Codec.unit(WastelandSurfaceLayer::new);
        @Override
        public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
            return new KeyDispatchDataCodec<>(CODEC);
        }

        @Override
        public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
            return new Rule(context);
        }

        public record Rule(SurfaceRules.Context context) implements SurfaceRules.SurfaceRule{
//            public static double max = 0;
//            public static double min = 0;
            public BlockState tryApply(int x, int y, int z) {
                if(context.blockY > 110){
                    if (context.stoneDepthAbove < 2) return Blocks.AIR.defaultBlockState();
                    float chance = Math.min(context.blockY-110,10) * 0.08F;
                    return new Random(context.pos.asLong()).nextFloat() < chance ? Blocks.STONE.defaultBlockState() : Blocks.MYCELIUM.defaultBlockState();
                }
                if(context.blockY >= 104) return context.stoneDepthAbove == 1 ? Blocks.MYCELIUM.defaultBlockState() : Blocks.COARSE_DIRT.defaultBlockState();//���ڸ߶�Ϊ99��98�ķ��飬�����Ϊɰ�����˿
                if(context.blockY >= 98) return Blocks.COARSE_DIRT.defaultBlockState();//�߶�Ϊ97��95�ķ����滻Ϊɰ��
                double noise = context.randomState.getOrCreateNoise(Noises.PILLAR).getValue(context.blockX,0,context.blockZ);//ʹ����NoiseThresholdConditionSource������������������ȡλ�õ�����ֵ����һֵһ��λ��+-0.75֮�䣬��ͬ����������������
                if (noise > 0.6D) {//��������ֵ�Ĳ�ͬ��Χ���ɲ�ͬ�ķ���
                    //���Ǵ�����getState���������������ж�Ŀ�귽�������Ϸ����Ǵ��Ϸ�
                    return getState(Blocks.MYCELIUM.defaultBlockState(),Blocks.COARSE_DIRT.defaultBlockState());
                }else if(noise > 0.3D){
                    //���ض������ڽ����������ʹ����������֮���й���Ч����
                    return new Random(context.pos.asLong()).nextFloat() * 0.3F < noise - 0.3D ? getState(Blocks.MYCELIUM.defaultBlockState(),Blocks.COARSE_DIRT.defaultBlockState()) : getState(Blocks.GRAVEL.defaultBlockState(),Blocks.ANDESITE.defaultBlockState());
                } else if (noise > 0.2D) {
                    return getState(Blocks.GRAVEL.defaultBlockState(),Blocks.ANDESITE.defaultBlockState());
                }else if (noise > -0.2D){
                    return new Random(context.pos.asLong()).nextFloat() * 0.4F < noise + 0.2D ? getState(Blocks.GRAVEL.defaultBlockState(),Blocks.ANDESITE.defaultBlockState()) : getState(Blocks.ANDESITE.defaultBlockState(),Blocks.DIRT.defaultBlockState());
                } else if (noise > -0.6D) {
                    return new Random(context.pos.asLong()).nextFloat() * 0.5F < noise + 0.8D ? getState(Blocks.ANDESITE.defaultBlockState(),Blocks.DIRT.defaultBlockState()) : null;
                } else {
                    return new Random(context.pos.asLong()).nextFloat() < 0.1F ? getState(Blocks.ANDESITE.defaultBlockState(),Blocks.DIRT.defaultBlockState()) : null;
                }
            }

            private BlockState getState(BlockState surfaceState,BlockState groundState){
                if(context.stoneDepthAbove > 3 || context.blockY <= 56){//���λ����ȴ���3����λ�õ���y=56���򷵻�null���⽫ʹ�����Ĭ������
                    return null;
                }else if(context.stoneDepthAbove <= 1){//����Ǳ�㷽�飬���ص�һ��
                    return surfaceState;
                }else {//����Ǵα�㷽�飬���صڶ���
                    return groundState;
                }
            }
        }
    }
}
