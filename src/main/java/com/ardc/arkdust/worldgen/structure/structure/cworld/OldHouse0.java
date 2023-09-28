package com.ardc.arkdust.worldgen.structure.structure.cworld;

@Deprecated
public class OldHouse0{
//public class OldHouse0 extends Structure<NoFeatureConfig> implements ArdStructureAddInfo {
//
//    public OldHouse0(Codec<NoFeatureConfig> codec) {
//        super(codec);
//    }
//
//    @Override
//    public IStartFactory<NoFeatureConfig> getStartFactory() {
//        return Start::new;
//    }
//
//    @Override
//    public GenerationStage.Decoration step() {//与生成的位置有关，大概。
//        return GenerationStage.Decoration.SURFACE_STRUCTURES;
//    }
//
//    @Override
//    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
//                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
//                                     Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
//        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
//        int landHeight = chunkGenerator.getBaseHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);//获取指定坐标的高度
//
//        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
//        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(Math.max(landHeight-1,5)));//获取此位置的地表坐标
//
//        return topBlock.getFluidState().isEmpty() && biome.getBaseTemperature() <= 1.2F;//获取此位置是否为流体（防止生成在水上）
//    }
//
//    @Override
//    public int spacing() {
//        return 70;
//    }
//
//    @Override
//    public int separation() {
//        return 40;
//    }
//
//    @Override
//    public int salt() {
//        return 108278;
//    }
//
//    @Override
//    public buildMode mode() {
//        return buildMode.OVERWORLD;
//    }
//
//    public static class Start extends StructureStart<NoFeatureConfig> {
//        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
//            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
//        }
//
//        @Override
//        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
////            this.pieces.add(new UndertreeBlueprintStructurePiece(NoFeatureConfig,0));
//            // 将区块转换为我们能使用的坐标
//            int x = chunkX * 16 + 7;
//            int z = chunkZ * 16 + 7;
//            int landHeight = chunkGenerator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
//            float flagFloat = random.nextFloat();
//            int preMove = 0;
//            BlockPos centerPos = new BlockPos(x, Math.max(1,landHeight), z);
//            if(flagFloat <= 0.25F) {
//                JigsawManager.addPieces(
//                        dynamicRegistryManager,
//                        new VillageConfig(() -> OldHouse0Pool.pool_common, 10),
//                        AbstractVillagePiece::new,
//                        chunkGenerator,
//                        templateManagerIn,
//                        centerPos,
//                        this.pieces,
//                        this.random,
//                        false,
//                        false);
//                this.calculateBoundingBox();
//
//                System.out.println(this.boundingBox);
//                BlockPos endPos = StructureHelper.getEndPos(this.boundingBox, centerPos);
//                int laterLandHeight = chunkGenerator.getBaseHeight(endPos.getX(), endPos.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
//                int move = laterLandHeight - landHeight;
////                System.out.println("\n  y move:" + move + "\n  cpos:" + centerPos.toString() + "," + landHeight + "\n  tpos:" + endPos.toString() + "," + laterLandHeight);
//                if (Math.abs(move) >= 3) {
//                    this.pieces.get(0).move(0, move + 1, 0);
//                    preMove = move +1;
//                }
//
//            }else{
//                JigsawManager.addPieces(
//                        dynamicRegistryManager,
//                        new VillageConfig(() -> OldHouse0Pool.pool_broken, 10),
//                        AbstractVillagePiece::new,
//                        chunkGenerator,
//                        templateManagerIn,
//                        centerPos,
//                        this.pieces,
//                        this.random,
//                        false,
//                        false);
//                this.calculateBoundingBox();
//            }
//            Vector3i bcpos = this.pieces.get(0).getBoundingBox().getCenter();
//            this.pieces.add(new BluePrintBoxPiece.Piece(new BlockPos(centerPos.getX(),64,centerPos.getZ())));
//            DirectionAndRotationHelper.PosDirectionRun(centerPos,bcpos,new PosHelper.PosMoveBag(5,-1+preMove,7),false).pieceMove(pieces.get(1));
//            if(flagFloat <= 0.25F) {
//                this.pieces.add(new BluePrintBoxPiece.Piece(new BlockPos(centerPos.getX(), 64, centerPos.getZ())));
//                DirectionAndRotationHelper.PosDirectionRun(centerPos, bcpos, new PosHelper.PosMoveBag(3, 5+preMove, 3), false).pieceMove(pieces.get(2));
//            }
//            this.calculateBoundingBox();
//        }
//    }
}
