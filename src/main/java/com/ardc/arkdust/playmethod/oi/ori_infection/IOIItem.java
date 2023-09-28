package com.ardc.arkdust.playmethod.oi.ori_infection;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public interface IOIItem{
    int getOILevel();//源石感染等级

    int doDamage();//造成的加权伤害

    int guaranteeDamage();//造成的保底伤害

    int playerOIPointAdd();//为玩家提高实体感染等级

    int guaranteePlayerOIPointAdd();//不论玩家是否能接受物品，提高感染等级

    default boolean ifThrow(){//是否在不能接受后迅速丢弃
        return true;
    }

    default Component transText(){//在无法接受时发出的信息
        return Component.translatable("pma.oi.getOIItem");
    }

    default Component infoAddOfOI(){//添加的信息
        return Component.translatable("pma.oi.OIInfo",getOILevel()).withStyle(ChatFormatting.RED);
    }
}
