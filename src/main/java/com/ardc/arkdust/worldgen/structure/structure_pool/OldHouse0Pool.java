package com.ardc.arkdust.worldgen.structure.structure_pool;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.ExtraStructureProcessorList;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;

public class OldHouse0Pool {
    public static JigsawPattern pool_common =
            new JigsawPattern(
                    new ResourceLocation(Utils.MOD_ID,"old_house_1"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(JigsawPiece.legacy(Utils.MOD_ID + ":cworld/old_house/old_house_1_0", ExtraStructureProcessorList.INTEGRITY_95), 1)),
                    JigsawPattern.PlacementBehaviour.RIGID
            );

    public static JigsawPattern pool_broken =
            new JigsawPattern(
                    new ResourceLocation(Utils.MOD_ID,"old_house_1"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(
                            Pair.of(JigsawPiece.legacy(Utils.MOD_ID + ":cworld/old_house/old_house_1_1", ExtraStructureProcessorList.SPIDER_WEB_25),1),
                            Pair.of(JigsawPiece.legacy(Utils.MOD_ID + ":cworld/old_house/old_house_1_2", ExtraStructureProcessorList.SPIDER_WEB_25),1),
                            Pair.of(JigsawPiece.legacy(Utils.MOD_ID + ":cworld/old_house/old_house_1_3", ExtraStructureProcessorList.SPIDER_WEB_75),1),
                            Pair.of(JigsawPiece.legacy(Utils.MOD_ID + ":cworld/old_house/old_house_1_4", ExtraStructureProcessorList.SPIDER_WEB_75),1)
                    ),
                    JigsawPattern.PlacementBehaviour.RIGID
            );

//    public JigsawPattern poolB = DynamicRegistries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
//    //这是初始建筑模板池json文件的读取路径.
//
//    // 注意，"structure_tutorial:run_down_house/start_pool" 的路径意味着
//    // 游戏将会自动寻找以下路径的建筑模板池:
//    // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
//    // 这就是为什么你的建筑模板池应该放在"data/<modid>/worldgen/template_pool/<the path to the pool here>"路径下
//    // 因为游戏会自动在worldgen/template_pool寻找该池.
//                            .get(new ResourceLocation(Utils.MOD_ID, "undertree_blueprint/start_pool")),
//
//    // 此参数用于设置拼图结构可以从中心递归出多少子结构。
//    // 我们的结构只有一片而且并不会递归，所以1或更多都不会造成影响。
//    // 然而，我建议你将此值设为一个相对宽松的值如10，以便人们轻松地使用数据包来为你的结构添加增添结构。
//    // 但不要在递归结构中将此值设置得太高而导致你的服务器因为成百上千个生成请求而崩溃!
//                            10)
}
