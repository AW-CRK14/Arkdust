package com.ardc.arkdust.enums;

public enum BlueprintTypeEnum {
    NULL(BlueprintValueEnum.COMMON),
    ARMOR(BlueprintValueEnum.TREASURE),
    ENERGY_SHIELD(BlueprintValueEnum.TREASURE),//ע������s5�����в���s6��Ż���
    AUTO_PROTECT(BlueprintValueEnum.TREASURE),//ע������s7��������װ�ã�����ʱ����С��Χ���ˣ���s8��������װ��

    SWORD(BlueprintValueEnum.TREASURE),//ע����ͷ��С��������Ҳ��
    EPEE(BlueprintValueEnum.TREASURE),//ע�����ӣ�����������Ҳ��
    ORI_SWORD(BlueprintValueEnum.TREASURE),
    POLEARMS(BlueprintValueEnum.TREASURE),//ע����������������������
    SMALL_WEAPON(BlueprintValueEnum.TREASURE),//ע������ȭ�ף�ȭ�̣���צ���ѹ��������ս����
    SHIELD(BlueprintValueEnum.TREASURE),

    BOW(BlueprintValueEnum.TREASURE),
    CROSSBOW(BlueprintValueEnum.TREASURE),
    HEAVE_PROJECTILE(BlueprintValueEnum.TREASURE),//ע������Ͷ��������Ե����
    LIGHT_PROJECTILE(BlueprintValueEnum.TREASURE),//ע������Ͷ������������

    WAND(BlueprintValueEnum.TREASURE),
    MAGIC_BALL(BlueprintValueEnum.TREASURE),
    SPELL_ENGINE(BlueprintValueEnum.TREASURE),//ע����ʾ�������ַ���������з���װ�ã���Ҫָ�ִ�

    PISTOL(BlueprintValueEnum.TREASURE),
    RIFLE(BlueprintValueEnum.TREASURE),//ע���������ǹ
    SNIPER(BlueprintValueEnum.TREASURE),
    HOWITZER(BlueprintValueEnum.TREASURE),
    SHOTGUN(BlueprintValueEnum.TREASURE),
    JET(BlueprintValueEnum.TREASURE),//ע������������˺�����
    AUXILIARY(BlueprintValueEnum.RARE),//ע������ǹе�����簢��ǹ

    AUTOMATIC_IMPROVE(BlueprintValueEnum.VALUABLE),
    WEAPON_ACCESSORY(BlueprintValueEnum.RARE),//ע��ֻ��ר���������������԰�װ��� ��ʱû���
    ARMOR_ACCESSORY(BlueprintValueEnum.RARE),
    MODULES(BlueprintValueEnum.RARE),//ģ��
    C6_STORAGE(BlueprintValueEnum.TREASURE),
    C7_TELEPORT(BlueprintValueEnum.TREASURE),
    C8_ENERGY(BlueprintValueEnum.TREASURE),
    C9_GEAR(BlueprintValueEnum.TREASURE),
    C10_ENCHANT(BlueprintValueEnum.TREASURE),
    C11_NERVE(BlueprintValueEnum.TREASURE),
    BIOLOGY(BlueprintValueEnum.RARE),
    FURNITURE(BlueprintValueEnum.VALUABLE),
    TACTICS(BlueprintValueEnum.VALUABLE),
    EXCLUSIVE(BlueprintValueEnum.RARE),//ר��
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
