package com.ardc.arkdust;

import com.ardc.arkdust.registry.*;
import com.ardc.arkdust.resource.DamageTypes;
import com.ardc.arkdust.worldgen.biome.CommonWorldRegion;
import com.ardc.arkdust.worldgen.structure.ExtraStructureSet;
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

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventConsumer {
    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event){
        Regions.register(new CommonWorldRegion(18));
    }

    @SubscribeEvent
    public static void OnLoadComplete(FMLLoadCompleteEvent event){
        StoryRegistry.s.pushToRegister();//¾çÇé°ü×¢²á
        BlueprintTargetRegistry.s.pushToRegister();
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, BiomeRegistry::bootstrap)
            .add(Registries.PROCESSOR_LIST, StructureProcessorListRegistry::bootstrap)
            .add(Registries.STRUCTURE, StructureRegistry::bootstrap)
            .add(Registries.DAMAGE_TYPE, DamageTypes::bootstrap)
            .add(Registries.STRUCTURE_SET, ExtraStructureSet::bootstrap);

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(),new DatapackBuiltinEntriesProvider(output,lookupProvider,BUILDER, Set.of(Utils.MOD_ID)));
    }
}
