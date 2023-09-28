package com.ardc.arkdust.worldgen.structure;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.structure.structure.cworld.*;
import com.ardc.arkdust.worldgen.structure.structure.story.moonfall.SMoonfallOasis;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ExtraStructureType {
    public static void bootstrap(){}

    public static final StructureType<UnderTreeBlueprintBox> CW$UNDERTREE_BLUEPRINT = register("cw/undertree_blueprint",UnderTreeBlueprintBox.CODEC);
    public static final StructureType<CWBoat> CW$BOAT = register("cw/boat", CWBoat.CODEC);
    public static final StructureType<PixArkLibrary> CW$PIXARK_LIBRARY = register("cw/pixark_library", PixArkLibrary.CODEC);
    public static final StructureType<CWOldHouse> CW$OLD_HOUSE = register("cw/old_house", CWOldHouse.CODEC);
    public static final StructureType<CWGrave> CW$GRAVE = register("cw/grave", CWGrave.CODEC);
    public static final StructureType<CWTower> CW$TOWER = register("cw/tower", CWTower.CODEC);
    public static final StructureType<SMoonfallOasis> STORE$MF$OASIS = register("cw/test_bridge", SMoonfallOasis.CODEC);
    public static final StructureType<GravellyWastelandMineshaft> CW$GRAVELLY_WASTELAND_MINESHAFT = register("cw/gravelly_wasteland_mineshaft", GravellyWastelandMineshaft.CODEC);

    private static <S extends Structure> StructureType<S> register(String name, Codec<S> codec) {
        return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, Utils.MOD_ID + ":" + name, () -> codec);
    }
}
