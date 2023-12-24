package com.ardc.arkdust.worldgen.structure;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.worldgen.StructureRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ExtraStructureSet {
    public static void bootstrap(BootstapContext<StructureSet> context) {

        HolderGetter<Structure> holdergetter = context.lookup(Registries.STRUCTURE);
        HolderGetter<Biome> holdergetter1 = context.lookup(Registries.BIOME);
        context.register(CW$UNDERTREE_BLUEPRINT, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$UNDERTREE_BLUEPRINT), new RandomSpreadStructurePlacement(35, 10, RandomSpreadType.LINEAR, 23847287)));
        context.register(CW$BOAT, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$BOAT), new RandomSpreadStructurePlacement(25, 8, RandomSpreadType.LINEAR, 326746)));
        context.register(CW$PIXARK_LIBRARY, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$PIXARK_LIBRARY), new RandomSpreadStructurePlacement(50, 20, RandomSpreadType.LINEAR, 39075237)));
        context.register(CW$OLD_HOUSE, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$OLD_HOUSE), new RandomSpreadStructurePlacement(25, 8, RandomSpreadType.LINEAR, 36298837)));
        context.register(CW$GRAVE, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$GRAVE), new RandomSpreadStructurePlacement(25, 8, RandomSpreadType.LINEAR, 784250928)));
        context.register(CW$TOWER, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$TOWER), new RandomSpreadStructurePlacement(25, 8, RandomSpreadType.LINEAR, 32846237)));
        context.register(CW$TEST_BRIDGE, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$TEST_BRIDGE), new RandomSpreadStructurePlacement(45, 20, RandomSpreadType.LINEAR, 8246613)));
        context.register(STORE$MF$OASIS, new StructureSet(holdergetter.getOrThrow(StructureRegistry.STORE$MF$OASIS), new RandomSpreadStructurePlacement(60, 30, RandomSpreadType.LINEAR, 81623861)));
        context.register(CW$GRAVELLY_WASTELAND_MINESHAFT, new StructureSet(holdergetter.getOrThrow(StructureRegistry.CW$GRAVELLY_WASTELAND_MINESHAFT), new RandomSpreadStructurePlacement(40, 20, RandomSpreadType.LINEAR, 83868293)));
    }

    public static final ResourceKey<StructureSet> CW$UNDERTREE_BLUEPRINT = createKey("cw/undertree_blueprint");
    public static final ResourceKey<StructureSet> CW$BOAT = createKey("cw/boat");
    public static final ResourceKey<StructureSet> CW$PIXARK_LIBRARY = createKey("cw/pixark_library");
    public static final ResourceKey<StructureSet> CW$OLD_HOUSE = createKey("cw/old_house");
    public static final ResourceKey<StructureSet> CW$GRAVE = createKey("cw/grave");
    public static final ResourceKey<StructureSet> CW$TOWER = createKey("cw/tower");
    public static final ResourceKey<StructureSet> CW$TEST_BRIDGE = createKey("cw/test_bridge");
    public static final ResourceKey<StructureSet> STORE$MF$OASIS = createKey("story/moonfall/oasis");
    public static final ResourceKey<StructureSet> CW$GRAVELLY_WASTELAND_MINESHAFT = createKey("cw/gravelly_wasteland_mineshaft");

    public static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(Utils.MOD_ID,name));
    }
}
