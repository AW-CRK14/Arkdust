package com.ardc.arkdust.worldgen.feature.structure_piece;

import com.ardc.arkdust.worldgen.feature.StructurePieceTypeAdd;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class BluePrintBoxPiece {
    public static class Piece extends StructurePiece {
        private int possibility = 32;

        public Piece(BlockPos setPos,int possibility) {
            super(StructurePieceTypeAdd.BLUE_PRINT_BOX,0);
            this.possibility = possibility;
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
            BlockPos.Mutable pos = new BlockPos.Mutable(boundingBox.x0,boundingBox.y0,boundingBox.z0);
            iSeedReader.setBlock(pos, Blocks.CHEST.defaultBlockState(), 3);
            int c = random.nextInt();
            if(c < this.possibility)
                return this.createChest(iSeedReader,mutableBoundingBox,random,pos.immutable(), LootTables.CAT_MORNING_GIFT,Blocks.CHEST.defaultBlockState());
            return this.createChest(iSeedReader,mutableBoundingBox,random,pos.immutable(), LootTables.DESERT_PYRAMID,Blocks.CHEST.defaultBlockState());
        }
    }
}

