package com.ardc.arkdust.worldgen.feature;

import com.ardc.arkdust.worldgen.feature.structure_piece.BluePrintBoxPiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface StructurePieceTypeAdd{
    IStructurePieceType BLUE_PRINT_BOX = IStructurePieceType.setPieceId(BluePrintBoxPiece.Piece::new,"arkdust:BPB");
}
