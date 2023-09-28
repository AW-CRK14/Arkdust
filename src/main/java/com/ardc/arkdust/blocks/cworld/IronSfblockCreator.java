package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.block_entity.SFBlockCreatorBE;
import com.ardc.arkdust.blockstate.RotateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.Collections;
import java.util.List;

public class IronSfblockCreator extends RotateBlock implements EntityBlock {

    public IronSfblockCreator() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(2, 120).noOcclusion());
    }

//    @Override
//    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        return runSFBlockCreator_Use(ItemRegistry.iron_structure_frame.get(), Items.IRON_INGOT, worldIn, pos, player);
//    }

//一眼丁真 鉴定为一坨使
//    public static ActionResultType runSFBlockCreator_Use(Item RETURN_ITEM, Item CAN_CREATE, World worldIn, BlockPos pos, PlayerEntity player) {
//        //获取方块实体位置
//        SFBlockCreatorBE SFBlockCrer = (SFBlockCreatorBE) worldIn.getBlockEntity(pos);
//        //定义返回物
//        ItemStack return_item = new ItemStack(RETURN_ITEM, 1);
//        return_item.setCount(1);
//        if (SFBlockCrer.count >= 16) {
//            SFBlockCrer.clean();//如果达到16个，重置count
//            ItemHandlerHelper.giveItemToPlayer(player, return_item);
//            return ActionResultType.SUCCESS;
//        } else if (!(player.getMainHandItem().getItem() == CAN_CREATE)) {//主手物品不可用时返回跳过
//            return ActionResultType.PASS;
//        } else if (Screen.hasControlDown()) {
//            int input = 16 - SFBlockCrer.count;
//            int handCount = player.getMainHandItem().getCount();//获取玩家手中的物品数量
//            input = Math.min(input, handCount);//取最值防止误清或其他问题
//            int count = SFBlockCrer.increaseCount(input);
//            player.getMainHandItem().shrink(input);//将玩家手中的物品减少
//            if (count >= 16) {
//                ItemHandlerHelper.giveItemToPlayer(player, return_item);
//                SFBlockCrer.clean();//重置方块实体数值
//            }
//            infoThrow(player,worldIn,count,input);
//            return ActionResultType.SUCCESS;
//        } else {
//            int count = SFBlockCrer.increaseCount(1);
//            player.getMainHandItem().shrink(1);//将玩家手中的物品减少一个
//            infoThrow(player,worldIn,count,1);
//            return ActionResultType.SUCCESS;
//        }
//    }
//
//    public static void infoThrow(PlayerEntity player, World worldIn, int count, int input){
//        if(!worldIn.isClientSide()){
//            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.add", input), false);
//            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.now_count", count), false);
//        }
//    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SFBlockCreatorBE(pos,state);
    }
}
