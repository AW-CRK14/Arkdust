package com.ardc.arkdust.blocks.ores;


import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;


public class PauOre extends Block {
    public PauOre(){
        super(Properties.of().strength(6.5F).requiresCorrectToolForDrops());
        //strength是强度，等价于非官方版中的hardnessAndResistance
    }

//    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        if(level.isClientSide && !player.isCreative()){
//            player.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)-> Utils.LOGGER.info(i.toString()));
//        }
//        return InteractionResult.SUCCESS;
//    }
}
