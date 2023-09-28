package com.ardc.arkdust.worldgen.structure;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.structure.cworld.*;
import com.ardc.arkdust.worldgen.structure.structure.story.moonfall.SMoonfallOasis;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

import java.util.Locale;

public class ExtraStructurePieceType {
    public static void bootstrap(){}

    public static final StructurePieceType CW$UNDERTREE_BLUEPRINT_BOX = registry(UnderTreeBlueprintBox.Tem::new,"cw/undertree_blueprint");
    public static final StructurePieceType CW$BOAT = registry(CWBoat.Piece::new,"cw/boat");
    public static final StructurePieceType CW$PIXARK_LIBRARY = registry(PixArkLibrary.Tem::new,"cw/pixark_lib");
    public static final StructurePieceType CW$OLD_HOUSE = registry(CWOldHouse.Piece::new,"cw/old_house");

    public static final StructurePieceType CW$GRAVE = registry(CWGrave.Piece::new,"cw/grave");
    public static final StructurePieceType CW$TOWER = registry(CWTower.Piece::new,"cw/tower");
    //    StructurePieceType CW_TEST_BRIDGE = registry()(CWTestBridge.Piece::new,"arkdust:cw/test_bridge");
    public static final StructurePieceType STORE$MF$OASIS = registry(SMoonfallOasis.Piece::new,"story/moonfall/oasis");
    public static final StructurePieceType CW$GRAVELLY_WASTELAND_MINESHAFT = registry(GravellyWastelandMineshaft.Piece::new,"cw/gravelly_wasteland_mineshaft");

    private static StructurePieceType registry(StructurePieceType.StructureTemplateType type, String id) {
        return Registry.register(BuiltInRegistries.STRUCTURE_PIECE,  Utils.MOD_ID + ":" + id.toLowerCase(Locale.ROOT), type);
    }
}
