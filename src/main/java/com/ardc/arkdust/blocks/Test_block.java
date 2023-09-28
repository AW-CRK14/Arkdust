package com.ardc.arkdust.blocks;

import com.ardc.arkdust.preobject.PreBlock;
import com.ardc.arkdust.resource.LootTable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class Test_block extends PreBlock {
    public Test_block(){
        super(Properties.copy(Blocks.COMMAND_BLOCK));
        //strength是强度，等价于非官方版中的hardnessAndResistance
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if(!worldIn.isClientSide()) {
            worldIn.setBlock(pos.above(),Blocks.CHEST.defaultBlockState(),3);
            BlockEntity tileEntity = worldIn.getBlockEntity(pos.above());
            if(tileEntity instanceof RandomizableContainerBlockEntity){
                ((RandomizableContainerBlockEntity) tileEntity).setLootTable(LootTable.GENERAL_SUPPLY_BOX_A,new Random().nextLong());
            }
        }
        return InteractionResult.SUCCESS;
    }

}
