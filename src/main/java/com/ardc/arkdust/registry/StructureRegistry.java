package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.feature.ConfiguredStructures;
import com.ardc.arkdust.worldgen.feature.StructureRegistryHelper;
import com.ardc.arkdust.worldgen.feature.structure.cworld.CWGrave;
import com.ardc.arkdust.worldgen.feature.structure.cworld.OldHouse0;
import com.ardc.arkdust.worldgen.feature.structure.cworld.PixArkLibrary;
import com.ardc.arkdust.worldgen.feature.structure.cworld.UnderTreeBlueprintBox;
import com.ardc.arkdust.worldgen.feature.structure_pool.OldHouse0Pool;
import com.ardc.arkdust.worldgen.feature.structure_pool.UndertreeBlueprintPool;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class StructureRegistry {

    public static final RegistryObject<Structure<NoFeatureConfig>> UNDERTREE_BLUEPRINT = StructureRegistryHelper.setup("undertree_blueprint",new UnderTreeBlueprintBox(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> OLD_HOUSE_0 = StructureRegistryHelper.setup("old_house_0",new OldHouse0(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> PIXARK_LIBRARY = StructureRegistryHelper.setup("pixark_library",new PixArkLibrary(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final RegistryObject<Structure<NoFeatureConfig>> CW_GRAVE = StructureRegistryHelper.setup("cw_grave",new CWGrave(NoFeatureConfig.CODEC), GenerationStage.Decoration.SURFACE_STRUCTURES);

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
        System.out.println("WorldLoad:" + worldW + ":" + world);
    }

    @SubscribeEvent
    public static void generateStructurePrepare(BiomeLoadingEvent event){
        if(event.getName() != null) {
//            RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
            float tem = event.getClimate().temperature;
            if(tem >=0.2 && tem <= 1.25 && !event.getCategory().equals(Biome.Category.OCEAN) && !event.getCategory().equals(Biome.Category.RIVER)){//树下蓝图箱生成依赖于群系温度
                event.getGeneration().addStructureStart(ConfiguredStructures.cfed_undertree_blueprint);//等价的
//                event.getGeneration().getStructures().add(()->ConfiguredStructures.cfed_undertree_blueprint);
            }
            if(event.getCategory().equals(Biome.Category.PLAINS)){//旧屋0结构
                event.getGeneration().addStructureStart(ConfiguredStructures.cfed_old_house_0);
            }
            if(event.getCategory().equals(Biome.Category.DESERT) || event.getCategory().equals(Biome.Category.PLAINS) || event.getCategory().equals(Biome.Category.FOREST) || event.getCategory().equals(Biome.Category.SAVANNA)){//像素方舟图书馆结构
                event.getGeneration().addStructureStart(ConfiguredStructures.cfed_pixark_library);
            }
            if(!event.getCategory().equals(Biome.Category.OCEAN) && !event.getCategory().equals(Biome.Category.RIVER)){//墓碑结构
                event.getGeneration().addStructureStart(ConfiguredStructures.cfed_cw_grave);
            }
        }
    }
}
