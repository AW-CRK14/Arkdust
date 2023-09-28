package com.ardc.arkdust.worldgen.structure.processor.structure_processor;

import com.ardc.arkdust.helper.BlockHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.helper.TagHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.resource.Tag;
import com.ardc.arkdust.worldgen.structure.processor.ExtraStructureProcessorType;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

public class WastelandMineshaftProcessor extends StructureProcessor {
    public static final WastelandMineshaftProcessor INSTANCE = new WastelandMineshaftProcessor();
    public static final Codec<WastelandMineshaftProcessor> CODEC = Codec.unit(() -> INSTANCE);

    private WastelandMineshaftProcessor() {
    }

//    public Template.BlockInfo process(IWorldReader reader, BlockPos lessAdvancedPiecePoint, BlockPos startCenterPos, Template.BlockInfo relativePositionInfo, Template.BlockInfo absolutePositionInfo, PlacementSettings settings, @Nullable Template template) {
    public StructureTemplate.StructureBlockInfo process(LevelReader reader, BlockPos lessAdvancedPiecePoint, BlockPos startCenterPos, StructureTemplate.StructureBlockInfo relativePositionInfo, StructureTemplate.StructureBlockInfo absolutePositionInfo, StructurePlaceSettings settings, StructureTemplate template) {
        Random r = new Random();
        Block block = absolutePositionInfo.state().getBlock();
        if (Blocks.STONE_BRICKS.equals(block)) {
            return r.nextFloat() <= 0.2F ? StructureHelper.setTemplateBlock(absolutePositionInfo, TagHelper.getRandomBlockElement(Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_GROUND,r).defaultBlockState()) : absolutePositionInfo;
        } else if (Blocks.GLOWSTONE.equals(block)) {
            return r.nextFloat() <= 0.4F ? StructureHelper.setTemplateBlock(absolutePositionInfo, BlockHelper.getRandomBlock(r, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE, Blocks.ANDESITE, Blocks.COBBLESTONE, Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE).defaultBlockState()) : absolutePositionInfo;
        } else if (Blocks.LIGHT_GRAY_CONCRETE.equals(block)) {
            return r.nextFloat() <= 0.4F ? StructureHelper.setTemplateBlock(absolutePositionInfo, BlockHelper.getRandomBlock(r, Blocks.ANDESITE, Blocks.COBBLESTONE, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.GRAY_CONCRETE, Blocks.AIR).defaultBlockState()) : absolutePositionInfo;
        } else if (BlockRegistry.dirty_concrete.get().equals(block)) {
            return r.nextFloat() <= 0.4F ? StructureHelper.setTemplateBlock(absolutePositionInfo, TagHelper.getRandomBlockElement(Tag.Blocks.STRUCTURE$WASTELAND_MINESHAFT_DIRTY_CONCRETE,r).defaultBlockState()) : absolutePositionInfo;
        } else if (BlockRegistry.red_paint_rushed_iron_block.get().equals(block)) {
            return r.nextFloat() <= 0.5F ? StructureHelper.setTemplateBlock(absolutePositionInfo, BlockRegistry.dark_rushed_iron_block.get().defaultBlockState()) : absolutePositionInfo;
        } else if (Blocks.BLACKSTONE.equals(block)) {
            return r.nextFloat() <= 0.3F ? StructureHelper.setTemplateBlock(absolutePositionInfo, BlockRegistry.c_originium_block.get().defaultBlockState()) : absolutePositionInfo;
        }

        return absolutePositionInfo;
    }

    protected StructureProcessorType<?> getType() {
        return ExtraStructureProcessorType.WASTELAND_MINESHAFT_PROCESSOR;
    }
}
