package com.ardc.arkdust.worldgen.structure.preobj;

import com.ardc.arkdust.helper.DirectionAndRotationHelper;
import com.ardc.arkdust.preobject.BlockState.RotateBlock;
import com.ardc.arkdust.worldgen.structure.ExtraStructureProcessorList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.fml.RegistryObject;

import java.util.Random;

public abstract class CommonCWTemplatePiece extends TemplateStructurePiece {
    public final ResourceLocation templateLocation;
    public final Rotation rotation;

    public CommonCWTemplatePiece(IStructurePieceType iStructurePieceType,TemplateManager templateManager, ResourceLocation structurePlace, BlockPos addPos, Rotation aRotation, Random random) {
        super(iStructurePieceType, 3);
        this.templateLocation = structurePlace;
        this.templatePosition = addPos;
        this.rotation = aRotation;
        this.loadTemplate(templateManager);
    }

    protected void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("Template", this.templateLocation.toString());
        nbt.putString("Rot", this.rotation.name());
    }

    public CommonCWTemplatePiece( IStructurePieceType iStructurePieceType,TemplateManager templateManager, CompoundNBT nbt) {
        super(iStructurePieceType, nbt);
        this.templateLocation = new ResourceLocation(nbt.getString("Template"));
        this.rotation = Rotation.valueOf(nbt.getString("Rot"));
        this.loadTemplate(templateManager);
    }

    public void loadTemplate(TemplateManager templateManager) {
        Template template = templateManager.getOrCreate(this.templateLocation);
        PlacementSettings placementsettings = getPlacementSetting();
        this.setup(template, this.templatePosition, placementsettings);
    }

    public PlacementSettings getPlacementSetting(){
        return (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setRotationPivot(new BlockPos(16,0,16)).addProcessor(ExtraStructureProcessorList.STRUCTURE_BLOCK_AND_ARD_IGNBLOCK.get());
    }

    //用于获取随机旋转的某个方块的状态
    public static BlockState randomRotationBlockState(RegistryObject<Block> block, Random random){
        BlockState state = block.get().defaultBlockState();
        if(state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)){
            Direction direction = DirectionAndRotationHelper.RandomDirection(DirectionAndRotationHelper.direcList.HORIZON_DIRECTION,random);
            state = state.setValue(block.get() instanceof RotateBlock ? RotateBlock.HORIZONTAL_FACING : BlockStateProperties.HORIZONTAL_FACING,direction);
        }
        return state;
    }

}
