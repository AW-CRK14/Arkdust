package com.ardc.arkdust.model.block;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;

public class BlueprintReduceBoxRenderer implements BlockEntityRenderer<BlueprintReduceBoxBE> {
    private static final Logger LOGGER = LogManager.getLogger();
    public BlueprintReduceBoxRenderer(BlockEntityRendererProvider.Context context) {
    }

    public static final ResourceLocation pic = new ResourceLocation(Utils.MOD_ID,"blocks/terra_blueprint/blueprint_stack");

    @Override
    public void render(BlueprintReduceBoxBE be, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5, 0, 0.5);
        matrixStackIn.mulPose(be.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getRotation());
        matrixStackIn.scale(0.0625F, 0.0625F, 0.0625F);

        TextureAtlasSprite i = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(pic);
        float center = i.getV0() * 0.25f + i.getV1() * 0.75f;
        float u1 = i.getU0() * 0.25f + i.getU1() * 0.75f;
        float v1 = i.getV0() * 0.0625f + i.getV1() * 0.9375f;

        VertexConsumer buffer = (VertexConsumer) bufferIn.getBuffer(RenderType.cutout());
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
