package com.ardc.arkdust.Items.blocks;

import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.BlockStateHelper;
import com.ardc.arkdust.helper.EnumHelper;
import com.ardc.arkdust.helper.ISkipShiftBlock;
import com.ardc.arkdust.playmethod.blueprint.BlueprintItem;
import com.ardc.arkdust.playmethod.blueprint.GeneralBlueprintItem;
import com.ardc.arkdust.preobject.BlockState.DropSelfBlock;
import com.ardc.arkdust.registry.Item$BlueprintRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CWorldBlueprintReduceBox extends DropSelfBlock implements IWaterLoggable, ISkipShiftBlock {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    private static final BooleanProperty TOP = BooleanProperty.create("top");
    private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CWorldBlueprintReduceBox() {
        super(Properties.of(Material.METAL)
                        .strength(3,70)
                        .harvestTool(ToolType.AXE)
                        .noOcclusion(),
                (s,c)->{
                    ItemStack stack = new ItemStack(ItemRegistry.basic_blueprint_reduce_box::get);
                    BlueprintReduceBoxBE be = (BlueprintReduceBoxBE) c.getParamOrNull(LootParameters.BLOCK_ENTITY);
                    if(be != null) {
                        stack.getOrCreateTag().put("BlockEntityTag",be.save(new CompoundNBT()));
                    }
                    return Collections.singletonList(stack);
                }
        );
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(TOP,false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public final int maxCount = 32;

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BlueprintReduceBoxBE(TileEntityTypeRegistry.BASIC_BLUEPRINT_REDUCE_BOX_BE.get(),maxCount);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        BlueprintReduceBoxBE entity = (BlueprintReduceBoxBE)worldIn.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);
        if(stack.getItem() instanceof BlueprintItem){
            if(!stack.getOrCreateTagElement("blueprint").getString("type").equals(BlueprintTypeEnum.C7_TELEPORT.name())){
                if(worldIn.isClientSide) player.displayClientMessage(new TranslationTextComponent("mec.bluePrintReduceBox.only_c7"),false);
                change(state,worldIn,pos,entity);
                return ActionResultType.SUCCESS;
            }
            int num = entity.addBlueprint(stack, Screen.hasShiftDown());
            ItemStack itemStack = ((GeneralBlueprintItem) Item$BlueprintRegistry.general_blueprint.get()).addDefaultInfo(entity.getObjType(),entity.getValue());
            itemStack.setCount(num);
            if(!player.addItem(itemStack))
                worldIn.addFreshEntity(new ItemEntity(worldIn,pos.getX()+0.5F,pos.getY()+1,pos.getZ()+0.5F,itemStack));
            change(state,worldIn,pos,entity);
            return ActionResultType.SUCCESS;
        }
        if(worldIn.isClientSide) {
            player.displayClientMessage(new TranslationTextComponent("mec.bluePrintReduceBox.count", entity.getCount(), entity.getMaxContain()), false);
            player.displayClientMessage(new TranslationTextComponent("mec.bluePrintReduceBox.type").append(entity.getObjType().name()), false);
            player.displayClientMessage(new TranslationTextComponent("mec.bluePrintReduceBox.value").append(entity.getValue().name()), false);
        }
        change(state,worldIn,pos,entity);
        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader iBlockReader, List<ITextComponent> textComponentList, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, iBlockReader, textComponentList, tooltipFlag);
        CompoundNBT nbt = itemStack.getTagElement("BlockEntityTag");
        if (nbt != null) {
            textComponentList.add(new TranslationTextComponent("pma.bp.type").append(EnumHelper.valueOfOrDefault(BlueprintTypeEnum.class,nbt.getString("type"),BlueprintTypeEnum.NULL).toString()));
            textComponentList.add(new TranslationTextComponent("pma.bp.value").append(EnumHelper.valueOfOrDefault(BlueprintValueEnum.class,nbt.getString("value"),BlueprintValueEnum.NULL).toString()));
            textComponentList.add(new TranslationTextComponent("pma.bp.count").append(nbt.getInt("count") + "/" + maxCount));
        }
    }

//    public ItemStack getCloneItemStack(IBlockReader reader, BlockPos pos, BlockState state) {
//        ItemStack itemstack = super.getCloneItemStack(reader, pos, state);
//        itemstack.addTagElement("BlockEntityTag", ((BlueprintReduceBoxBE) reader.getBlockEntity(pos)).save(new CompoundNBT()));
//        return itemstack;
//    }

    public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity entity = world.getBlockEntity(pos);
        if(entity instanceof BlueprintReduceBoxBE){
            entity.load(state,itemStack.getOrCreateTagElement("BlockEntityTag"));
        }
    }

    private void change(BlockState state, World world, BlockPos pos, BlueprintReduceBoxBE entity){
        if(state.getValue(TOP) != entity.test()){
            world.setBlock(pos,state.setValue(TOP,entity.test()),3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED, HORIZONTAL_FACING,TOP);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return VoxelShapes.box(0.0625F,0,0.0625F,0.9375F,0.75F,0.9375F);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context){
        return BlockStateHelper.waterloggedRotateBlock(this,context);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos){
        if(state.getValue(WATERLOGGED)){
            world.getLiquidTicks().scheduleTick(pos,Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
