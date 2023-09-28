package com.ardc.arkdust.blocks;

import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class Test_block_b extends PreBlock {
    public Test_block_b(){
        super(Properties.copy(Blocks.COMMAND_BLOCK));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        player.displayClientMessage(Component.literal("From:" + (world.isClientSide ? "client" : "server") + "  Hand:" + hand),false);
        player.displayClientMessage(Component.literal("Item:" + stack.getItem()),false);
        player.displayClientMessage(Component.literal("Nbt:" + stack.getOrCreateTag()),false);
        player.displayClientMessage(Component.literal("  "),false);
        CompoundTag nbt = new CompoundTag();
        player.save(nbt);
        player.displayClientMessage(Component.literal("Player:" + nbt),false);
        return InteractionResult.SUCCESS;
    }
}
