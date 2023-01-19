package com.ardc.arkdust.worldgen.structure.structure_piece;

import com.ardc.arkdust.resourcelocation.LootTable;
import com.ardc.arkdust.worldgen.structure.StructurePieceTypeAdd;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class BluePrintBoxPiece {
    public static class Piece extends StructurePiece {

        public Piece(BlockPos setPos) {
            super(StructurePieceTypeAdd.BLUE_PRINT_BOX,0);
            this.boundingBox = new MutableBoundingBox(setPos.getX(),setPos.getY(),setPos.getZ(),setPos.getX(),setPos.getY(),setPos.getZ());
        }

        public Piece(TemplateManager p_i51343_1_, CompoundNBT p_i51343_2_) {
            super(StructurePieceTypeAdd.BLUE_PRINT_BOX, p_i51343_2_);
        }

        @Override
        protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        }

        @Override
        public boolean postProcess(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int i = iSeedReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, this.boundingBox.x0, this.boundingBox.z0);
            BlockPos.Mutable pos = new BlockPos.Mutable(boundingBox.x0,Math.max(boundingBox.y0 + i - 64,2),boundingBox.z0);
//            System.out.println("box load! In" + pos.immutable());
//            iSeedReader.setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);
            if(random.nextBoolean())
                return this.createChest(iSeedReader,mutableBoundingBox,random,pos.immutable(), LootTable.CW_BLUEPRINT_BOX,Blocks.CHEST.defaultBlockState());
            return this.createChest(iSeedReader,mutableBoundingBox,random,pos.immutable(), LootTables.DESERT_PYRAMID,Blocks.CHEST.defaultBlockState());
        }
    }
}

