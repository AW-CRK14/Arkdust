package com.ardc.arkdust.registry;

import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import com.ardc.arkdust.model.block.BlueprintReduceBoxRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TexturesRegistry {
    @SubscribeEvent
    public static void texturesAtlasAttach(TextureStitchEvent.Pre event){
        if(event.getMap().location().equals(PlayerContainer.BLOCK_ATLAS)){
            event.addSprite(BlueprintReduceBoxRenderer.pic);
        }
    }
}
