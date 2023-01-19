package com.ardc.arkdust.RunHelper;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

public class AdvancementHelper {
    public static void tryAddAdvancementToPlayer(PlayerEntity entity,ResourceLocation resource){
        if(entity instanceof ServerPlayerEntity){
            Advancement adv = ((ServerPlayerEntity) entity).server.getAdvancements().getAdvancement(resource);
            if(adv == null) return;
            AdvancementProgress ap = ((ServerPlayerEntity) entity).getAdvancements().getOrStartProgress(adv);
            if(!ap.isDone()){
                for (String s : ap.getRemainingCriteria()) {
                    ((ServerPlayerEntity) entity).getAdvancements().award(adv, s);
                }
            }
        }
    }
}
