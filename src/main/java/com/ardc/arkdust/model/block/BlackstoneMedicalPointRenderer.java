package com.ardc.arkdust.model.block;

import com.ardc.arkdust.block_entity.BlackstoneMedicalPointBE;
import com.ardc.arkdust.registry.BlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Quaternionf;

public class BlackstoneMedicalPointRenderer implements BlockEntityRenderer<BlackstoneMedicalPointBE> {
    public BlackstoneMedicalPointRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BlackstoneMedicalPointBE be, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        long time = System.currentTimeMillis();

        stack.pushPose();
        boolean beState = be.blackstoneMedicalPointState();
        stack.translate(1,1.5,1);
        stack.mulPose(new Quaternionf().rotateAxis(time/100F*260,0.37F,0.64F,0.82F));
        stack.translate(-0.5,-0.5,-0.5);
        stack.scale(0.5F,0.5F,0.5F);
        if(beState) {
            BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
            dispatcher.renderSingleBlock(BlockRegistry.life_model.get().defaultBlockState(),stack,buffer,combinedLightIn,combinedOverlayIn, ModelData.EMPTY, RenderType.translucent());
        }
        stack.popPose();
    }
}
