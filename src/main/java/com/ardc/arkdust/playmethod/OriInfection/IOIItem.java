package com.ardc.arkdust.playmethod.OriInfection;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public interface IOIItem{
    int getOILevel();//Դʯ��Ⱦ�ȼ�

    int doDamage();//��ɵļ�Ȩ�˺�

    int guaranteeDamage();//��ɵı����˺�

    int playerOIPointAdd();//Ϊ������ʵ���Ⱦ�ȼ�

    int guaranteePlayerOIPointAdd();//��������Ƿ��ܽ�����Ʒ����߸�Ⱦ�ȼ�

    default boolean ifThrow(){//�Ƿ��ڲ��ܽ��ܺ�Ѹ�ٶ���
        return true;
    }

    default TranslationTextComponent transText(){//���޷�����ʱ��������Ϣ
        return new TranslationTextComponent("pma.oi.getOIItem");
    }

    default IFormattableTextComponent infoAddOfOI(){//��ӵ���Ϣ
        return new TranslationTextComponent("pma.oi.OIInfo",getOILevel()).withStyle(TextFormatting.RED);
    }
}
