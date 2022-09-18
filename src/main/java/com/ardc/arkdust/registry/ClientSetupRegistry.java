package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.model.block.BlackstoneMedicalPointRenderer;
import com.ardc.arkdust.model.block.HangScreenedTableRenderer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

//用于使物品随值而改变。目前只会做堆叠（悲）
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientSetupRegistry {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event){//FMLClientSetupEvent客户端设置事件，大概是指定
        event.enqueueWork(()->{
            //物品材质变化
//            ItemModelsProperties.register(ItemRegistry.e_t_experiment_data.get(),new ResourceLocation(Utils.MOD_ID,"quantity"),(ItemStack, ClientWorld, LivingEntity)->ItemStack.getCount());

            //方块实体额外渲染器
            ClientRegistry.bindTileEntityRenderer(TileEntityTypeRegistry.aScreenedTableBE.get(), HangScreenedTableRenderer::new);
            ClientRegistry.bindTileEntityRenderer(TileEntityTypeRegistry.aBlackstoneMedicalPointBE.get(), BlackstoneMedicalPointRenderer::new);
        });
    }
}