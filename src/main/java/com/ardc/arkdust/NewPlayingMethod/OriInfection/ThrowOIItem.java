package com.ardc.arkdust.NewPlayingMethod.OriInfection;

import net.minecraft.command.impl.GameModeCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Mod.EventBusSubscriber
public class ThrowOIItem {
    @SubscribeEvent
    public static void onPlayerGetOIItems(EntityItemPickupEvent event){
        Item item = event.getItem().getItem().getItem();
        ItemStack itemStack = event.getItem().getItem();
        PlayerEntity player = event.getPlayer();
        World world = player.level;
        if(item instanceof IOIItem && !world.isClientSide()){//�ж�����˿��Լ��Ƿ�Ϊ��Ⱦ����Ʒ
            player.hurt(OIDamage.ORIROCK_INFECTION,((IOIItem) item).guaranteeDamage());//��ɱ����˺�
            double playerOIResistanceLevel = 0;//TODO ���Դʯ����
            double playerOIResistanceAttenuation = 0;//TODO ���Դʯ����˥��
            double playerOIResistanceAddition = 0;//TODO ���Դʯ���Ը���
            //TODO guaranteePlayerOILevelAdd����
            if(((IOIItem) item).getOILevel() >= playerOIResistanceLevel - playerOIResistanceAttenuation + playerOIResistanceAddition){
                float getDamageBonus = (float) new OIMain.WorldOI().getWholeOIDataHere(world,player) / 100 + 1;//�����˺��ӳ�
                float itemCountBonus = (float) (1 + (itemStack.getCount() - 1) * 0.2);//������������
                float hurt = ((IOIItem) item).doDamage() * getDamageBonus * itemCountBonus - (float) playerOIResistanceLevel;//�������˺�
                player.hurt(OIDamage.ORIROCK_INFECTION, hurt);
                //TODO playerOILevelAdd����
                if(!player.isCreative() && ((IOIItem) item).ifThrow()){
                    player.displayClientMessage(new TranslationTextComponent("pma.oi.getOIItem").withStyle(TextFormatting.RED),false);//������Ϣ
                    Random r = new Random();//����������Թ��ж��Ƿ���ʧ��Ʒ
                    ItemEntity reItem = new ItemEntity(world,player.getX(),player.getY(),player.getZ(),r.nextBoolean() ? itemStack.copy(): ItemStack.EMPTY);//����������ʵ��
                    reItem.setPickUpDelay(300);//����ʰȡ��ʱ��ʮ���룩
                    itemStack.setCount(0);//���ԭ��Ʒ
                    world.addFreshEntity(reItem);//��ӵ�����ʵ��
                }
            }
        }
    }
}
