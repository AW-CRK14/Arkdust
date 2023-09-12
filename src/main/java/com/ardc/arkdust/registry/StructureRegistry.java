package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.config.ListNBTFeatureConfig;
import com.ardc.arkdust.worldgen.config.RandomPosOffsetConfig;
import com.ardc.arkdust.worldgen.config.SingleBlockPlacementConfig;
import com.ardc.arkdust.worldgen.feature.OreFeature;
import com.ardc.arkdust.worldgen.structure.ConfiguredStructures;
import com.ardc.arkdust.helper.StructureRegistryHelper;
import com.ardc.arkdust.worldgen.structure.structure.cworld.*;
import com.ardc.arkdust.worldgen.structure.structure.story.moonfall.SMoonfallOasis;
import com.ardc.arkdust.worldgen.structure.structure_pool.OldHouse0Pool;
import com.ardc.arkdust.worldgen.structure.structure_pool.UndertreeBlueprintPool;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class StructureRegistry {

    public static final RegistryObject<Structure<NoFeatureConfig>> UNDERTREE_BLUEPRINT = StructureRegistryHelper.setup("undertree_blueprint",new UnderTreeBlueprintBox(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> CW_OLD_HOUSE_0 = StructureRegistryHelper.setup("cw_old_house_0",new OldHouse0(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> PIXARK_LIBRARY = StructureRegistryHelper.setup("pixark_library",new PixArkLibrary(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> CW_GRAVE = StructureRegistryHelper.setup("cw_grave",new CWGrave(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> CW_TOWER = StructureRegistryHelper.setup("cw_tower",new CWTower(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> CW_OLD_HOUSE = StructureRegistryHelper.setup("cw_old_house",new CWOldHouse(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> CW_BOAT = StructureRegistryHelper.setup("cw_boat",new CWBoat(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> STORY_MOONFALL_OASIS = StructureRegistryHelper.setup("story_moonfall_oasis",new SMoonfallOasis(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> CW_TEST_BRIDGE = StructureRegistryHelper.setup("cw_test_bridge",new CWTestBridge(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> GRAVELLY_WASTELAND_MINESHAFT = StructureRegistryHelper.setup("gravelly_wasteland_mineshaft",new GravellyWastelandMineshaft(NoFeatureConfig.CODEC), GenerationStage.Decoration.UNDERGROUND_DECORATION);

    public static final List<JigsawPattern> jigsawPatternList = Arrays.asList(UndertreeBlueprintPool.pool, OldHouse0Pool.pool_broken,OldHouse0Pool.pool_common);

    public static void JigsawRegistryList(){
        for (JigsawPattern p : jigsawPatternList) {
            JigsawPatternRegistry.register(p);
        }
    }

    public static ServerWorld world;
    @SubscribeEvent
    public static void generateStructurePrepare(PlayerEvent.PlayerLoggedInEvent event){
        World worldW = event.getPlayer().level;
        if (worldW instanceof ServerWorld){
            world = (ServerWorld) worldW;
        }
    }

    @SubscribeEvent
    public static void generateStructurePrepare(BiomeLoadingEvent event){
        if(event.getName() != null) {
//            RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
            float tem = event.getClimate().temperature;
            BiomeGenerationSettingsBuilder gen = event.getGeneration();
            Biome.Category category = event.getCategory();

            if(tem >=0.2 && tem <= 1.25 && !category.equals(Biome.Category.OCEAN) && !category.equals(Biome.Category.RIVER)){//树下蓝图箱生成依赖于群系温度
                gen.addStructureStart(ConfiguredStructures.cfed_undertree_blueprint);//等价的
//                gen.getStructures().add(()->ConfiguredStructures.cfed_undertree_blueprint);
            }
            if(category.equals(Biome.Category.PLAINS)){
                gen.addStructureStart(ConfiguredStructures.cfed_cw_old_house_0);
            }
            if(category.equals(Biome.Category.DESERT) || category.equals(Biome.Category.PLAINS) || category.equals(Biome.Category.FOREST) || category.equals(Biome.Category.SAVANNA)){//像素方舟图书馆结构
                gen.addStructureStart(ConfiguredStructures.cfed_pixark_library);
            }
            if(!category.equals(Biome.Category.OCEAN) && !category.equals(Biome.Category.RIVER)){//墓碑结构与塔结构
                gen.addStructureStart(ConfiguredStructures.cfed_cw_grave);
                gen.addStructureStart(ConfiguredStructures.cfed_cw_tower);
                gen.addStructureStart(ConfiguredStructures.cfed_cw_old_house);
                gen.addStructureStart(ConfiguredStructures.cfed_cw_test_bridge);
            }
            if(category.equals(Biome.Category.OCEAN) || category.equals(Biome.Category.RIVER)){
                gen.addStructureStart(ConfiguredStructures.cfed_cw_boat);
            }
            if(category.equals(Biome.Category.DESERT)){
                gen.addStructureStart(ConfiguredStructures.cfed_story_moonfall_oasis);
            }
            if(event.getName().equals(BiomeRegistry.CW$FAULT_LINE.getId())){
                gen.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, OreFeature.ORE_PAU);
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.FAULTLINE$STONE_OAK_TREE.get().configured(NoFeatureConfig.INSTANCE).chance(24).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))));
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.OVERWORLD_ORE_PILE.get().configured(NoFeatureConfig.INSTANCE).chance(56).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(5))));
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.ABS$SINGLE_BLOCK_ON.get().configured(new SingleBlockPlacementConfig(Arrays.asList(Blocks.MYCELIUM,Blocks.GRAVEL),BlockRegistry.mycelium_pile.get().defaultBlockState())).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))).count(8));
//                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.ABS$LIST_NBT_FEATURE.get().configured(new ListNBTFeatureConfig(Utils.MOD_ID+":biomefeature/faultline/hole_",Arrays.asList("0","1","2"),-4,true,Blocks.STONE,1)).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))).chance(12));
                gen.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(),()-> FeatureRegistry.ABS$LIST_NBT_FEATURE.get().configured(new ListNBTFeatureConfig(Utils.MOD_ID+":biomefeature/faultline/ruin_",6,Arrays.asList(Blocks.GRAVEL,Blocks.COBBLESTONE, Blocks.STONE),-1,true,0.85F)).decorated(PlacementRegistry.RANDOM_OFFSET.get().configured(new RandomPosOffsetConfig(7))).chance(196));
                gen.addStructureStart(ConfiguredStructures.cfed_gravelly_wasteland_mineshaft);
//                event.getEffects().getAmbientParticleSettings().
            }
        }
    }
}
