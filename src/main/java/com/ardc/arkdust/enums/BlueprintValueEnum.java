package com.ardc.arkdust.enums;

import net.minecraft.ChatFormatting;

public enum BlueprintValueEnum {
    NULL(ChatFormatting.DARK_GRAY),
    COMMON(ChatFormatting.WHITE),//�ף�Cϵ��û�а�ɫƷ����ͼ
    SPECIAL(ChatFormatting.DARK_GREEN),//��
    VALUABLE(ChatFormatting.BLUE),//��
    TREASURE(ChatFormatting.DARK_PURPLE),//�ϣ�Cϵ���ռ�������Ʒ�����������������ͨר�䣬���޷�������ͼ�ļ����߼���о��
    RARE(ChatFormatting.YELLOW),//���ؼ�ר�䣬��������ͼ�ļ����ؼ���о��
    EPIC(ChatFormatting.GOLD),//�ȣ��ǳ������ΪԤ��
    MIRACLE(ChatFormatting.RED),//�죬�Ҳ���Ϊ�һ���������
    ACTIVITY(ChatFormatting.DARK_AQUA);

    public final ChatFormatting formatting;
    BlueprintValueEnum(ChatFormatting formatting){
        this.formatting = formatting;
    }
}
