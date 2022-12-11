package com.ardc.arkdust.Items.blocks.ores;


import com.ardc.arkdust.playmethod.OriInfection.OIMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;


public class Pau_ore extends Block{
    public Pau_ore(){
        super(Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).strength(8).harvestLevel(2).requiresCorrectToolForDrops());
        //strength是强度，等价于非官方版中的hardnessAndResistance
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        OIMain.seedOIData(player);
        return ActionResultType.SUCCESS;
    }

}
