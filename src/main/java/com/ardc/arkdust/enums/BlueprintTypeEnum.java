package com.ardc.arkdust.enums;

public enum BlueprintTypeEnum {
    NULL(BlueprintValueEnum.COMMON),
    ARMOR(BlueprintValueEnum.TREASURE),
    ENERGY_SHIELD(BlueprintValueEnum.TREASURE),//注：包含s5防弹夹层与s6电磁护盾
    AUTO_PROTECT(BlueprintValueEnum.TREASURE),//注：包含s7主动反击装置（触发时击飞小范围敌人）与s8主动治疗装置

    SWORD(BlueprintValueEnum.TREASURE),//注：斧头，小刀，镰刀也算
    EPEE(BlueprintValueEnum.TREASURE),//注：锤子，链锤，锯子也算
    ORI_SWORD(BlueprintValueEnum.TREASURE),
    POLEARMS(BlueprintValueEnum.TREASURE),//注：长柄类武器，包括旗子
    SMALL_WEAPON(BlueprintValueEnum.TREASURE),//注：包含拳套，拳刺，钩爪等难归类物理近战武器
    SHIELD(BlueprintValueEnum.TREASURE),

    BOW(BlueprintValueEnum.TREASURE),
    CROSSBOW(BlueprintValueEnum.TREASURE),
    HEAVE_PROJECTILE(BlueprintValueEnum.TREASURE),//注：重型投掷物，比如迷迭香等
    LIGHT_PROJECTILE(BlueprintValueEnum.TREASURE),//注：轻型投掷物，比如晓歌等

    WAND(BlueprintValueEnum.TREASURE),
    MAGIC_BALL(BlueprintValueEnum.TREASURE),
    SPELL_ENGINE(BlueprintValueEnum.TREASURE),//注：表示上述两种法器外的所有法术装置，主要指手搓

    PISTOL(BlueprintValueEnum.TREASURE),
    RIFLE(BlueprintValueEnum.TREASURE),//注：包含冲锋枪
    SNIPER(BlueprintValueEnum.TREASURE),
    HOWITZER(BlueprintValueEnum.TREASURE),
    SHOTGUN(BlueprintValueEnum.TREASURE),
    JET(BlueprintValueEnum.TREASURE),//注：喷射类持续伤害武器
    AUXILIARY(BlueprintValueEnum.RARE),//注：辅助枪械，比如阿的枪

    AUTOMATIC_IMPROVE(BlueprintValueEnum.VALUABLE),
    WEAPON_ACCESSORY(BlueprintValueEnum.RARE),//注：只有专武与特殊武器可以安装配件 暂时没想好
    ARMOR_ACCESSORY(BlueprintValueEnum.RARE),
    MODULES(BlueprintValueEnum.RARE),//模组
    C6_STORAGE(BlueprintValueEnum.TREASURE),
    C7_TELEPORT(BlueprintValueEnum.TREASURE),
    C8_ENERGY(BlueprintValueEnum.TREASURE),
    C9_GEAR(BlueprintValueEnum.TREASURE),
    C10_ENCHANT(BlueprintValueEnum.TREASURE),
    C11_NERVE(BlueprintValueEnum.TREASURE),
    BIOLOGY(BlueprintValueEnum.RARE),
    FURNITURE(BlueprintValueEnum.VALUABLE),
    TACTICS(BlueprintValueEnum.VALUABLE),
    EXCLUSIVE(BlueprintValueEnum.RARE),//专武
    MEDICINE(BlueprintValueEnum.VALUABLE),
    ALLOY(BlueprintValueEnum.RARE),
    MATERIAL(BlueprintValueEnum.RARE),
    TREASURE(BlueprintValueEnum.EPIC),
    OBJ(BlueprintValueEnum.RARE);

    public final BlueprintValueEnum maxValue;
    BlueprintTypeEnum(BlueprintValueEnum max){
        this.maxValue = max;
    }
}
