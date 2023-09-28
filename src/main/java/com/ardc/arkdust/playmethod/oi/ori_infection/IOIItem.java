package com.ardc.arkdust.playmethod.oi.ori_infection;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public interface IOIItem{
    int getOILevel();//Դʯ��Ⱦ�ȼ�

    int doDamage();//��ɵļ�Ȩ�˺�

    int guaranteeDamage();//��ɵı����˺�

    int playerOIPointAdd();//Ϊ������ʵ���Ⱦ�ȼ�

    int guaranteePlayerOIPointAdd();//��������Ƿ��ܽ�����Ʒ����߸�Ⱦ�ȼ�

    default boolean ifThrow(){//�Ƿ��ڲ��ܽ��ܺ�Ѹ�ٶ���
        return true;
    }

    default Component transText(){//���޷�����ʱ��������Ϣ
        return Component.translatable("pma.oi.getOIItem");
    }

    default Component infoAddOfOI(){//��ӵ���Ϣ
        return Component.translatable("pma.oi.OIInfo",getOILevel()).withStyle(ChatFormatting.RED);
    }
}
