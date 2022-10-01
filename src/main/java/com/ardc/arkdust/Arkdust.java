package com.ardc.arkdust;

import com.ardc.arkdust.worldgen.feature.ConfiguredStructures;
import com.ardc.arkdust.worldgen.feature.StructureRegistryHelper;
import com.ardc.arkdust.registry.*;
import net.minecraftforge.common.MinecraftForge;
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
        ItemRegistry.ITEMS.register(BUS);//items类注册入mod主线
        BlockRegistry.BLOCKS.register(BUS);//blocks类注册入mod主线
        TileEntityTypeRegistry.TE.register(BUS);//方块实体注册入mod主线
        StructureRegistryHelper.STRUCTURES.register(BUS);//
        BUS.addListener(this::ardStructureRegistrySetup);
        MinecraftForge.EVENT_BUS.register(this);
//        SurfaceBuilderInit.SURFACE_BUILDER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void ardStructureRegistrySetup(final FMLCommonSetupEvent event){
        StructureRegistryHelper.DimensionSettingRegistry();
        StructureRegistry.JigsawRegistryList();
        ConfiguredStructures.registryCfedStructure();
    }
}
