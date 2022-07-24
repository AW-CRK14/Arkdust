package com.ardc.arkdust.CodeMigration.Block.terra_industrial;

import com.ardc.arkdust.BlockEntity.SFBlockCreatorBE;
import com.ardc.arkdust.CodeMigration.pre.PreBlock;
import com.ardc.arkdust.ItemRegistry;
import com.ardc.arkdust.type.TechMaterial;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import javax.annotation.Nullable;

public class SFBlockCreator extends PreBlock {
    public final Item CAN_CREATE;
    public final Item RETURN_ITEM;

    public SFBlockCreator(TechMaterial material, Item returnItem) {
        super(Properties.of(Material.HEAVY_METAL).strength(2, 120).harvestTool(ToolType.PICKAXE));
        CAN_CREATE = material.getItem();//��ȡ������Ʒ��Ҫ�Ĳ���
        RETURN_ITEM = returnItem;//��ȡ��������
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
        //��ȡ����ʵ��λ��
        SFBlockCreatorBE SFBlockCrer = (SFBlockCreatorBE) worldIn.getBlockEntity(pos);
        assert SFBlockCrer != null;
        System.out.println(SFBlockCrer.count);
        //�����µĵ������Թ�ʹ��
        ItemEntity drop = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RETURN_ITEM));
        if (SFBlockCrer.count >= 16) {
            SFBlockCrer.clean();//����ﵽ16��������count
            worldIn.addFreshEntity(drop);//�����µĵ�����
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
                worldIn.addFreshEntity(drop);//�����µĵ�����
                SFBlockCrer.clean();//���÷���ʵ����ֵ
                System.out.println("clean. " + SFBlockCrer.count);
            }
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.add", input), false);
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.now_count", count), false);
            return ActionResultType.SUCCESS;
        } else {
            int count = SFBlockCrer.increaseCount(1);
            player.getMainHandItem().shrink(1);//��������е���Ʒ����һ��
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.add", 1), false);
            player.displayClientMessage(new TranslationTextComponent("mec.SFBlockCrer.now_count", count), false);
            return ActionResultType.SUCCESS;
        }
    }

//    @Override
//    @OnlyIn(Dist.CLIENT)//��д�������ڱ�
//    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
//        return 1;
//    }
}
