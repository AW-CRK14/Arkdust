package com.ardc.arkdust.BlockEntity.Process;

import com.ardc.arkdust.BlockEntity.ScreenedTableBE;
import com.ardc.arkdust.Enums.BlockEntityAbout.ScreenedTable;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OreScreened_Use {
    public static boolean returnPassOrNot = true;
    public static BlockState state;
    public static BooleanProperty HAS_ITEM;
    public static int hardLevel;
    public static ScreenedTableBE blockBE;
    public static Item thisItem;
    public static World world;
    public static BlockPos pos;
    public static PlayerEntity player;

    public static ActionResultType oreScreened_Use(BlockState astate, World worldIn, BlockPos apos, PlayerEntity aplayer, BooleanProperty aHAS_ITEM, int ahardLevel){
        //传参
        returnPassOrNot = true;
        world = worldIn;
        state = astate;
        HAS_ITEM = aHAS_ITEM;
        hardLevel = ahardLevel;
        blockBE = (ScreenedTableBE) worldIn.getBlockEntity(apos);
        pos = apos;
        player = aplayer;
        thisItem = aplayer.getMainHandItem().getItem();//玩家手上的物品
//        System.out.printf("%nuse running:    hasItem? ->" + blockBE.hasItem);
        if (Screen.hasControlDown()){//测试用：输出state内容
//            System.out.println(state);
            return ActionResultType.SUCCESS;
        }
        if (blockBE.hasItem) {//创建掉落物于此方块上方
            world.setBlock(new BlockPos(pos),state.setValue(HAS_ITEM,false),1);
            ItemEntity drop = new ItemEntity(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), blockBE.getLoot());
            worldIn.addFreshEntity(drop);
//            System.out.printf("%n    go to -> drop:%n    getValue[HAS_ITEM]->" + state.getValue(HAS_ITEM) + "%n    getDrop->" + drop);
            return ActionResultType.SUCCESS;
        }
//        System.out.printf("%n    go to -> test for in:");
        testAndThrow(ScreenedTable.GRAVEL);
        testAndThrow(ScreenedTable.SAND);
        testAndThrow(ScreenedTable.RED_SAND);
        testAndThrow(ScreenedTable.SOUL_SAND);
        testAndThrow(ScreenedTable.PRISMARINE);
        testAndThrow(ScreenedTable.BLACKSTONE_SAND);
//        System.out.printf("return:" + String.valueOf(returnPassOrNot ? ActionResultType.PASS : ActionResultType.SUCCESS) + "%nrunning end");
        return returnPassOrNot ? ActionResultType.PASS : ActionResultType.SUCCESS;
    }

    public static void testAndThrow(ScreenedTable to) {
        if (thisItem == to.getKeyItem() && hardLevel >= to.getHardLevel() && returnPassOrNot) {//如果物品相等且要求物品等级小于机器等级
            boolean test =  blockBE.tryToPlaceIn(thisItem, to.throwNewItem());//创建放入物品并返回是否成功的值
//            if(!world.isClientSide()) {
//                System.out.println("  test:" + test);
//                System.out.println("  state:" + state);
//            }
            world.setBlock(new BlockPos(pos),state.setValue(HAS_ITEM,test),1);//重置方块状态
            player.getMainHandItem().shrink(1);//扣除玩家手上的物品一个
            returnPassOrNot = false;
//            if(!world.isClientSide()) {
//                System.out.println("  HAS_ITEM:" + state.getValue(HAS_ITEM));
//                System.out.println("  state:" + state);
//                System.out.println("  blockState:" + state.getBlockState());
//                System.out.println("");
//            }
        }
//        System.out.printf("%n    hangItem ->" + thisItem + "%n    to ->" + to + "%n    passOrNot ->" + returnPassOrNot + "%n");
//        System.out.printf("%n    handItem == KeyItem? ->" + String.valueOf(thisItem == to.getKeyItem()) + "%n    hardLevel >= RecipeHardLevel ->" + String.valueOf(hardLevel >= to.getHardLevel()) + "%n%n");
    }
}
