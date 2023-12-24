package com.ardc.arkdust.worldgen.feature.pre;

import com.ardc.arkdust.worldgen.config.ListNBTFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class ListNBTGenerateFeature extends Feature<ListNBTFeatureConfig> {
    public ListNBTGenerateFeature(Codec<ListNBTFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ListNBTFeatureConfig> context) {
        BlockPos pos = context.origin();
            StructureTemplate template = context.level().getLevel().getStructureManager().getOrCreate(context.config().getRandomResource(context.random()));
            StructurePlaceSettings settings = new StructurePlaceSettings().setRotation(Rotation.getRandom(context.random())).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).addProcessor(new BlockRotProcessor(Mth.clamp(context.config().complete, 0.0F, 1.0F))).setRandom(context.random());
            if (context.config().moveToCenter) {
                settings.setRotationPivot(new BlockPos(template.getSize()).offset(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2));
                pos = pos.offset(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2);
            }
            return template.placeInWorld(context.level(),pos,pos,settings,context.random(),2);
    }
}
