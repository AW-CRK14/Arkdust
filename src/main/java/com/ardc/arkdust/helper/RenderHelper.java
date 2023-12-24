package com.ardc.arkdust.helper;

import com.ardc.arkdust.gui.widget.RenderOverrideSlot;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

public class RenderHelper {
    public static final Logger LOGGER = LogManager.getLogger();
    public static Vector4f m4fPosTrans(Matrix4f m4f, float x, float y, float z){
        Vector4f v4f = new Vector4f(x, y, z, 1.0F);
        v4f.mul(m4f);
        return v4f;
    }

    public static Color itemStackColor(ItemStack stack){
        try {
            if (stack == null || stack.isEmpty()) return new Color(1F, 1F, 1F);
            return new Color(stack.getDisplayName().getStyle().getColor().getValue());
        }catch (Throwable ignore){
            return new Color(1F,1F,1F);
        }
    }


    public static void renderItem(GuiGraphics stack,ItemStack itemStack, int x, int y,float scale) {
        if (!itemStack.isEmpty()) {
            Level level = Minecraft.getInstance().level;
            Player player = Minecraft.getInstance().player;

            BakedModel bakedmodel = Minecraft.getInstance().getItemRenderer().getModel(itemStack, level, player, 0);
            stack.pose().pushPose();
            stack.pose().translate(x + 8,y + 8,150);

            try {
                stack.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                stack.pose().scale(16.0F * scale, 16.0F * scale, 16.0F * scale);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) Lighting.setupForFlatItems();
                Minecraft.getInstance().getItemRenderer().render(itemStack, ItemDisplayContext.GUI, false, stack.pose(), stack.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                stack.flush();
                if (flag) Lighting.setupFor3DItems();
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering item");
                CrashReportCategory crashreportcategory = crashreport.addCategory("Item being rendered");
                crashreportcategory.setDetail("Item Type", () -> String.valueOf(itemStack.getItem()));
                crashreportcategory.setDetail("Registry Name", () -> String.valueOf(ForgeRegistries.ITEMS.getKey(itemStack.getItem())));
                crashreportcategory.setDetail("Item Damage", () -> String.valueOf(itemStack.getDamageValue()));
                crashreportcategory.setDetail("Item NBT", () -> String.valueOf(itemStack.getTag()));
                crashreportcategory.setDetail("Item Foil", () -> String.valueOf(itemStack.hasFoil()));
                throw new ReportedException(crashreport);
            }

            stack.pose().popPose();
        }
    }

    public static void renderItemDecorations(Font font, GuiGraphics stack, ItemStack itemStack, int x, int y, @Nullable String count, float countScale) {
        if (!itemStack.isEmpty()) {
            stack.pose().pushPose();
            if (itemStack.getCount() != 1 || count != null) {
                stack.pose().pushPose();
                String s = count == null ? String.valueOf(itemStack.getCount()) : count;
                stack.pose().translate(x+17, y+16, 200.0F);
                stack.pose().scale(countScale,countScale,1);
                stack.drawString(font, s, - font.width(s), -7, 16777215, true);
                stack.pose().popPose();
            }

            if (itemStack.isBarVisible()) {
                int l = itemStack.getBarWidth();
                int i = itemStack.getBarColor();
                int j = x + 2;
                int k = y + 13;
                stack.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
                stack.fill(RenderType.guiOverlay(), j, k, j + l, k + 1, i | -16777216);
            }

            LocalPlayer localplayer = Minecraft.getInstance().player;
            float f = localplayer == null ? 0.0F : localplayer.getCooldowns().getCooldownPercent(itemStack.getItem(), Minecraft.getInstance().getFrameTime());
            if (f > 0.0F) {
                int i1 = y + Mth.floor(16.0F * (1.0F - f));
                int j1 = i1 + Mth.ceil(16.0F * f);
                stack.fill(RenderType.guiOverlay(), x, i1, x + 16, j1, Integer.MAX_VALUE);
            }

            stack.pose().popPose();
            net.minecraftforge.client.ItemDecoratorHandler.of(itemStack).render(stack, font, itemStack, x, y);
        }
    }

    public static void renderEmpIndItemDecorations(Font font, GuiGraphics stack, ItemStack itemStack, int x, int y, @Nullable String count, float alpha) {
        if (!itemStack.isEmpty()) {
            stack.pose().pushPose();
            if (itemStack.getCount() != 1 || count != null) {
                stack.pose().pushPose();
                String s = count == null ? String.valueOf(itemStack.getCount()) : count;
                stack.pose().translate(10, 20, 200.0F);
                stack.pose().scale(1.25F,1.25F,1);
                stack.setColor(1,1,1,1);
                stack.drawString(font, s, - font.width(s)/2, -3, 0xFFFFFF, false);
                stack.setColor(1,1,1,alpha);
                stack.pose().popPose();
            }

            if (itemStack.isBarVisible()) {
                float c = itemStack.getBarWidth() / 13F;
                int i = itemStack.getBarColor();

                float r = (float)FastColor.ARGB32.red(i) / 255.0F;
                float g = (float)FastColor.ARGB32.green(i) / 255.0F;
                float b = (float)FastColor.ARGB32.blue(i) / 255.0F;

                stack.setColor(r, g, b, alpha * 0.2F);
                stack.blit(RenderOverrideSlot.EmpIndSlot.SLOT_BELOW,0,19,0,0,20,1,20,1);
                stack.setColor(r, g, b, alpha);
                stack.blit(RenderOverrideSlot.EmpIndSlot.SLOT_BELOW,0,19,0,0,(int)(20 * c),1,20,1);
                stack.setColor(1,1,1,alpha);
            }else {
                Color color = RenderHelper.itemStackColor(itemStack);
                stack.setColor(color.getRed()/255F,color.getGreen()/255F,color.getBlue()/255F,alpha * 0.7F);
                stack.blit(RenderOverrideSlot.EmpIndSlot.SLOT_BELOW,0,19,0,0,20,1,20,1);
                stack.setColor(1,1,1,alpha);
            }


//            LocalPlayer localplayer = Minecraft.getInstance().player;
//            float f = localplayer == null ? 0.0F : localplayer.getCooldowns().getCooldownPercent(itemStack.getItem(), Minecraft.getInstance().getFrameTime());
//            if (f > 0.0F) {
//                int i1 = y + Mth.floor(16.0F * (1.0F - f));
//                int j1 = i1 + Mth.ceil(16.0F * f);
//                stack.fill(RenderType.guiOverlay(), x, i1, x + 16, j1, Integer.MAX_VALUE);
//            }

            stack.pose().popPose();
            net.minecraftforge.client.ItemDecoratorHandler.of(itemStack).render(stack, font, itemStack, x, y);
        }
    }


    public static Font getFontInstance(ResourceLocation id) {
        FontManager manager = Minecraft.getInstance().fontManager;
        return new Font((r)->{
            FontSet set = manager.fontSets.get(id);
            if(set == null){
                LOGGER.warn("[ArdHelper-Render-FontIns]Can't get font set for resource location:{},font set elements:{}",id,manager.fontSets);
                set = manager.missingFontSet;
            }
            return set;
            },true);
    }

    public static void vertex(GuiGraphics stack, BufferBuilder builder, Vector4f relativeV4f, Color color, float u, float v, int overlay, int light){
        relativeV4f.mul(stack.pose().last().pose());
        Vector3f v3f = new Vector3f(0,0,0);
        v3f.mul(stack.pose().last().normal());
        builder.vertex(relativeV4f.x(),relativeV4f.y(),relativeV4f.z(),color.getRed() / 256F,color.getBlue() / 256F,color.getGreen() / 256F,color.getAlpha() / 256F,u,v,overlay,light,v3f.x(),v3f.y(),v3f.z());
    }
}
