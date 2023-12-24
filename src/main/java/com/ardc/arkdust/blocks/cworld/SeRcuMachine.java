package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.block_entity.OERIMachineBE;
import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SeRcuMachine extends PreBlock implements EntityBlock {
    public SeRcuMachine() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(4,10).noOcclusion().requiresCorrectToolForDrops());
    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult rayTraceResult) {
        if(!world.isClientSide && hand.equals(InteractionHand.MAIN_HAND)){
            NetworkHooks.openScreen((ServerPlayer) player, (OERIMachineBE)world.getBlockEntity(pos),(buffer)->buffer.writeBlockPos(pos));
        }
//        world.getBlockEntity(pos).setChanged();
        return InteractionResult.SUCCESS;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OERIMachineBE(pos,state);
    }
}
