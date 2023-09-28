package com.ardc.arkdust.model.block;

import com.ardc.arkdust.block_entity.ScreenedTableBE;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class HangScreenedTableRenderer implements BlockEntityRenderer<ScreenedTableBE> {
    public HangScreenedTableRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ScreenedTableBE be, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        stack.pushPose();
        stack.scale(0.75F,0.75F,0.75F);
        stack.translate(0.167F,1.09F,0.167F);
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        ItemStack itemStack = be.items.get(0);
        if(itemStack.getItem() instanceof BlockItem && be.hasItem){
            BlockState state = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            dispatcher.renderSingleBlock(state,stack, buffer, combinedLightIn, combinedOverlayIn, ModelData.EMPTY, RenderType.cutout());
        }
        stack.popPose();
    }
}
