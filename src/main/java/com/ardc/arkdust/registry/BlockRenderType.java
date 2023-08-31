package com.ardc.arkdust.registry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockRenderType {
        @SubscribeEvent
        public static void onRenderTypeSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
//                RenderTypeLookup.setRenderLayer(BlockRegistry.pau_fluorescence_glass.get(), RenderType.lightning());
                RenderTypeLookup.setRenderLayer(BlockRegistry.pau_fluorescence_glass.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.iron_structure_frame_creator.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.blue_crystal.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.iron_structure_frame.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(BlockRegistry.blackstone_lamp.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.hang_screened_table.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(BlockRegistry.life_model.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.chaotic_air.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.structure_ignore_block.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.c7lab_strock_glass_1.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.c7lab_strock_glass_2.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.rushed_iron_mineshaft_net.get(), RenderType.cutout());
                RenderTypeLookup.setRenderLayer(BlockRegistry.broken_rushed_iron_mineshaft_net.get(), RenderType.cutout());
//                RenderTypeLookup.setRenderLayer(BlockRegistry.projection_block.get(), RenderType.translucent());
            });
        }
}