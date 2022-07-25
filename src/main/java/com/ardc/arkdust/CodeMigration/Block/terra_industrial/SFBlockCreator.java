package com.ardc.arkdust.CodeMigration.Block.terra_industrial;

import com.ardc.arkdust.BlockEntity.SFBlockCreatorBE;
import com.ardc.arkdust.BlockRegistry;
import com.ardc.arkdust.CodeMigration.BlockState.RotateBlock;
import com.ardc.arkdust.CodeMigration.pre.PreBlock;
import com.ardc.arkdust.ItemRegistry;
import com.ardc.arkdust.type.TechMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class SFBlockCreator extends RotateBlock {
    public final Item CAN_CREATE;
    public final Block RETURN_ITEM;

    public SFBlockCreator() {
        super(Properties.of(Material.HEAVY_METAL).strength(2, 120).harvestTool(ToolType.PICKAXE));
        CAN_CREATE = Items.IRON_INGOT;//获取制作物品需要的材料
        RETURN_ITEM = BlockRegistry.iron_structure_frame.get();//获取制作产物
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SFBlockCreatorBE();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        //获取方块实体位置
        SFBlockCreatorBE SFBlockCrer = (SFBlockCreatorBE) worldIn.getBlockEntity(pos);
        assert SFBlockCrer != null;
        System.out.println(SFBlockCrer.count);
        //定义返回物
        ItemStack return_item = new ItemStack(RETURN_ITEM,1);
        return_item.setCount(1);
        if (SFBlockCrer.count >= 16) {
            SFBlockCrer.clean();//如果达到16个，重置count
            ItemHandlerHelper.giveItemToPlayer(player,return_item);
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
                ItemHandlerHelper.giveItemToPlayer(player,return_item);
                SFBlockCrer.clean();//重置方块实体数值
            }
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.add", input), false);
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.now_count", count), false);
            return ActionResultType.SUCCESS;
        } else {
            int count = SFBlockCrer.increaseCount(1);
            player.getMainHandItem().shrink(1);//将玩家手中的物品减少一个
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.add", 1), false);
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.now_count", count), false);
            return ActionResultType.SUCCESS;
        }
    }

}
