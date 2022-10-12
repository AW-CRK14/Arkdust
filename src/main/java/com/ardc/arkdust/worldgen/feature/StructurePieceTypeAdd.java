package com.ardc.arkdust.worldgen.feature;

import com.ardc.arkdust.worldgen.feature.structure.cworld.CWGrave;
import com.ardc.arkdust.worldgen.feature.structure.cworld.PixArkLibrary;
import com.ardc.arkdust.worldgen.feature.structure_piece.BluePrintBoxPiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface StructurePieceTypeAdd{
    IStructurePieceType BLUE_PRINT_BOX = IStructurePieceType.setPieceId(BluePrintBoxPiece.Piece::new,"arkdust:bpb");
    IStructurePieceType PIX_ARK_LIBRARY = IStructurePieceType.setPieceId(PixArkLibrary.Piece::new,"arkdust:pixark_lib");
    IStructurePieceType CW_GRAVE = IStructurePieceType.setPieceId(CWGrave.Piece::new,"arkdust:cw_grave");
}
