package com.ardc.arkdust.render.world;

import com.ardc.arkdust.worldgen.biome.BiomeKey;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FogExtraRender {
    private static int counter;
    private static ResourceLocation biome;

    @SubscribeEvent
    public static void renderExtraFog(ViewportEvent.RenderFog event){
        Player playerEntity = Minecraft.getInstance().player;
        Holder<Biome> biome = playerEntity.level().getBiome(playerEntity.blockPosition());
        if(biome.is(BiomeKey.CW$FAULT_LINE) && (playerEntity.level().isRaining()||playerEntity.level().isThundering())){
            if(counter<1000) counter++;
        }else {
            if(counter>0) counter--;
        }
        if(counter>0 && event.getType() == FogType.NONE){
            float base = 0.032F*counter + event.getFarPlaneDistance()*(1-counter/1000F);

            event.setFarPlaneDistance(base);
            event.setNearPlaneDistance(base*0.75F);
        }
    }

//    private static void renderFog(Biome colorFrom){
//        String hex = Integer.toHexString(colorFrom.getFogColor());
//        float r = Integer.parseInt(hex.substring(0,2),16)/255F;
//        float g = Integer.parseInt(hex.substring(2,4),16)/255F;
//        float b = Integer.parseInt(hex.substring(4,6),16)/255F;
//        RenderSystem.fog(GL11.GL_FOG_COLOR,r,g,b,0.4F);
//    }
}
