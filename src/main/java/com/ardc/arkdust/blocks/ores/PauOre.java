package com.ardc.arkdust.blocks.ores;


import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ToolType;


public class PauOre extends Block {
    public PauOre(){
        super(Properties.of().requiredFeatures(ToolType.PICKAXE).strength(8).harvestLevel(2).requiresCorrectToolForDrops());
        //strength是强度，等价于非官方版中的hardnessAndResistance
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        if(worldIn.isClientSide && !player.isCreative()){
            player.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)-> Utils.LOGGER.info(i.toString()));
        }
        return ActionResultType.SUCCESS;
    }

}
