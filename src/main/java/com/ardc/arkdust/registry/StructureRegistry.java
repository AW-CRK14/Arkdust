package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.resource.Tag;
import com.ardc.arkdust.worldgen.structure.ExtraStructureJigsawPool;
import com.ardc.arkdust.worldgen.structure.structure.cworld.*;
import com.ardc.arkdust.worldgen.structure.structure.story.moonfall.SMoonfallOasis;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class StructureRegistry {

    public static void bootstrap(BootstapContext<Structure> context)  {
        HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> poolHolderGetter = context.lookup(Registries.TEMPLATE_POOL);

        Structure.StructureSettings landSetting = new Structure.StructureSettings(biomeHolderGetter.getOrThrow(Tag.Biomes.IS_LAND), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE);
        Structure.StructureSettings desertSetting = new Structure.StructureSettings(biomeHolderGetter.getOrThrow(Tags.Biomes.IS_DESERT), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE);
        Structure.StructureSettings wastelandSetting = new Structure.StructureSettings(biomeHolderGetter.getOrThrow(Tag.Biomes.ALLOW_GWM_GEN), Map.of(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.BEARD_BOX);
        Structure.StructureSettings seaSetting = new Structure.StructureSettings(biomeHolderGetter.getOrThrow(BiomeTags.IS_OCEAN), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE);
        Structure.StructureSettings caveSetting = new Structure.StructureSettings(biomeHolderGetter.getOrThrow(Tags.Biomes.IS_CAVE), Map.of(), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE);

        context.register(CW$UNDERTREE_BLUEPRINT,new UnderTreeBlueprintBox(landSetting));
        context.register(CW$BOAT,new CWBoat(seaSetting));
        context.register(CW$PIXARK_LIBRARY,new PixArkLibrary(landSetting));
        context.register(CW$OLD_HOUSE,new CWOldHouse(landSetting));
        context.register(CW$GRAVE,new CWGrave(landSetting));
        context.register(CW$TOWER,new CWTower(landSetting));
        context.register(CW$TEST_BRIDGE,new JigsawStructure(landSetting,poolHolderGetter.getOrThrow(ExtraStructureJigsawPool.TEST_BRIDGE),8, ConstantHeight.of(VerticalAnchor.absolute(0)), true, Heightmap.Types.WORLD_SURFACE_WG));
        context.register(STORE$MF$OASIS,new SMoonfallOasis(desertSetting));
    }

    public static final ResourceKey<Structure> CW$UNDERTREE_BLUEPRINT = createKey("cw/undertree_blueprint");
    public static final ResourceKey<Structure> CW$OLD_HOUSE = createKey("cw/old_house");
    public static final ResourceKey<Structure> CW$PIXARK_LIBRARY = createKey("cw/pixark_library");
    public static final ResourceKey<Structure> CW$GRAVE = createKey("cw/grave");
    public static final ResourceKey<Structure> CW$TOWER = createKey("cw/tower");
    public static final ResourceKey<Structure> CW$BOAT = createKey("cw/boat");
    public static final ResourceKey<Structure> CW$TEST_BRIDGE = createKey("cw/test_bridge");
    public static final ResourceKey<Structure> CW$GRAVELLY_WASTELAND_MINESHAFT = createKey("cw/gravelly_wasteland_mineshaft");
    public static final ResourceKey<Structure> STORE$MF$OASIS = createKey("store/mf/oasis");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Utils.MOD_ID,name));
    }

//    public static final RegistryObject<Structure<NoFeatureConfig>> UNDERTREE_BLUEPRINT = StructureRegistryHelper.setup("undertree_blueprint",new UnderTreeBlueprintBox(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> CW_OLD_HOUSE_0 = StructureRegistryHelper.setup("cw_old_house_0",new OldHouse0(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> PIXARK_LIBRARY = StructureRegistryHelper.setup("pixark_library",new PixArkLibrary(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> CW_GRAVE = StructureRegistryHelper.setup("cw_grave",new CWGrave(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> CW_TOWER = StructureRegistryHelper.setup("cw_tower",new CWTower(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> CW_OLD_HOUSE = StructureRegistryHelper.setup("cw_old_house",new CWOldHouse(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> CW_BOAT = StructureRegistryHelper.setup("cw_boat",new CWBoat(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> STORY_MOONFALL_OASIS = StructureRegistryHelper.setup("story_moonfall_oasis",new SMoonfallOasis(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> CW_TEST_BRIDGE = StructureRegistryHelper.setup("cw_test_bridge",new CWTestBridge(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
//    public static final RegistryObject<Structure<NoFeatureConfig>> GRAVELLY_WASTELAND_MINESHAFT = StructureRegistryHelper.setup("gravelly_wasteland_mineshaft",new GravellyWastelandMineshaft(NoFeatureConfig.CODEC), GenerationStage.Decoration.UNDERGROUND_DECORATION);



//    @SubscribeEvent
//    public static void generateStructurePrepare(BiomeLoadingEvent event){
//        if(event.getName() != null) {
////            RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
//            float tem = event.getClimate().temperature;
//            BiomeGenerationSettingsBuilder gen = event.getGeneration();
//            Biome.Category category = event.getCategory();
//
//            if(tem >=0.2 && tem <= 1.25 && !category.equals(Biome.Category.OCEAN) && !category.equals(Biome.Category.RIVER)){//树下蓝图箱生成依赖于群系温度
//                gen.addStructureStart(ConfiguredStructures.cfed_undertree_blueprint);//等价的
////                gen.getStructures().add(()->ConfiguredStructures.cfed_undertree_blueprint);
//            }
//            if(category.equals(Biome.Category.PLAINS)){
//                gen.addStructureStart(ConfiguredStructures.cfed_cw_old_house_0);
//            }
//            if(category.equals(Biome.Category.DESERT) || category.equals(Biome.Category.PLAINS) || category.equals(Biome.Category.FOREST) || category.equals(Biome.Category.SAVANNA)){//像素方舟图书馆结构
//                gen.addStructureStart(ConfiguredStructures.cfed_pixark_library);
//            }
//            if(!category.equals(Biome.Category.OCEAN) && !category.equals(Biome.Category.RIVER)){//墓碑结构与塔结构
//                gen.addStructureStart(ConfiguredStructures.cfed_cw_grave);
//                gen.addStructureStart(ConfiguredStructures.cfed_cw_tower);
//                gen.addStructureStart(ConfiguredStructures.cfed_cw_old_house);
//                gen.addStructureStart(ConfiguredStructures.cfed_cw_test_bridge);
//            }
//            if(category.equals(Biome.Category.OCEAN) || category.equals(Biome.Category.RIVER)){
//                gen.addStructureStart(ConfiguredStructures.cfed_cw_boat);
//            }
//            if(category.equals(Biome.Category.DESERT)){
//                gen.addStructureStart(ConfiguredStructures.cfed_story_moonfall_oasis);
//            }
//            if(event.getName().equals(BiomeRegistry.CW$FAULT_LINE.getId())){
//                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, OreFeature.ORE_PAU);
//                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.FAULTLINE$STONE_OAK_TREE.get().configured(NoFeatureConfig.INSTANCE).chance(24).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))));
//                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.OVERWORLD_ORE_PILE.get().configured(NoFeatureConfig.INSTANCE).chance(56).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(5))));
//                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.ABS$SINGLE_BLOCK_ON.get().configured(new SingleBlockPlacementConfig(Arrays.asList(Blocks.MYCELIUM,Blocks.GRAVEL),BlockRegistry.mycelium_pile.get().defaultBlockState())).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))).count(8));
////                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.ABS$LIST_NBT_FEATURE.get().configured(new ListNBTFeatureConfig(Utils.MOD_ID+":biomefeature/faultline/hole_",Arrays.asList("0","1","2"),-4,true,Blocks.STONE,1)).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))).chance(12));
//                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.ABS$LIST_NBT_FEATURE.get().configured(new ListNBTFeatureConfig(Utils.MOD_ID+":biomefeature/faultline/ruin_",6,Arrays.asList(Blocks.GRAVEL,Blocks.COBBLESTONE, Blocks.STONE),-1,true,0.85F)).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))).chance(196));
//                gen.addStructureStart(ConfiguredStructures.cfed_gravelly_wasteland_mineshaft);
////                event.getEffects().getAmbientParticleSettings().
//            }
//        }
//    }
}
