package com.ardc.arkdust.enums;

import net.minecraft.util.text.TextFormatting;

public enum BlueprintValueEnum {
    NULL(TextFormatting.DARK_GRAY),
    COMMON(TextFormatting.WHITE),//�ף�Cϵ��û�а�ɫƷ����ͼ
    SPECIAL(TextFormatting.DARK_GREEN),//��
    VALUABLE(TextFormatting.BLUE),//��
    TREASURE(TextFormatting.DARK_PURPLE),//�ϣ�Cϵ���ռ�������Ʒ�����������������ͨר�䣬���޷�������ͼ�ļ����߼���о��
    RARE(TextFormatting.YELLOW),//���ؼ�ר�䣬��������ͼ�ļ����ؼ���о��
    EPIC(TextFormatting.GOLD),//�ȣ��ǳ������ΪԤ��
    MIRACLE(TextFormatting.RED),//�죬�Ҳ���Ϊ�һ���������
    ACTIVITY(TextFormatting.DARK_AQUA);

    public final TextFormatting formatting;
    BlueprintValueEnum(TextFormatting formatting){
        this.formatting = formatting;
    }
}
