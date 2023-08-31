package com.ardc.arkdust.model.block;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlueprintReduceBoxRenderer extends TileEntityRenderer<BlueprintReduceBoxBE> {
    private static final Logger LOGGER = LogManager.getLogger();
    public BlueprintReduceBoxRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    public static final ResourceLocation pic = new ResourceLocation(Utils.MOD_ID,"blocks/terra_blueprint/blueprint_stack");

    @Override
    public void render(BlueprintReduceBoxBE be, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5, 0, 0.5);
        matrixStackIn.mulPose(be.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getRotation());
        matrixStackIn.scale(0.0625F, 0.0625F, 0.0625F);

        TextureAtlasSprite i = Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(pic);
        float center = i.getV0() * 0.25f + i.getV1() * 0.75f;
        float u1 = i.getU0() * 0.25f + i.getU1() * 0.75f;
        float v1 = i.getV0() * 0.0625f + i.getV1() * 0.9375f;

        BufferBuilder buffer = (BufferBuilder) bufferIn.getBuffer(RenderType.cutout());
//        LOGGER.warn("[ArdTest-Render-BlueprintReduceBoxExtraRender]buffer format check point (only for breakpoint)");
        if (be.getCount() != 0) {
            float height = 1 + 8 * ((float)be.getCount()/be.getMaxContain());

            Matrix4f m4f = matrixStackIn.last().pose();
            buffer.vertex(m4f, -6, -6, -height).color(255, 255, 255, 255).uv(i.getU0(), center)
                    .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
            buffer.vertex(m4f, -6, 6, -height).color(255, 255, 255, 255).uv(i.getU0(), i.getV0())
                    .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
            buffer.vertex(m4f, 6, 6, -height).color(255, 255, 255, 255).uv(u1, i.getV0())
                    .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
            buffer.vertex(m4f, 6, -6, -height).color(255, 255, 255, 255).uv(u1, center)
                    .uv2(combinedLightIn).normal(0, 0, 1).endVertex();

            if(height >= 4) {
                buffer.vertex(m4f, -6, 6, -height).color(255, 255, 255, 255).uv(i.getU0(), center)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
                buffer.vertex(m4f, -6, 6, 3-height).color(255, 255, 255, 255).uv(i.getU0(), v1)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
                buffer.vertex(m4f, 6, 6, 3-height).color(255, 255, 255, 255).uv(u1, v1)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
                buffer.vertex(m4f, 6, 6, -height).color(255, 255, 255, 255).uv(u1, center)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
            }
            if(height >= 7) {
                buffer.vertex(m4f, -6, 6, 3-height).color(255, 255, 255, 255).uv(i.getU0(), center)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
                buffer.vertex(m4f, -6, 6, 6-height).color(255, 255, 255, 255).uv(i.getU0(), v1)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
                buffer.vertex(m4f, 6, 6, 6-height).color(255, 255, 255, 255).uv(u1, v1)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
                buffer.vertex(m4f, 6, 6, 3-height).color(255, 255, 255, 255).uv(u1, center)
                        .uv2(combinedLightIn).normal(0, 0, 1).endVertex();
            }

        }
        matrixStackIn.popPose();
    }
}
