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
    public GenerationStage.Decoration step() {//与生成的位置有关，大概。
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
//    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {//生成怪物列表
//        return STRUCTURE_MONSTERS;
//    }
//
//    @Override
//    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {//生成生物列表
//        return STRUCTURE_CREATURES;
//    }


    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
                                     Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);//获取生成坐标（<<为二进制左移四位等价于*16）
        int landHeight = chunkGenerator.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);//获取指定坐标的高度

        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));//获取此位置的地表坐标

        return topBlock.getFluidState().isEmpty() && biome.getBaseTemperature() <= 1.2F;//获取此位置是否为流体（防止生成在水上）
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
            // 将区块转换为我们能使用的坐标
            int x = chunkX * 16 + 7;
            int z = chunkZ * 16 + 7;

            /*
             * 我们将其传入addPieces以告诉它在哪里生成结构
             * 如果addPieces的最后一个参数为true, blockpos的Y值会被忽略而
             * 结构将会生成于地形高度.将此参数设置为否
             * 使结构强制生成在blockpos的Y值处.此处可供你选择!
             */
            BlockPos centerPos = new BlockPos(x, 0, z);

            /*
             * 如果你正在制造下届结构,你或许会想将结构生成在岩层上方(大概是指露出地表)
             * 最好的方法是使用getBaseColumn方法(此方法在chuckGenerator下)来捕捉结构所在的x与z位置的方块竖列.
             * 然后遍历以找到上方为空气方块的地面并将结构的y值设置为其.
             * 确保将JigsawManager.addPieces中的布尔值设置为否(指的应该是p_242837_9_)
             * 以使结构生成在方块坐标的y值而不是在基岩层.
             */
            //IBlockReader blockReader = chunkGenerator.getBaseColumn(blockpos.getX(), blockpos.getZ());
            //结构所要做的就是调用这个方法从而将其变成一个拼图的基础
            JigsawManager.addPieces(
                    dynamicRegistryManager,
                    new VillageConfig(() -> UndertreeBlueprintPool.pool, 10),
//                    new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                    // 这是初始建筑模板池json文件的读取路径.
                    //
                    // 注意，"structure_tutorial:run_down_house/start_pool" 的路径意味着
                    // 游戏将会自动寻找以下路径的建筑模板池:
                    // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
                    // 这就是为什么你的建筑模板池应该放在"data/<modid>/worldgen/template_pool/<the path to the pool here>"路径下
                    // 因为游戏会自动在worldgen/template_pool寻找该池.
//                            .get(new ResourceLocation(Utils.MOD_ID, "undertree_blueprint/start_pool")),

                    // 此参数用于设置拼图结构可以从中心递归出多少子结构。
                    // 我们的结构只有一片而且并不会递归，所以1或更多都不会造成影响。
                    // 然而，我建议你将此值设为一个相对宽松的值如10，以便人们轻松地使用数据包来为你的结构添加增添结构。
                    // 但不要在递归结构中将此值设置得太高而导致你的服务器因为成百上千个生成请求而崩溃!
//                            10),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    centerPos, // 结构位置。如果最后一个参数为true，y值将会被忽略。
                    this.pieces, //在调用此方法后列表将会被拼图结构填充。
                    this.random,
                    false, //特殊的村庄边界调整。它……难以描述，将此值设置为false使你的生成结构不会相互交错。
                    // 不相交或完全包含会使子结构保持完好。这样更加简单。
                    true);  //是否放置在地表。将此值设置为false以使结构生成在传入的y位置。在下届生成结构时一定要将此值设为false，否则高度的自动运算会将其放置在顶层基岩上方。
            this.pieces.add(new BluePrintBoxPiece.Piece(centerPos.offset(3,3,3),16));//TODO 蓝图宝箱结构片区

            // **以下两行是可选的**
            //
            // 在这里，你可以使用this.pieces做一些有趣的事
            // 比如无故将中心五十个方块清除,清除结构重复部分
            // 或添加其它结构使之只存在一个，诸如此类。但你没有每个片区的方块数据因为它只保存了
            // 每个片区的大小和位置。方块不久后将会被放入JigsawManager。
            //
            // 既然如此，我们使用`piece.offset`(此处使用move)来让结构抬升一格使其生成在地面上方而非水中或沉入土地
            // 然后使用`piece.getBoundingBox().y0让我们的边界框下降一格以使周围的土地下沉一些而不遮挡住门
            // 你也可以抬高使结构被埋在地下。This bounding box stuff with land is only for structures
            // that you added to Structure.NOISE_AFFECTING_FEATURES field handles adding land around the base of structures.
            //
            // By lifting the house up by 1 and lowering the bounding box, the land at bottom of house will now be
            // flush with the surrounding terrain without blocking off the doorstep.
            this.pieces.forEach(piece -> piece.move(0, 1, 0));
            this.pieces.forEach(piece -> piece.getBoundingBox().y0 -= 1);

            // 在默认情况下，一个结构会产生在起始坐标的一个角上并会围着这个角随机旋转。
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

            //用作Debug，查看建筑生成的位置
            LogManager.getLogger().log(Level.DEBUG, "Rundown House at " +
                    this.pieces.get(0).getBoundingBox().x0 + " " +
                    this.pieces.get(0).getBoundingBox().y0 + " " +
                    this.pieces.get(0).getBoundingBox().z0);
        }
    }
}
