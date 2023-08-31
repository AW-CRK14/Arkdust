package com.ardc.arkdust;

import com.ardc.arkdust.helper.ISkipShiftBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEventConsumer {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void OnShiftCheckBlock(PlayerInteractEvent.RightClickBlock event){
        World world = event.getWorld();
        BlockState state = world.getBlockState(event.getHitVec().getBlockPos());
//        LOGGER.warn("[ArkdustText]OnShiftCheckBlock event run. world:{} hand:{}",event.getWorld(),event.getHand());
        if(Screen.hasShiftDown() && state.getBlock() instanceof ISkipShiftBlock && ((ISkipShiftBlock) state.getBlock()).acceptHand(event.getHand())){
//            LOGGER.warn("[ArkdustText]If mode get True result. world:{} hand:{}",event.getWorld(),event.getHand());
            ActionResultType type = state.use(world, event.getPlayer(),event.getHand(),event.getHitVec());
            if(type == ActionResultType.CONSUME || type == ActionResultType.SUCCESS){
                event.setCanceled(true);
                event.setCancellationResult(type);
            }
        }
    }
}
