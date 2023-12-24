package com.ardc.arkdust.worldgen.structure.preobj;

import com.ardc.arkdust.blockstate.RotateBlock;
import com.ardc.arkdust.helper.DirectionAndRotationHelper;
import com.ardc.arkdust.helper.StructureHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.worldgen.structure.ExtraStructurePieceType;
import com.ardc.arkdust.worldgen.structure.processor.ExtraStructureProcessorType;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public abstract class CommonCWTemplatePiece extends TemplateStructurePiece {
    public final Rotation rotation;
    public final BlockPos rotatePivot;

    public CommonCWTemplatePiece(StructurePieceType type, Structure.GenerationContext context, BlockPos pos, List<ResourceLocation> resourceLocation) {
        this(type,context,pos,resourceLocation,false,0);
    }
    public CommonCWTemplatePiece(StructurePieceType type, Structure.GenerationContext context, BlockPos pos, ResourceLocation resourceLocation) {
        this(type,context,pos,resourceLocation,false,0);
    }
    public CommonCWTemplatePiece(StructurePieceType type, Structure.GenerationContext context, BlockPos pos, List<ResourceLocation> resourceLocation,boolean yGenReset,int extraYMove) {
        this(type,context,pos,resourceLocation.get(context.random().nextInt(resourceLocation.size())),yGenReset,extraYMove);
    }
    public CommonCWTemplatePiece(StructurePieceType type, Structure.GenerationContext context, BlockPos pos, ResourceLocation resourceLocation,boolean yGenReset,int extraYMove) {
        this(type, context,pos, resourceLocation,getBasicPlacementSetting(Rotation.getRandom(context.random()),rotationPivot(context.structureTemplateManager().getOrCreate(resourceLocation))),yGenReset,extraYMove);
    }
    public CommonCWTemplatePiece(StructurePieceType type, Structure.GenerationContext context, BlockPos pos, ResourceLocation resourceLocation, StructurePlaceSettings settings, boolean yGenReset, int extraYMove) {
        super(type, 5, context.structureTemplateManager(), resourceLocation, resourceLocation.toString(), settings, pos);
        this.rotation = getRotation();
        this.rotatePivot = rotationPivot(this.template);

        if(yGenReset) {//在生成时自动移动y使其贴合地表。这一过程将使原y值被忽略
            AtomicInteger yMin= new AtomicInteger(256);
            StructureHelper.forEachVer(boundingBox,(pos1 -> yMin.set(Math.min(context.chunkGenerator().getFirstFreeHeight(pos1.getX(), pos1.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState()), yMin.get()))));

            this.templatePosition = pos.atY(yMin.get() + extraYMove);
            boundingBox.move(0, yMin.get() + extraYMove,0);
        }
    }

    //nbt加载默认模式
    public CommonCWTemplatePiece(StructurePieceType type,StructureTemplateManager manager, CompoundTag nbt) {
        this(type,manager,nbt, (r)-> getBasicPlacementSetting(Rotation.valueOf(nbt.getString("rot")),BlockPos.of(nbt.getLong("pivot"))));
    }
    //nbt加载自定义模式
    public CommonCWTemplatePiece(StructurePieceType type,StructureTemplateManager manager, CompoundTag nbt,Function<ResourceLocation, StructurePlaceSettings> func) {
        super(type, nbt, manager, func);
        this.rotation = Rotation.valueOf(nbt.getString("rot"));
        this.rotatePivot = BlockPos.of(nbt.getLong("pivot"));
    }

    public static StructurePlaceSettings getBasicPlacementSetting(Rotation rotation, BlockPos pivot){
        return (new StructurePlaceSettings()).setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR).setRotationPivot(pivot).addProcessor(ExtraStructureProcessorType.IGNORE_STRI);
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

//    @Override
//    public void postProcess(WorldGenLevel genLevel, StructureManager manager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
//        if(genYAutoMove) {//在生成时自动移动y使其贴合地表。这一过程将使原y值被忽略
//            AtomicInteger yMin= new AtomicInteger(512);
//            StructureHelper.forEachVer(boundingBox,(pos1 -> chunkGenerator.getFirstOccupiedHeight(pos1.getX(),pos1.getZ(), Heightmap.Types.WORLD_SURFACE_WG,genLevel,)));
//            int dertaY = yMin.get() - pos.getY() + genYExtraMove;
//
//            this.templatePosition = templatePosition().atY(yMin.get());
////            pos = templatePosition;
//            boundingBox.move(0,dertaY,0);
//        }
//        super.postProcess(genLevel, manager, chunkGenerator, random, boundingBox, chunkPos, pos);
//    }

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
