package com.ardc.arkdust;

import com.ardc.arkdust.registry.*;
import com.ardc.arkdust.resource.Tag;
import com.ardc.arkdust.worldgen.biome.BiomeKey;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.ExtraStructureType;
import com.ardc.arkdust.worldgen.structure.processor.ExtraStructureProcessorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class Arkdust {

    public Arkdust(){
        final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.registryItemDeferredRegister(BUS);
        BlockRegistry.BLOCKS.register(BUS);//方块注册
        TileEntityTypeRegistry.TE.register(BUS);//方块实体注册
        ContainerRegistry.CONTAINER.register(BUS);//方块容器注册

        //生物群系注册
//        BiomeRegistry.BIOMES.register(BUS);
        BiomeKey.generateBiome();
//        SurfaceBuilderRegistry.SURFACE_BUILDER.register(BUS);
//        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,Utils.MOD_ID,);

        //粒子注册
        ParticleRegistry.PARTICLE.register(BUS);
        //ParticleFactoryRegistry工厂注册

        //结构注册
//        StructureRegistryHelper.STRUCTURES.register(BUS);
        ExtraStructureType.bootstrap();
        ExtraStructurePieceType.bootstrap();
        ExtraStructureProcessorType.bootstrap();
        FeatureRegistry.FEATURES.register(BUS);
        PlacementRegistry.PLACEMENT.register(BUS);
//        StructureRegistryHelper.DimensionSettingRegistry();
//        ConfiguredStructures.registryCfedStructure();

        Tag.init();

        //Ard内部信息注册-见FMLFinishEventHandler
    }
}
