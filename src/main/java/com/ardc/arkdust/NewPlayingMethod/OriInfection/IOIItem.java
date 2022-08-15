package com.ardc.arkdust.NewPlayingMethod.OriInfection;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public interface IOIItem{
    int getOILevel();//Դʯ��Ⱦ�ȼ�

    int doDamage();//��ɵļ�Ȩ�˺�

    int guaranteeDamage();//��ɵı����˺�

    default double playerOILevelAdd(){//Ϊ������ʵ���Ⱦ�ȼ�
        return 0;
    }

    default double guaranteePlayerOILevelAdd(){//��������Ƿ��ܽ�����Ʒ����߸�Ⱦ�ȼ�
        return 0;
    }

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
