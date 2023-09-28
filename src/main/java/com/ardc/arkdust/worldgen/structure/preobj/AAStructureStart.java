package com.ardc.arkdust.worldgen.structure.preobj;

import com.ardc.arkdust.advanced_obj.AATemJigsawPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;

@Deprecated
public abstract class AAStructureStart{
//public abstract class AAStructureStart extends StructureStart<NoFeatureConfig> {
//    public AAStructureStart(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
//        super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
//    }
//
//    @Override
//    public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {
//        int x = chunkX * 16;
//        int z = chunkZ * 16;
//        int landHeight = chunkGenerator.getBaseHeight(x, z, heightMapType());
//        BlockPos centerPos = getCenterPos(x,z,landHeight);
//        addChildren(templateManagerIn,this.pieces,random,centerPos);
//        this.calculateBoundingBox();
//        if(shouldMoveToCenter()) {
//            StructureHelper.movePieceToCenter(centerPos, this.boundingBox, this.pieces);
//            this.calculateBoundingBox();
//        }
//    }
//
//    public BlockPos getCenterPos(int x,int z,int landHeight){
//        return new BlockPos(x,Math.max(landHeight + yRemove(),1),z);
//    }
//
//    public Heightmap.Type heightMapType(){
//        return Heightmap.Type.WORLD_SURFACE_WG;
//    }
//
//    public boolean shouldMoveToCenter(){
//        return true;
//    }
//
//    public void iterateAATemJigsawPiece(AATemJigsawPiece piece, StructureTemplateManager manager){
//        this.pieces.add(piece);
//        List<AATemJigsawPiece> childrenList = piece.createChildList(manager);
//        this.pieces.addAll(childrenList);
//        if(!childrenList.isEmpty()){
//            childrenList.forEach((i)->iterateAATemJigsawPiece(i,manager));
//        }
//    }
//
//    public abstract void addChildren(TemplateManager templateManagerIn, List<StructurePiece> pieces, SharedSeedRandom random, BlockPos centerPos);
//
//    public abstract int yRemove();
}
