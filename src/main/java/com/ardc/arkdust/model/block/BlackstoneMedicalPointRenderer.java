package com.ardc.arkdust.model.block;

import com.ardc.arkdust.BlockEntity.BlackstoneMedicalPointBE;
import com.ardc.arkdust.model.modelblock.LifeBlockModel;
import com.ardc.arkdust.registry.BlockRegistry;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;

public class BlackstoneMedicalPointRenderer extends TileEntityRenderer<BlackstoneMedicalPointBE> {
    public BlackstoneMedicalPointRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(BlackstoneMedicalPointBE be, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.pushPose();
        int beState = be.blackstoneMedicalPointState();
        matrixStackIn.translate(0.25F,1,0.25F);
        matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(30));
        matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(30));
        matrixStackIn.mulPose(Vector3f.ZN.rotationDegrees(30));
        matrixStackIn.scale(0.5F,0.5F,0.5F);
        if(beState != 0) {
            BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
            BlockState state = BlockRegistry.life_model.get().defaultBlockState();
            if (beState == 1)
                state = state.setValue(LifeBlockModel.COLOR,1).getBlockState();
            else if (beState == 2)
                state = state.setValue(LifeBlockModel.COLOR,3).getBlockState();
            else
                state = state.setValue(LifeBlockModel.COLOR,2).getBlockState();
            dispatcher.renderBlock(state,matrixStackIn,bufferIn,combinedLightIn,combinedOverlayIn, EmptyModelData.INSTANCE);
        }
        matrixStackIn.popPose();
    }
}
