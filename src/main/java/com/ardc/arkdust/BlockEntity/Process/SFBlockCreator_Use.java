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
        //获取方块实体位置
        SFBlockCreatorBE SFBlockCrer = (SFBlockCreatorBE) worldIn.getBlockEntity(pos);
        //定义返回物
        ItemStack return_item = new ItemStack(RETURN_ITEM, 1);
        return_item.setCount(1);
        if (SFBlockCrer.count >= 16) {
            SFBlockCrer.clean();//如果达到16个，重置count
            ItemHandlerHelper.giveItemToPlayer(player, return_item);
            return ActionResultType.SUCCESS;
        } else if (!(player.getMainHandItem().getItem() == CAN_CREATE)) {//主手物品不可用时返回跳过
            return ActionResultType.PASS;
        } else if (Screen.hasControlDown()) {
            int input = 16 - SFBlockCrer.count;
            int handCount = player.getMainHandItem().getCount();//获取玩家手中的物品数量
            input = Math.min(input, handCount);//取最值防止误清或其他问题
            int count = SFBlockCrer.increaseCount(input);
            player.getMainHandItem().shrink(input);//将玩家手中的物品减少
            if (count >= 16) {
                ItemHandlerHelper.giveItemToPlayer(player, return_item);
                SFBlockCrer.clean();//重置方块实体数值
            }
            infoThrow(player,worldIn,count,input);
            return ActionResultType.SUCCESS;
        } else {
            int count = SFBlockCrer.increaseCount(1);
            player.getMainHandItem().shrink(1);//将玩家手中的物品减少一个
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
