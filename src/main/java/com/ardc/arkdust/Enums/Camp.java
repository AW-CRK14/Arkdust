package com.ardc.arkdust.Enums;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Arrays;
import java.util.List;

public enum Camp {
    PLAYER("player", Arrays.asList(ServerPlayerEntity.class,PlayerEntity.class, WolfEntity.class, VillagerEntity.class, CatEntity.class));


    private final String name;
    private final List<Class<? extends LivingEntity>> entityClassList;

    Camp(String name, List<Class<? extends LivingEntity>> entityList){
        this.name = name;
        this.entityClassList = entityList;
    }

    public String getCampName(){
        return name;
    }

    public List<Class<? extends LivingEntity>> getCampEntityClassList(){
        return entityClassList;
    }


}
