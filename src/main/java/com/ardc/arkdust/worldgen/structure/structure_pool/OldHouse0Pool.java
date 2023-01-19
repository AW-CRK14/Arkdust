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
//    //���ǳ�ʼ����ģ���json�ļ��Ķ�ȡ·��.
//
//    // ע�⣬"structure_tutorial:run_down_house/start_pool" ��·����ζ��
//    // ��Ϸ�����Զ�Ѱ������·���Ľ���ģ���:
//    // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
//    // �����Ϊʲô��Ľ���ģ���Ӧ�÷���"data/<modid>/worldgen/template_pool/<the path to the pool here>"·����
//    // ��Ϊ��Ϸ���Զ���worldgen/template_poolѰ�Ҹó�.
//                            .get(new ResourceLocation(Utils.MOD_ID, "undertree_blueprint/start_pool")),
//
//    // �˲�����������ƴͼ�ṹ���Դ����ĵݹ�������ӽṹ��
//    // ���ǵĽṹֻ��һƬ���Ҳ�����ݹ飬����1����඼�������Ӱ�졣
//    // Ȼ�����ҽ����㽫��ֵ��Ϊһ����Կ��ɵ�ֵ��10���Ա��������ɵ�ʹ�����ݰ���Ϊ��Ľṹ�������ṹ��
//    // ����Ҫ�ڵݹ�ṹ�н���ֵ���õ�̫�߶�������ķ�������Ϊ�ɰ���ǧ���������������!
//                            10)
}
