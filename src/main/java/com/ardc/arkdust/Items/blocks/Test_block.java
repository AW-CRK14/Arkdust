package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.CodeMigration.Material;
import com.ardc.arkdust.CodeMigration.resourcelocation.LootTable;
import com.ardc.arkdust.preobject.pre.PreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class Test_block extends PreBlock {
    public Test_block(){
        super(Properties.of(Material.TEST1).strength(2));
        //strength是强度，等价于非官方版中的hardnessAndResistance
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        if(!worldIn.isClientSide() && worldIn.getBlockState(pos.above()).getBlock().equals(Blocks.AIR)) {
            worldIn.setBlock(pos.above(),Blocks.CHEST.defaultBlockState(),3);
            TileEntity tileEntity = worldIn.getBlockEntity(pos.above());
            if(tileEntity instanceof ChestTileEntity){
                Random r = new Random();
                ((ChestTileEntity) tileEntity).setLootTable(LootTable.CW_BLUEPRINT_BOX,r.nextLong());
            }
        }
        return ActionResultType.SUCCESS;
    }

}
