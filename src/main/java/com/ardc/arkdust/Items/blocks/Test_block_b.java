package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.CodeMigration.Material;
import com.ardc.arkdust.CodeMigration.RunHelper.PrintHelper;
import com.ardc.arkdust.playmethod.OriInfection.OIMain;
import com.ardc.arkdust.playmethod.story.IStorySaveCapability;
import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBE;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class Test_block_b extends Block{
    public Test_block_b(){
        super(Properties.of(Material.TEST1).strength(2));
        //strength是强度，等价于非官方版中的hardnessAndResistance

    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide()) {
                LazyOptional<IStorySaveCapability> cap = player.getCapability(CapabilityRegistry.STORY_CAPABILITY);
                cap.ifPresent((i) -> System.out.println(i.createListInfo()));
        }
        return ActionResultType.SUCCESS;
    }
}
