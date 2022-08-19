package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

//用于使物品随值而改变。目前只会做堆叠（悲）
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class PropertyRegistry {
    @SubscribeEvent
    public static void etedatasAccumulation(FMLClientSetupEvent event){//FMLClientSetupEvent客户端设置事件，大概是指定
        event.enqueueWork(()->{
            ItemModelsProperties.register(ItemRegistry.e_t_experiment_data.get(),new ResourceLocation(Utils.MOD_ID,"quantity"),(ItemStack, ClientWorld, LivingEntity)->ItemStack.getCount());
        });
//    }
//    public static void ettdatasAccumulation(FMLClientSetupEvent event){
//        event.enqueueWork(()->{
//            ItemModelsProperties.register(ItemRegistry.e_t_theory_data.get(),new ResourceLocation(Utils.MOD_ID,"quantity"),(ItemStack, ClientWorld, LivingEntity)->ItemStack.getCount());
//        });
//    }
//    public static void medicalKitTips(FMLClientSetupEvent event){
//        event.enqueueWork(()->{
//            ItemModelsProperties.register(ItemRegistry.medical_kit.get(),new ResourceLocation(Utils.MOD_ID,"life"),(ItemStack, ClientWorld, PlayerEntity)->ItemStack.getMaxDamage());
//        });
    }
}