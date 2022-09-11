package com.ardc.arkdust.worldgen.feature.structure.cworld;

import com.ardc.arkdust.worldgen.feature.ArdStructureAddInfo;
import com.ardc.arkdust.worldgen.feature.structure_piece.BluePrintBoxPiece;
import com.ardc.arkdust.worldgen.feature.structure_pool.UndertreeBlueprintPool;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class UnderTreeBlueprintBox extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {

    public UnderTreeBlueprintBox(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public GenerationStage.Decoration step() {//�����ɵ�λ���йأ���š�
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

//    public String getFeatureName() {
//        return "undertree_blueprint";
//    }


//    private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
//            new MobSpawnInfo.Spawners(EntityType.ILLUSIONER, 10, 1, 4),
//            new MobSpawnInfo.Spawners(EntityType.VINDICATOR, 10, 1, 4)
//    );
//
//    private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(
//            new MobSpawnInfo.Spawners(EntityType.SHEEP, 10, 1, 5),
//            new MobSpawnInfo.Spawners(EntityType.RABBIT, 10, 1, 4)
//    );
//    @Override
//    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {//���ɹ����б�
//        return STRUCTURE_MONSTERS;
//    }
//
//    @Override
//    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {//���������б�
//        return STRUCTURE_CREATURES;
//    }


    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
                                     Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);//��ȡ�������꣨<<Ϊ������������λ�ȼ���*16��
        int landHeight = chunkGenerator.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);//��ȡָ������ĸ߶�

        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));//��ȡ��λ�õĵر�����

        return topBlock.getFluidState().isEmpty() && biome.getBaseTemperature() <= 1.2F;//��ȡ��λ���Ƿ�Ϊ���壨��ֹ������ˮ�ϣ�
    }

    @Override
    public int spacing() {
        return 100;
    }

    @Override
    public int separation() {
        return 50;
    }

    @Override
    public int salt() {
        return 114514;
    }


//    @Override
//    public boolean getDefaultRestrictsSpawnsToInside() {
//        return true;
//    }

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
//            this.pieces.add(new UndertreeBlueprintStructurePiece(NoFeatureConfig,0));
            // ������ת��Ϊ������ʹ�õ�����
            int x = chunkX * 16 + 7;
            int z = chunkZ * 16 + 7;

            /*
             * ���ǽ��䴫��addPieces�Ը��������������ɽṹ
             * ���addPieces�����һ������Ϊtrue, blockpos��Yֵ�ᱻ���Զ�
             * �ṹ���������ڵ��θ߶�.���˲�������Ϊ��
             * ʹ�ṹǿ��������blockpos��Yֵ��.�˴��ɹ���ѡ��!
             */
            BlockPos centerPos = new BlockPos(x, 0, z);

            /*
             * ��������������½�ṹ,�������뽫�ṹ�������Ҳ��Ϸ�(�����ָ¶���ر�)
             * ��õķ�����ʹ��getBaseColumn����(�˷�����chuckGenerator��)����׽�ṹ���ڵ�x��zλ�õķ�������.
             * Ȼ��������ҵ��Ϸ�Ϊ��������ĵ��沢���ṹ��yֵ����Ϊ��.
             * ȷ����JigsawManager.addPieces�еĲ���ֵ����Ϊ��(ָ��Ӧ����p_242837_9_)
             * ��ʹ�ṹ�����ڷ��������yֵ�������ڻ��Ҳ�.
             */
            //IBlockReader blockReader = chunkGenerator.getBaseColumn(blockpos.getX(), blockpos.getZ());
            //�ṹ��Ҫ���ľ��ǵ�����������Ӷ�������һ��ƴͼ�Ļ���
            JigsawManager.addPieces(
                    dynamicRegistryManager,
                    new VillageConfig(() -> UndertreeBlueprintPool.pool, 10),
//                    new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                    // ���ǳ�ʼ����ģ���json�ļ��Ķ�ȡ·��.
                    //
                    // ע�⣬"structure_tutorial:run_down_house/start_pool" ��·����ζ��
                    // ��Ϸ�����Զ�Ѱ������·���Ľ���ģ���:
                    // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
                    // �����Ϊʲô��Ľ���ģ���Ӧ�÷���"data/<modid>/worldgen/template_pool/<the path to the pool here>"·����
                    // ��Ϊ��Ϸ���Զ���worldgen/template_poolѰ�Ҹó�.
//                            .get(new ResourceLocation(Utils.MOD_ID, "undertree_blueprint/start_pool")),

                    // �˲�����������ƴͼ�ṹ���Դ����ĵݹ�������ӽṹ��
                    // ���ǵĽṹֻ��һƬ���Ҳ�����ݹ飬����1����඼�������Ӱ�졣
                    // Ȼ�����ҽ����㽫��ֵ��Ϊһ����Կ��ɵ�ֵ��10���Ա��������ɵ�ʹ�����ݰ���Ϊ��Ľṹ�������ṹ��
                    // ����Ҫ�ڵݹ�ṹ�н���ֵ���õ�̫�߶�������ķ�������Ϊ�ɰ���ǧ���������������!
//                            10),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    centerPos, // �ṹλ�á�������һ������Ϊtrue��yֵ���ᱻ���ԡ�
                    this.pieces, //�ڵ��ô˷������б��ᱻƴͼ�ṹ��䡣
                    this.random,
                    false, //����Ĵ�ׯ�߽��������������������������ֵ����Ϊfalseʹ������ɽṹ�����໥����
                    // ���ཻ����ȫ������ʹ�ӽṹ������á��������Ӽ򵥡�
                    true);  //�Ƿ�����ڵر�����ֵ����Ϊfalse��ʹ�ṹ�����ڴ����yλ�á����½����ɽṹʱһ��Ҫ����ֵ��Ϊfalse������߶ȵ��Զ�����Ὣ������ڶ�������Ϸ���
            this.pieces.add(new BluePrintBoxPiece.Piece(centerPos.offset(3,3,3),16));//TODO ��ͼ����ṹƬ��

            // **���������ǿ�ѡ��**
            //
            // ����������ʹ��this.pieces��һЩ��Ȥ����
            // �����޹ʽ�������ʮ���������,����ṹ�ظ�����
            // ����������ṹʹֻ֮����һ����������ࡣ����û��ÿ��Ƭ���ķ���������Ϊ��ֻ������
            // ÿ��Ƭ���Ĵ�С��λ�á����鲻�ú󽫻ᱻ����JigsawManager��
            //
            // ��Ȼ��ˣ�����ʹ��`piece.offset`(�˴�ʹ��move)���ýṹ̧��һ��ʹ�������ڵ����Ϸ�����ˮ�л��������
            // Ȼ��ʹ��`piece.getBoundingBox().y0�����ǵı߽���½�һ����ʹ��Χ�������³�һЩ�����ڵ�ס��
            // ��Ҳ����̧��ʹ�ṹ�����ڵ��¡�This bounding box stuff with land is only for structures
            // that you added to Structure.NOISE_AFFECTING_FEATURES field handles adding land around the base of structures.
            //
            // By lifting the house up by 1 and lowering the bounding box, the land at bottom of house will now be
            // flush with the surrounding terrain without blocking off the doorstep.
            this.pieces.forEach(piece -> piece.move(0, 1, 0));
            this.pieces.forEach(piece -> piece.getBoundingBox().y0 -= 1);

            // ��Ĭ������£�һ���ṹ���������ʼ�����һ�����ϲ���Χ������������ת��
            // and will randomly rotate around that corner, we will center the piece on centerPos instead.
            // This is so that our structure's start piece is now centered on the water check done in isFeatureChunk.
            // Whatever the offset done to center the start piece, that offset is applied to all other pieces
            // so the entire structure is shifted properly to the new spot.
            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for (StructurePiece structurePiece : this.pieces) {
                structurePiece.move(xOffset, 0, zOffset);
            }

            this.calculateBoundingBox();

            //����Debug���鿴�������ɵ�λ��
            LogManager.getLogger().log(Level.DEBUG, "Rundown House at " +
                    this.pieces.get(0).getBoundingBox().x0 + " " +
                    this.pieces.get(0).getBoundingBox().y0 + " " +
                    this.pieces.get(0).getBoundingBox().z0);
        }
    }
}
