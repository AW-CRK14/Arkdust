package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.block_entity.SeRcuMachineBE;
import com.ardc.arkdust.preobject.PreBlock;
import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Properties;

public class SeRcuMachine extends PreBlock {
    public SeRcuMachine() {
        super(Properties.of(Material.METAL).strength(4,10).noOcclusion().harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SeRcuMachineBE();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isClientSide && hand.equals(Hand.MAIN_HAND)){
            NetworkHooks.openGui((ServerPlayerEntity) player, (SeRcuMachineBE)world.getBlockEntity(pos),(buffer)->buffer.writeBlockPos(pos));
        }
        return ActionResultType.SUCCESS;
    }
}
