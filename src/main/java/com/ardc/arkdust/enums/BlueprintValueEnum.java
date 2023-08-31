package com.ardc.arkdust.enums;

import net.minecraft.util.text.TextFormatting;

public enum BlueprintValueEnum {
    NULL(TextFormatting.DARK_GRAY),
    COMMON(TextFormatting.WHITE),//白，C系列没有白色品质蓝图
    SPECIAL(TextFormatting.DARK_GREEN),//绿
    VALUABLE(TextFormatting.BLUE),//蓝
    TREASURE(TextFormatting.DARK_PURPLE),//紫，C系列终极类型物品，顶级武器配件，普通专武，有限泛用性蓝图文件，高级核芯等
    RARE(TextFormatting.YELLOW),//金，特级专武，泛用性蓝图文件，特级核芯等
    EPIC(TextFormatting.GOLD),//橙，非常珍贵，作为预留
    MIRACLE(TextFormatting.RED),//红，我不认为我会做到这里
    ACTIVITY(TextFormatting.DARK_AQUA);

    public final TextFormatting formatting;
    BlueprintValueEnum(TextFormatting formatting){
        this.formatting = formatting;
    }
}
