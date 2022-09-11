package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.BlockEntity.ScreenedTableBE;
import com.ardc.arkdust.CodeMigration.pre.PreBlock;
import com.ardc.arkdust.BlockEntity.Process.OreScreened_Use;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
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

public class HangScreenedTable extends PreBlock {
    public static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");

    public HangScreenedTable() {
        super(Properties.of(Material.WOOD).strength(2, 120).harvestTool(ToolType.PICKAXE).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(HAS_ITEM,false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block,BlockState> builder){
        builder.add(HAS_ITEM);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ScreenedTableBE();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        return OreScreened_Use.oreScreened_Use(state,worldIn,pos,player,HAS_ITEM,1);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    public void onRemove(BlockState block, World world, BlockPos pos, BlockState toBlock, boolean iif) {
        ScreenedTableBE blockBE = (ScreenedTableBE) world.getBlockEntity(pos);
        if (block.hasTileEntity() && (!block.is(toBlock.getBlock()) || !toBlock.hasTileEntity())/*如果新的方块不是这个方块或者新的方块不具有实体*/) {
            world.removeBlockEntity(pos);
            ItemEntity drop = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), blockBE.getLootItem());
            world.addFreshEntity(drop);
        }
//        System.out.printf("%nblock on remove:%n    blockstate1:" + block + "%n    blockstate2:" + toBlock);
    }

}
