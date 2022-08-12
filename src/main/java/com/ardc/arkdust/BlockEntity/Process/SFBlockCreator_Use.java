package com.ardc.arkdust.BlockEntity.Process;

import com.ardc.arkdust.BlockEntity.SFBlockCreatorBE;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

public class SFBlockCreator_Use {
    public static ActionResultType runSFBlockCreator_Use(Item RETURN_ITEM, Item CAN_CREATE, World worldIn, BlockPos pos, PlayerEntity player) {
        //��ȡ����ʵ��λ��
        SFBlockCreatorBE SFBlockCrer = (SFBlockCreatorBE) worldIn.getBlockEntity(pos);
        //���巵����
        ItemStack return_item = new ItemStack(RETURN_ITEM, 1);
        return_item.setCount(1);
        if (SFBlockCrer.count >= 16) {
            SFBlockCrer.clean();//����ﵽ16��������count
            ItemHandlerHelper.giveItemToPlayer(player, return_item);
            return ActionResultType.SUCCESS;
        } else if (!(player.getMainHandItem().getItem() == CAN_CREATE)) {//������Ʒ������ʱ��������
            return ActionResultType.PASS;
        } else if (Screen.hasControlDown()) {
            int input = 16 - SFBlockCrer.count;
            int handCount = player.getMainHandItem().getCount();//��ȡ������е���Ʒ����
            input = Math.min(input, handCount);//ȡ��ֵ��ֹ�������������
            int count = SFBlockCrer.increaseCount(input);
            player.getMainHandItem().shrink(input);//��������е���Ʒ����
            if (count >= 16) {
                ItemHandlerHelper.giveItemToPlayer(player, return_item);
                SFBlockCrer.clean();//���÷���ʵ����ֵ
            }
            infoThrow(player,worldIn,count,input);
            return ActionResultType.SUCCESS;
        } else {
            int count = SFBlockCrer.increaseCount(1);
            player.getMainHandItem().shrink(1);//��������е���Ʒ����һ��
            infoThrow(player,worldIn,count,1);
            return ActionResultType.SUCCESS;
        }
    }

    public static void infoThrow(PlayerEntity player, World worldIn, int count, int input){
        if(!worldIn.isClientSide()){
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.add", input), false);
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.now_count", count), false);
        }
    }
}
