package com.ardc.arkdust;

import com.ardc.arkdust.helper.ISkipShiftBlock;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEventConsumer {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
        LOGGER.info("Registered biome:{}", ForgeRegistries.BIOMES.getKeys());
    }

    @SubscribeEvent
    public static void onShiftCheckBlock(PlayerInteractEvent.RightClickBlock event){
        Level world = event.getLevel();
        BlockState state = world.getBlockState(event.getHitVec().getBlockPos());
//        LOGGER.warn("[ArkdustText]OnShiftCheckBlock event run. world:{} hand:{}",event.getWorld(),event.getHand());
        if(Screen.hasShiftDown() && state.getBlock() instanceof ISkipShiftBlock && ((ISkipShiftBlock) state.getBlock()).acceptHand(event.getHand())){
//            LOGGER.warn("[ArkdustText]If mode get True result. world:{} hand:{}",event.getWorld(),event.getHand());
            InteractionResult type = state.use(world, event.getEntity(),event.getHand(),event.getHitVec());
            if(type == InteractionResult.CONSUME || type == InteractionResult.SUCCESS){
                event.setCanceled(true);
                event.setCancellationResult(type);
            }
        }
    }
}
