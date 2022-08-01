package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.BlockEntity.HandScreenedTableBE;
import com.ardc.arkdust.CodeMigration.BlockState.RotateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class Hand_screened_table extends RotateBlock {
    public static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");

    public Hand_screened_table() {
        super(Properties.of(Material.HEAVY_METAL).strength(2, 120).harvestTool(ToolType.PICKAXE).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(HAS_ITEM,false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HandScreenedTableBE();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        HandScreenedTableBE blockBE = new HandScreenedTableBE();
//        if(blockBE.hasItem){
//            state.setValue(HAS_ITEM, false);
//            int num = blockBE.getLootNum();
//            ItemEntity drop = new ItemEntity(worldIn,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(blockBE.,num));
//            drop.
//        }
        return ActionResultType.PASS;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

}
