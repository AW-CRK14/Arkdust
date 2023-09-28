package com.ardc.arkdust.worldgen.structure.preobj;

import com.ardc.arkdust.blockstate.RotateBlock;
import com.ardc.arkdust.helper.DirectionAndRotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

public abstract class CommonCWTemplatePiece extends TemplateStructurePiece {
    public final Rotation rotation;
    public final BlockPos rotatePivot;


    public CommonCWTemplatePiece(StructurePieceType type, StructureTemplateManager templateManager, BlockPos pos, ResourceLocation resourceLocation,StructurePlaceSettings settings) {
        super(type, 5, templateManager, resourceLocation, resourceLocation.toString(),settings, pos);
        this.rotation = getRotation();
        this.rotatePivot = rotationPivot(this.template);
    }

    public CommonCWTemplatePiece(StructurePieceType type, StructureTemplateManager templateManager, RandomSource random, BlockPos pos, ResourceLocation resourceLocation) {
        this(type, templateManager,pos, resourceLocation,getBasicPlacementSetting(Rotation.getRandom(random),rotationPivot(templateManager.getOrCreate(resourceLocation))));

    }

    public CommonCWTemplatePiece(StructurePieceType type,StructureTemplateManager manager, CompoundTag nbt) {
        super(type, nbt, manager, (r)-> getBasicPlacementSetting(Rotation.valueOf(nbt.getString("rot")),BlockPos.of(nbt.getLong("pivot"))));
        this.rotation = Rotation.valueOf(nbt.getString("rot"));
        this.rotatePivot = BlockPos.of(nbt.getLong("pivot"));
    }

    public static StructurePlaceSettings getBasicPlacementSetting(Rotation rotation, BlockPos pivot){
        return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR).setRotationPivot(pivot);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        tag.putString("rot", String.valueOf(this.placeSettings.getRotation()));
        tag.putLong("pivot", rotatePivot.asLong());
        super.addAdditionalSaveData(context, tag);
    }
    protected static BlockPos rotationPivot(StructureTemplate template){
        return new BlockPos(template.getSize().getX()/2,0,template.getSize().getZ()/2);
    }

    //用于获取随机旋转的某个方块的状态

    public static BlockState randomRotationBlockState(RegistryObject<Block> block, RandomSource random){
        BlockState state = block.get().defaultBlockState();
        if(state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
            Direction direction = DirectionAndRotationHelper.RandomDirection(DirectionAndRotationHelper.direcList.HORIZON_DIRECTION,random);
            state = state.setValue(block.get() instanceof RotateBlock ? RotateBlock.HORIZONTAL_FACING : BlockStateProperties.HORIZONTAL_FACING,direction);
        }
        return state;
    }

//    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(Utils.MOD_ID,"cworld/undertree_blueprint/undertree_blueprint_1");

    @Override
    protected void handleDataMarker(String meta, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource randomSource, BoundingBox boundingBox) {

    }

}
