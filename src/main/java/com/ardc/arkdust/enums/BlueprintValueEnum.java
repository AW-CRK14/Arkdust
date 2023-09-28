package com.ardc.arkdust.enums;

import net.minecraft.ChatFormatting;

public enum BlueprintValueEnum {
    NULL(ChatFormatting.DARK_GRAY),
    COMMON(ChatFormatting.WHITE),//白，C系列没有白色品质蓝图
    SPECIAL(ChatFormatting.DARK_GREEN),//绿
    VALUABLE(ChatFormatting.BLUE),//蓝
    TREASURE(ChatFormatting.DARK_PURPLE),//紫，C系列终极类型物品，顶级武器配件，普通专武，有限泛用性蓝图文件，高级核芯等
    RARE(ChatFormatting.YELLOW),//金，特级专武，泛用性蓝图文件，特级核芯等
    EPIC(ChatFormatting.GOLD),//橙，非常珍贵，作为预留
    MIRACLE(ChatFormatting.RED),//红，我不认为我会做到这里
    ACTIVITY(ChatFormatting.DARK_AQUA);

    public final ChatFormatting formatting;
    BlueprintValueEnum(ChatFormatting formatting){
        this.formatting = formatting;
    }
}
