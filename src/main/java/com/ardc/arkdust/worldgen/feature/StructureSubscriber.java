package com.ardc.arkdust.worldgen.feature;

import com.ardc.arkdust.Utils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class StructureSubscriber {
//    @SubscribeEvent
//    public static void worldStructureSetup(FMLCommonSetupEvent event){
//        event.enqueueWork(()->{
//            StructureRegistry.setupStructures();
//            ConfiguredStructures.registryCfedStructure();
//        });
//    }
    
    @SubscribeEvent
    public static void generateStructurePrepare(BiomeLoadingEvent event){
        if(event.getName() != null) {
//            RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
            float tem = event.getClimate().temperature;
            if(tem >=0.2 && tem <= 1.25 && !event.getCategory().equals(Biome.Category.OCEAN)){//树下蓝图箱生成依赖于群系温度
                event.getGeneration().addStructureStart(ConfiguredStructures.cfed_undertree_blueprint);//等价的
//                event.getGeneration().getStructures().add(()->ConfiguredStructures.cfed_undertree_blueprint);
            }
        }
    }

//    @SubscribeEvent
//    public static void addStructure(WorldEvent.Load event){
//        if(event.getWorld() instanceof ServerWorld){
//            ServerWorld world = (ServerWorld) event.getWorld();
//            try {
//                Method GETCODEC_METHOD =
//                        ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
//                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey(
//                        (Codec<? extends ChunkGenerator>)GETCODEC_METHOD.invoke(world.getChunkSource().generator));
//
//                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
//                    return;
//                }
//            } catch (Exception e) {
//                LogManager.getLogger().error("Was unable to check if " + world.getLevel()
//                        + " is using Terraforged's ChunkGenerator.");
//            }
//
//            Map<Structure<?>, StructureSeparationSettings> tempMap =
//                    new HashMap<>(world.getChunkSource().generator.getSettings().structureConfig());
//            tempMap.putIfAbsent(StructureRegistry.UNDERTREE_BLUEPRINT.get(),
//                    DimensionStructuresSettings.DEFAULTS.get(StructureRegistry.UNDERTREE_BLUEPRINT.get()));
//            world.getChunkSource().generator.getSettings().structureConfig = tempMap;
//        }
//    }
    
}
