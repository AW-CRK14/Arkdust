package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.model.block.BlackstoneMedicalPointRenderer;
import com.ardc.arkdust.model.block.BlueprintReduceBoxRenderer;
import com.ardc.arkdust.model.block.HangScreenedTableRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientSetupRegistry {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            //物品材质变化
            ItemProperties.register(ItemRegistry.ender_energy_radar.get(),new ResourceLocation(Utils.MOD_ID,"damage"),(itemStack, level, livingEntity,num)->itemStack.getDamageValue());

            //方块实体额外渲染器
            BlockEntityRenderers.register(TileEntityTypeRegistry.SCREENED_TABLE_BE.get(), HangScreenedTableRenderer::new);
            BlockEntityRenderers.register(TileEntityTypeRegistry.BLACKSTONE_MEDICAL_POINT_BE.get(), BlackstoneMedicalPointRenderer::new);
            BlockEntityRenderers.register(TileEntityTypeRegistry.BASIC_BLUEPRINT_REDUCE_BOX_BE.get(), BlueprintReduceBoxRenderer::new);
        });
    }
}