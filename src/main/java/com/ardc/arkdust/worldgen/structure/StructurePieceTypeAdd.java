package com.ardc.arkdust.worldgen.structure;

import com.ardc.arkdust.worldgen.structure.structure.cworld.*;
import com.ardc.arkdust.worldgen.structure.structure.story.moonfall.SMoonfallOasis;
import com.ardc.arkdust.worldgen.structure.structure_piece.BluePrintBoxPiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public interface StructurePieceTypeAdd{
    IStructurePieceType BLUE_PRINT_BOX = IStructurePieceType.setPieceId(BluePrintBoxPiece.Piece::new,"arkdust:bpb");
    IStructurePieceType PIX_ARK_LIBRARY = IStructurePieceType.setPieceId(PixArkLibrary.Piece::new,"arkdust:pixark_lib");
    IStructurePieceType CW_GRAVE = IStructurePieceType.setPieceId(CWGrave.Piece::new,"arkdust:cw/grave");
    IStructurePieceType CW_TOWER = IStructurePieceType.setPieceId(CWTower.Piece::new,"arkdust:cw/tower");
    IStructurePieceType CW_OLD_HOUSE = IStructurePieceType.setPieceId(CWOldHouse.Piece::new,"arkdust:cw/old_house");
    IStructurePieceType CW_BOAT = IStructurePieceType.setPieceId(CWBoat.Piece::new,"arkdust:cw/boat");
//    IStructurePieceType CW_TEST_BRIDGE = IStructurePieceType.setPieceId(CWTestBridge.Piece::new,"arkdust:cw/test_bridge");
    IStructurePieceType STORY_MOONFALL_OASIS = IStructurePieceType.setPieceId(SMoonfallOasis.Piece::new,"arkdust:story/moonfall/oasis");
}
