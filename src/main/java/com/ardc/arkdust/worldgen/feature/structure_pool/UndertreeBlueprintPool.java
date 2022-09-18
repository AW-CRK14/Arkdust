package com.ardc.arkdust.worldgen.feature.structure_pool;

import com.ardc.arkdust.Utils;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.ProcessorLists;

public class UndertreeBlueprintPool {
    public static JigsawPattern pool =
            new JigsawPattern(
                    new ResourceLocation(Utils.MOD_ID,"undertree_blueprint"),
                    new ResourceLocation("empty"),
                    ImmutableList.of(Pair.of(JigsawPiece.legacy(
                            Utils.MOD_ID + ":cworld/undertree_blueprint/undertree_blueprint_1",
                            ProcessorLists.MOSSIFY_70_PERCENT),1)),
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
