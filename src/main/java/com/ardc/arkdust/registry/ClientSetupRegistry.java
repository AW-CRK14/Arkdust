package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.model.block.BlackstoneMedicalPointRenderer;
import com.ardc.arkdust.model.block.BlueprintReduceBoxRenderer;
import com.ardc.arkdust.model.block.HangScreenedTableRenderer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientSetupRegistry {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            //物品材质变化
            ItemModelsProperties.register(ItemRegistry.ender_energy_radar.get(),new ResourceLocation(Utils.MOD_ID,"damage"),(ItemStack, ClientWorld, LivingEntity)->ItemStack.getDamageValue());

            //方块实体额外渲染器
            ClientRegistry.bindTileEntityRenderer(TileEntityTypeRegistry.SCREENED_TABLE_BE.get(), HangScreenedTableRenderer::new);
            ClientRegistry.bindTileEntityRenderer(TileEntityTypeRegistry.BLACKSTONE_MEDICAL_POINT_BE.get(), BlackstoneMedicalPointRenderer::new);
            ClientRegistry.bindTileEntityRenderer(TileEntityTypeRegistry.BASIC_BLUEPRINT_REDUCE_BOX_BE.get(), BlueprintReduceBoxRenderer::new);
        });
    }
}