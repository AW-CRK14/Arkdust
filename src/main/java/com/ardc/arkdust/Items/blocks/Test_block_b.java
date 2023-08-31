package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.obj_property.ExtraMaterial;
import com.ardc.arkdust.capability.story.IStorySaveCapability;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class Test_block_b extends Block{
    public Test_block_b(){
        super(Properties.of(ExtraMaterial.TEST1).strength(2));
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        player.displayClientMessage(new StringTextComponent("From:" + (world.isClientSide ? "client" : "server") + "  Hand:" + hand),false);
        player.displayClientMessage(new StringTextComponent("Item:" + stack.getItem()),false);
        player.displayClientMessage(new StringTextComponent("Nbt:" + stack.getOrCreateTag()),false);
        return ActionResultType.SUCCESS;
    }
}
