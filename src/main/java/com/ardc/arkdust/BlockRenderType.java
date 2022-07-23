package com.ardc.arkdust;

import com.ardc.arkdust.BlockRegistry;
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
                RenderTypeLookup.setRenderLayer(BlockRegistry.pau_fluorescence_glass.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.blue_crystal.get(), RenderType.translucent());
                RenderTypeLookup.setRenderLayer(BlockRegistry.iron_structure_frame.get(), RenderType.cutout());
            });
        }
}