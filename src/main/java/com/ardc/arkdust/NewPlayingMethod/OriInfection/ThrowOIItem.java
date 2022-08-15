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
        if(item instanceof IOIItem && !world.isClientSide()){//判断世界端口以及是否为感染性物品
            player.hurt(OIDamage.ORIROCK_INFECTION,((IOIItem) item).guaranteeDamage());//造成保底伤害
            double playerOIResistanceLevel = 0;//TODO 玩家源石抗性
            double playerOIResistanceAttenuation = 0;//TODO 玩家源石抗性衰减
            double playerOIResistanceAddition = 0;//TODO 玩家源石抗性附加
            //TODO guaranteePlayerOILevelAdd提升
            if(((IOIItem) item).getOILevel() >= playerOIResistanceLevel - playerOIResistanceAttenuation + playerOIResistanceAddition){
                float getDamageBonus = (float) new OIMain.WorldOI().getWholeOIDataHere(world,player) / 100 + 1;//计算伤害加成
                float itemCountBonus = (float) (1 + (itemStack.getCount() - 1) * 0.2);//计算数量叠加
                float hurt = ((IOIItem) item).doDamage() * getDamageBonus * itemCountBonus - (float) playerOIResistanceLevel;//计算总伤害
                player.hurt(OIDamage.ORIROCK_INFECTION, hurt);
                //TODO playerOILevelAdd提升
                if(!player.isCreative() && ((IOIItem) item).ifThrow()){
                    player.displayClientMessage(new TranslationTextComponent("pma.oi.getOIItem").withStyle(TextFormatting.RED),false);//发送信息
                    Random r = new Random();//创建随机数以供判断是否消失物品
                    ItemEntity reItem = new ItemEntity(world,player.getX(),player.getY(),player.getZ(),r.nextBoolean() ? itemStack.copy(): ItemStack.EMPTY);//创建掉落物实体
                    reItem.setPickUpDelay(300);//设置拾取延时（十五秒）
                    itemStack.setCount(0);//清除原物品
                    world.addFreshEntity(reItem);//添加掉落物实体
                }
            }
        }
    }
}
