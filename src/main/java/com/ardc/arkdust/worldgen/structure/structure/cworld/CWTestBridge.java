package com.ardc.arkdust.worldgen.structure.structure.cworld;

@Deprecated
public class CWTestBridge{
//    public static final Codec<CWTestBridge> CODEC = simpleCodec(CWTestBridge::new);
//
//    public CWTestBridge(Structure.StructureSettings p_i231997_1_) {
//        super(p_i231997_1_);
//    }
//
//    @Override
//    public GenerationStep.Decoration step() {//与生成的位置有关，大概。
//        return GenerationStep.Decoration.SURFACE_STRUCTURES;
//    }
//
//    @Override
//    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
//
//        BlockPos center = PosHelper.randomSkew(context,12);
//        Holder<Biome> biome = context.biomeSource().getNoiseBiome(QuartPos.fromBlock(center.getX()), QuartPos.fromBlock(center.getY()), QuartPos.fromBlock(center.getZ()), context.randomState().sampler());
//        if(StructureHelper.isEachPlaceAvailable(context.chunkGenerator(), Heightmap.Types.WORLD_SURFACE_WG,3, PosHelper.getCenterAndSquareVertexPos(center,6,false,true),context.heightAccessor(),context.randomState()) && biome.value().getBaseTemperature() >= 1.2F) {
//            JigsawStructure structure = new JigsawStructure();
//
//            return Optional.of(new GenerationStub(center, (builder) -> builder.addPiece(new CWOldHouse.Piece(context.structureTemplateManager(), context.random(), center))));
//        }
//        return Optional.empty();
////        TemplateStructurePiece piece =
//    }
//
//    @Override
//    public StructureType<?> type() {
//        return CW$TEST_BRIDGE;
//    }
//
//    public static class Start extends StructureStart<NoFeatureConfig> {
//
//        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
//            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
//        }
//
//        @Override
//        public void generatePieces(DynamicRegistries registries, ChunkGenerator generator, TemplateManager manager, int x, int z, Biome biome, NoFeatureConfig featureConfig) {
//            BlockPos pos = new BlockPos(x*16+4,0,z*16+4);
//
//            JigsawManager.addPieces(registries,
//                    new VillageConfig(()->registries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(Utils.MOD_ID,"cw/test_bridge/main")),8),
//                    AbstractVillagePiece::new,generator,manager,pos,this.pieces,this.random,false,true);
//
//            this.calculateBoundingBox();
//        }
//    }
}
