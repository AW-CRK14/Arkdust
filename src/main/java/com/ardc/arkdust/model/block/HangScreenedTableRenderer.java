package com.ardc.arkdust.model.block;

import com.ardc.arkdust.BlockEntity.ScreenedTableBE;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.data.EmptyModelData;

public class HangScreenedTableRenderer extends TileEntityRenderer<ScreenedTableBE> {
    public HangScreenedTableRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(ScreenedTableBE be, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.pushPose();
        matrixStackIn.scale(0.75F,0.75F,0.75F);
        matrixStackIn.translate(0.167F,1.09F,0.167F);
        BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        ItemStack itemStack = be.items.get(0);
        if(itemStack.getItem() instanceof BlockItem && be.hasItem){
            BlockState state = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            dispatcher.renderBlock(state,matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
        }
        matrixStackIn.popPose();
    }
}
