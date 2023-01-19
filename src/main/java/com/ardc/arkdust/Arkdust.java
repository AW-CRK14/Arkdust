package com.ardc.arkdust;

import com.ardc.arkdust.worldgen.biome.BiomeKey;
import com.ardc.arkdust.worldgen.structure.ConfiguredStructures;
import com.ardc.arkdust.RunHelper.StructureRegistryHelper;
import com.ardc.arkdust.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Utils.MOD_ID)
public class Arkdust {
    public static final Logger LOGGER = LogManager.getLogger(Arkdust.class);

    public Arkdust(){
        final IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(BUS);//物品注册
        BlockRegistry.BLOCKS.register(BUS);//方块注册
        TileEntityTypeRegistry.TE.register(BUS);//方块实体注册

        //结构注册
        StructureRegistryHelper.STRUCTURES.register(BUS);
        BUS.addListener(this::ardStructureRegistrySetup);
        SurfaceBuilderRegistry.SURFACE_BUILDER.register(BUS);

        //生物群系注册
        BiomeRegistry.BIOMES.register(BUS);
        BiomeKey.generateBiome();
    }

    private void ardStructureRegistrySetup(final FMLCommonSetupEvent event){
        StructureRegistryHelper.DimensionSettingRegistry();
        StructureRegistry.JigsawRegistryList();
        ConfiguredStructures.registryCfedStructure();
    }
}
