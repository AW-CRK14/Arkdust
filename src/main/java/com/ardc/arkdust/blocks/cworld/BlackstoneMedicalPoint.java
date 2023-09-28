package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.block_entity.BlackstoneMedicalPointBE;
import com.ardc.arkdust.blockstate.DropSelfBlock;
import com.ardc.arkdust.registry.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlackstoneMedicalPoint extends DropSelfBlock implements EntityBlock {
    public static final BooleanProperty WORKING = BooleanProperty.create("working");
    public BlackstoneMedicalPoint() {
        super(Properties.copy(Blocks.BLACKSTONE).strength(2, 6).noOcclusion().lightLevel((s)->s.getValue(WORKING) ? 8 : 2).requiresCorrectToolForDrops());
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource randomSource) {
        ((BlackstoneMedicalPointBE)world.getBlockEntity(pos)).tick();
        world.scheduleTick(pos,this,40);
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (worldIn.getBlockState(pos.below()).getBlock().equals(BlockRegistry.pau_block.get())) {
            if(!worldIn.isClientSide()) player.displayClientMessage(Component.translatable("mes.bs_medical_point.activation").withStyle(ChatFormatting.DARK_GREEN), false);
            worldIn.setBlock(pos, state.setValue(WORKING, true), 1);
            worldIn.scheduleTick(pos,this,40);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(WORKING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BlackstoneMedicalPointBE(p_153215_,p_153216_);
    }
}




