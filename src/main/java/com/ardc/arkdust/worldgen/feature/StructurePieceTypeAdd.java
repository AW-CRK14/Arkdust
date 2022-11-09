package com.ardc.arkdust.worldgen.feature;

import com.ardc.arkdust.worldgen.feature.structure.cworld.*;
import com.ardc.arkdust.worldgen.feature.structure_piece.BluePrintBoxPiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface StructurePieceTypeAdd{
    IStructurePieceType BLUE_PRINT_BOX = IStructurePieceType.setPieceId(BluePrintBoxPiece.Piece::new,"arkdust:bpb");
    IStructurePieceType PIX_ARK_LIBRARY = IStructurePieceType.setPieceId(PixArkLibrary.Piece::new,"arkdust:pixark_lib");
    IStructurePieceType CW_GRAVE = IStructurePieceType.setPieceId(CWGrave.Piece::new,"arkdust:cw_grave");
    IStructurePieceType CW_TOWER = IStructurePieceType.setPieceId(CWTower.Piece::new,"arkdust:cw_tower");
    IStructurePieceType CW_OLD_HOUSE = IStructurePieceType.setPieceId(CWOldHouse.Piece::new,"arkdust:cw_old_house");
    IStructurePieceType CW_BOAT = IStructurePieceType.setPieceId(CWBoat.Piece::new,"arkdust:cw_boat");
}
