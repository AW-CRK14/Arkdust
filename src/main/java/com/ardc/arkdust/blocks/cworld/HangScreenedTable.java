package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.block_entity.ScreenedTableBE;
import com.ardc.arkdust.blockstate.DropSelfBlock;
import com.ardc.arkdust.helper.LootHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class HangScreenedTable extends DropSelfBlock implements EntityBlock {
    public static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");

    public HangScreenedTable() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(2, 12).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(HAS_ITEM,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(HAS_ITEM);
        super.createBlockStateDefinition(builder);
    }

//    @Override
//    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        ScreenedTableBE blockBE = (ScreenedTableBE) worldIn.getBlockEntity(pos);
//        if (blockBE.hasItem) {//创建掉落物于此方块上方
//            worldIn.setBlock(new BlockPos(pos),state.setValue(HAS_ITEM,false),1);
//            ItemEntity drop = new ItemEntity(worldIn, pos.getX()+0.5F, pos.getY() + 1, pos.getZ()+0.5F, blockBE.getLoot());
//            worldIn.addFreshEntity(drop);
////            System.out.printf("%n    go to -> drop:%n    getValue[HAS_ITEM]->" + state.getValue(HAS_ITEM) + "%n    getDrop->" + drop);
//            return InteractionResult.SUCCESS;
//        }
////        System.out.printf("%n    go to -> test for in:");
//        testAndThrow(ScreenedTable.GRAVEL);
//        testAndThrow(ScreenedTable.SAND);
//        testAndThrow(ScreenedTable.RED_SAND);
//        testAndThrow(ScreenedTable.SOUL_SAND);
//        testAndThrow(ScreenedTable.PRISMARINE);
//        testAndThrow(ScreenedTable.BLACKSTONE_SAND);
////        System.out.printf("return:" + String.valueOf(returnPassOrNot ? ActionResultType.PASS : ActionResultType.SUCCESS) + "%nrunning end");
//        return returnPassOrNot ? ActionResultType.PASS : ActionResultType.SUCCESS;
//    }
//
//    public void testAndThrow(ScreenedTable to, Item thisItem, ScreenedTableBE be,) {
//        if (thisItem == to.getKeyItem() && hardLevel >= to.getHardLevel() && returnPassOrNot) {//如果物品相等且要求物品等级小于机器等级
//            boolean test =  blockBE.tryToPlaceIn(thisItem, to.throwNewItem());//创建放入物品并返回是否成功的值
////            if(!world.isClientSide()) {
////                System.out.println("  test:" + test);
////                System.out.println("  state:" + state);
////            }
//            world.setBlock(new BlockPos(pos),state.setValue(HAS_ITEM,test),1);//重置方块状态
//            player.getMainHandItem().shrink(1);//扣除玩家手上的物品一个
//            returnPassOrNot = false;
////            if(!world.isClientSide()) {
////                System.out.println("  HAS_ITEM:" + state.getValue(HAS_ITEM));
////                System.out.println("  state:" + state);
////                System.out.println("  blockState:" + state.getBlockState());
////                System.out.println("");
////            }
//        }
////        System.out.printf("%n    hangItem ->" + thisItem + "%n    to ->" + to + "%n    passOrNot ->" + returnPassOrNot + "%n");
////        System.out.printf("%n    handItem == KeyItem? ->" + String.valueOf(thisItem == to.getKeyItem()) + "%n    hardLevel >= RecipeHardLevel ->" + String.valueOf(hardLevel >= to.getHardLevel()) + "%n%n");
//    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return LootHelper.dropSelfWhenNoLoot(state,builder);
    }

    public void onRemove(BlockState block, Level world, BlockPos pos, BlockState toBlock, boolean iif) {
        super.onRemove(block,world,pos,toBlock,iif);
        ScreenedTableBE blockBE = (ScreenedTableBE) world.getBlockEntity(pos);
        if (blockBE!=null && block.hasBlockEntity() && !block.is(toBlock.getBlock())) {
            world.removeBlockEntity(pos);
            ItemEntity drop = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), blockBE.getLootItem());
            world.addFreshEntity(drop);
        }
//        System.out.printf("%nblock on remove:%n    blockstate1:" + block + "%n    blockstate2:" + toBlock);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ScreenedTableBE(pos,state);
    }
}
