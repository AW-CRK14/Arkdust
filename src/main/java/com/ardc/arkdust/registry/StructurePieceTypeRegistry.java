package com.ardc.arkdust.registry;

import com.ardc.arkdust.worldgen.structure.structure.cworld.*;
import com.ardc.arkdust.worldgen.structure.structure.story.moonfall.SMoonfallOasis;
import com.ardc.arkdust.worldgen.structure.structure_piece.BluePrintBoxPiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class StructurePieceTypeRegistry {
    public static void bootstrap(){}

    public static final IStructurePieceType BLUE_PRINT_BOX = IStructurePieceType.setPieceId(BluePrintBoxPiece.Piece::new,"arkdust:bpb");
    public static final IStructurePieceType PIX_ARK_LIBRARY = IStructurePieceType.setPieceId(PixArkLibrary.Piece::new,"arkdust:pixark_lib");
    public static final IStructurePieceType CW_GRAVE = IStructurePieceType.setPieceId(CWGrave.Piece::new,"arkdust:cw/grave");
    public static final IStructurePieceType CW_TOWER = IStructurePieceType.setPieceId(CWTower.Piece::new,"arkdust:cw/tower");
    public static final IStructurePieceType CW_OLD_HOUSE = IStructurePieceType.setPieceId(CWOldHouse.Piece::new,"arkdust:cw/old_house");
    public static final IStructurePieceType CW_BOAT = IStructurePieceType.setPieceId(CWBoat.Piece::new,"arkdust:cw/boat");
//    IStructurePieceType CW_TEST_BRIDGE = IStructurePieceType.setPieceId(CWTestBridge.Piece::new,"arkdust:cw/test_bridge");
    public static final IStructurePieceType STORY_MOONFALL_OASIS = IStructurePieceType.setPieceId(SMoonfallOasis.Piece::new,"arkdust:story/moonfall/oasis");
    public static final IStructurePieceType GRAVELLY_WASTELAND_MINESHAFT = IStructurePieceType.setPieceId(GravellyWastelandMineshaft.Piece::new,"arkdust:gravelly_wasteland_mineshaft");
}
