package com.ardc.arkdust;

import com.ardc.arkdust.recipe.RecipeSerializerRegistry;
import com.ardc.arkdust.registry.RecipeTypeRegistry;
import com.ardc.arkdust.registry.*;
import com.ardc.arkdust.registry.worldgen.*;
import com.ardc.arkdust.resource.DamageTypes;
import com.ardc.arkdust.worldgen.biome.BiomeKey;
import com.ardc.arkdust.worldgen.biome.CommonWorldRegion;
import com.ardc.arkdust.worldgen.modifier.ExtraPlacementModifierType;
import com.ardc.arkdust.worldgen.structure.ExtraStructureJigsawPool;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.ExtraStructureSet;
import com.ardc.arkdust.worldgen.structure.ExtraStructureType;
import com.ardc.arkdust.worldgen.structure.processor.ExtraStructureProcessorType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Utils.MOD_ID)
public class ModBusEventConsumer {
    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event){
        event.enqueueWork(()-> {
            Regions.register(new CommonWorldRegion(4));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,Utils.MOD_ID,CommonWorldRegion.build());

//            Tag.init();
            BiomeKey.bootstrap();
            ExtraStructureType.bootstrap();
            ExtraStructurePieceType.bootstrap();
            ExtraStructureProcessorType.bootstrap();
            ExtraPlacementModifierType.bootstrap();
            CommonWorldRegion.ruleSourceRegBootstrap();

        });
    }

    @SubscribeEvent
    public static void OnLoadComplete(FMLLoadCompleteEvent event){
        StoryRegistry.s.pushToRegister();//¾çÇé°ü×¢²á
        BlueprintTargetRegistry.s.pushToRegister();
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.PROCESSOR_LIST, StructureProcessorListRegistry::bootstrap)
            .add(Registries.STRUCTURE, StructureRegistry::bootstrap)
            .add(Registries.TEMPLATE_POOL, ExtraStructureJigsawPool::bootstrap)
            .add(Registries.DAMAGE_TYPE, DamageTypes::bootstrap)
            .add(Registries.STRUCTURE_SET, ExtraStructureSet::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureRegistry::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatureRegistry::bootstrap)
            .add(Registries.BIOME, BiomeRegistry::bootstrap);

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output,lookupProvider,BUILDER, Set.of(Utils.MOD_ID)));
    }
}
