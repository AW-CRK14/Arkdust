package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.CodeMigration.Material;
import com.ardc.arkdust.NewPlayingMethod.OriInfection.OIMain;
import com.ardc.arkdust.NewPlayingMethod.OriInfection.WorldOIData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Test_block_b extends Block{
    public Test_block_b(){
        super(Properties.of(Material.TEST1).strength(2));
        //strength是强度，等价于非官方版中的hardnessAndResistance

    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        new OIMain.WorldOI().tryAddWorldOIPoint(worldIn,5);
        return ActionResultType.SUCCESS;
    }
}
