package com.ardc.arkdust.render.world;

import com.ardc.arkdust.registry.BiomeRegistry;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber
public class FogExtraRender {
    private static int counter;
    private static ResourceLocation biome;

    @SubscribeEvent
    public static void renderExtraFog(EntityViewRenderEvent.RenderFogEvent event){
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        Biome biome = playerEntity.level.getBiome(playerEntity.blockPosition());
        if(biome.getRegistryName().equals(BiomeRegistry.CW$FAULT_LINE.get().getRegistryName()) && (event.getInfo().getEntity().level.isRaining()||event.getInfo().getEntity().level.isThundering())){
            if(counter<1000) counter++;
        }else {
            if(counter>0) counter--;
        }
        if(counter>0 && event.getType() == FogRenderer.FogType.FOG_TERRAIN){
            RenderSystem.pushMatrix();

            float base = 0.032F*counter + event.getFarPlaneDistance()*(1-counter/1000F);

            RenderSystem.fogStart(base*0.75F);
            RenderSystem.fogEnd(base);

            RenderSystem.popMatrix();
        }
    }

    private static void renderFog(Biome colorFrom){
        String hex = Integer.toHexString(colorFrom.getFogColor());
        float r = Integer.parseInt(hex.substring(0,2),16)/255F;
        float g = Integer.parseInt(hex.substring(2,4),16)/255F;
        float b = Integer.parseInt(hex.substring(4,6),16)/255F;
        RenderSystem.fog(GL11.GL_FOG_COLOR,r,g,b,0.4F);
    }
}
