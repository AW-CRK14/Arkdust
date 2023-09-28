package com.ardc.arkdust.helper;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AdvancementHelper {
    public static void tryAddAdvancementToPlayer(Player entity, ResourceLocation resource){
        if(entity instanceof ServerPlayer){
            Advancement adv = ((ServerPlayer) entity).server.getAdvancements().getAdvancement(resource);
            if(adv == null) return;
            AdvancementProgress ap = ((ServerPlayer) entity).getAdvancements().getOrStartProgress(adv);
            if(!ap.isDone()){
                for (String s : ap.getRemainingCriteria()) {
                    ((ServerPlayer) entity).getAdvancements().award(adv, s);
                }
            }
        }
    }
}
