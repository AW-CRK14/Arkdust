package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.BlockEntity.SFBlockCreatorBE;
import com.ardc.arkdust.CodeMigration.BlockState.RotateBlock;
import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.BlockEntity.Process.SFBlockCreator_Use;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
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

public class Iron_sfblock_creator extends RotateBlock {

    public Iron_sfblock_creator() {
        super(Properties.of(Material.HEAVY_METAL).strength(2, 120).harvestTool(ToolType.PICKAXE).noOcclusion());
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
        return SFBlockCreator_Use.runSFBlockCreator_Use(ItemRegistry.iron_structure_frame.get(), Items.IRON_INGOT, worldIn, pos, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

}
