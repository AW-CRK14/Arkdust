package com.ardc.arkdust.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

import java.awt.*;

public class RenderHelper {
    public static Vector4f m4fPosTrans(Matrix4f m4f, float x, float y, float z){
        Vector4f v4f = new Vector4f(x, y, z, 1.0F);
        v4f.transform(m4f);
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



    public static void vertex(MatrixStack stack, BufferBuilder builder, Vector4f relativeV4f, ColorPack color, float u, float v, int overlay, int light){
        relativeV4f.transform(stack.last().pose());
        Vector3f v3f = new Vector3f(0,0,0);
        v3f.transform(stack.last().normal());
        builder.vertex(relativeV4f.x(),relativeV4f.y(),relativeV4f.z(),color.r,color.b,color.g,color.a,u,v,overlay,light,v3f.x(),v3f.y(),v3f.z());
    }


    public static ColorPack colorOf(byte red,byte green,byte blue,byte alpha){
        return new ColorPack(red, green, blue, alpha);
    }
    public static ColorPack colorOf(byte red,byte green,byte blue){
        return new ColorPack(red, green, blue, (byte) 255);
    }
    public static ColorPack color0(){
        return ColorPack.color0;
    }
    public static class ColorPack{
        public static final ColorPack color0 = new ColorPack((byte) 255,(byte)255,(byte)255,(byte)255);

        public byte r;
        public byte g;
        public byte b;
        public byte a;
        public ColorPack(byte red,byte green,byte blue,byte alpha){
            r= red;
            g = green;
            b = blue;
            a = alpha;
        }
    }
}
