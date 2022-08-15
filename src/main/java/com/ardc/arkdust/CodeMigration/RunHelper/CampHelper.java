package com.ardc.arkdust.CodeMigration.RunHelper;

import com.ardc.arkdust.Enums.Camp;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class CampHelper {
    public static boolean ifEntityBelongToCamp(Camp camp, LivingEntity entity){
        Class<? extends LivingEntity> entityClass = entity.getClass();
//        if(entity instanceof PlayerEntity) entityClass = PlayerEntity.class;
        for(Class<? extends LivingEntity> i : camp.getCampEntityClassList()){
            if(i == entityClass){
                return true;
            }
        }
        return false;
    }
    public static boolean ifEntityBelongToCamp(List<Camp> camp, LivingEntity entity){
        for(Camp c:camp){
            if(ifEntityBelongToCamp(c,entity)) return true;
        }
        return false;
    }
    public static List<Camp> getEntityInCamp(LivingEntity entity){
        Class<? extends LivingEntity> entityClass = entity.getClass();
        List<Camp> list = new ArrayList<>();
        for(Camp camp : Camp.values()){
            for(Class<? extends LivingEntity> i : camp.getCampEntityClassList()){
                if(i == entityClass){
                    list.add(camp);
                }
            }
        }
        return list;
    }
}
