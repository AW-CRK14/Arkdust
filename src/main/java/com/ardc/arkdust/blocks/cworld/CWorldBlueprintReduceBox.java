package com.ardc.arkdust.blocks.cworld;

import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import com.ardc.arkdust.blockstate.DropSelfBlock;
import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.BlockStateHelper;
import com.ardc.arkdust.helper.EnumHelper;
import com.ardc.arkdust.helper.ISkipShiftBlock;
import com.ardc.arkdust.playmethod.blueprint.BlueprintItem;
import com.ardc.arkdust.playmethod.blueprint.GeneralBlueprintItem;
import com.ardc.arkdust.registry.item.Item$BlueprintRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import com.ardc.arkdust.registry.item.Item$CommonWorld;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CWorldBlueprintReduceBox extends DropSelfBlock implements SimpleWaterloggedBlock, ISkipShiftBlock, EntityBlock {
    private static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    private static final BooleanProperty TOP = BooleanProperty.create("top");
    private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CWorldBlueprintReduceBox() {
        super(Properties.copy(Blocks.IRON_BLOCK)
                        .strength(3,70)
                        .noOcclusion(),
                (s,c)->{
                    ItemStack stack = new ItemStack(Item$CommonWorld.basic_blueprint_reduce_box::get);
                    BlueprintReduceBoxBE be = (BlueprintReduceBoxBE) c.getParamOrNull(LootContextParams.BLOCK_ENTITY);
                    if(be != null) {
                        CompoundTag nbt = new CompoundTag();
                        be.saveAdditional(nbt);
                        stack.getOrCreateTag().put("BlockEntityTag",nbt);
                    }
                    return Collections.singletonList(stack);
                }
        );
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED,false).setValue(TOP,false));
    }


    public final int maxCount = 32;


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlueprintReduceBoxBE entity = (BlueprintReduceBoxBE)worldIn.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);
        if(stack.getItem() instanceof BlueprintItem){
            if(!stack.getOrCreateTagElement("blueprint").getString("type").equals(BlueprintTypeEnum.C7_TELEPORT.name())){
                if(worldIn.isClientSide) player.displayClientMessage(Component.translatable("mec.bluePrintReduceBox.only_c7"),false);
                change(state,worldIn,pos,entity);
                return InteractionResult.SUCCESS;
            }
            int num = entity.addBlueprint(stack, Screen.hasShiftDown());
            ItemStack itemStack = ((GeneralBlueprintItem) Item$BlueprintRegistry.general_blueprint.get()).addDefaultInfo(entity.getObjType(),entity.getValue());
            itemStack.setCount(num);
            if(!player.addItem(itemStack))
                worldIn.addFreshEntity(new ItemEntity(worldIn,pos.getX()+0.5F,pos.getY()+1,pos.getZ()+0.5F,itemStack));
            change(state,worldIn,pos,entity);
            return InteractionResult.SUCCESS;
        }
        if(worldIn.isClientSide) {
            player.displayClientMessage(Component.translatable("mec.bluePrintReduceBox.count", entity.getCount(), entity.getMaxContain()), false);
            player.displayClientMessage(Component.translatable("mec.bluePrintReduceBox.type").append(entity.getObjType().name()), false);
            player.displayClientMessage(Component.translatable("mec.bluePrintReduceBox.value").append(entity.getValue().name()), false);
        }
        change(state,worldIn,pos,entity);
        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, BlockGetter iBlockReader, List<Component> textComponentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, iBlockReader, textComponentList, tooltipFlag);
        CompoundTag nbt = itemStack.getTagElement("BlockEntityTag");
        if (nbt != null) {
            textComponentList.add(Component.translatable("pma.bp.type").append(EnumHelper.valueOfOrDefault(BlueprintTypeEnum.class,nbt.getString("type"),BlueprintTypeEnum.NULL).toString()));
            textComponentList.add(Component.translatable("pma.bp.value").append(EnumHelper.valueOfOrDefault(BlueprintValueEnum.class,nbt.getString("value"),BlueprintValueEnum.NULL).toString()));
            textComponentList.add(Component.translatable("pma.bp.count").append(nbt.getInt("count") + "/" + maxCount));
        }
    }

//    public ItemStack getCloneItemStack(IBlockReader reader, BlockPos pos, BlockState state) {
//        ItemStack itemstack = super.getCloneItemStack(reader, pos, state);
//        itemstack.addTagElement("BlockEntityTag", ((BlueprintReduceBoxBE) reader.getBlockEntity(pos)).save(new CompoundNBT()));
//        return itemstack;
//    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack itemStack) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof BlueprintReduceBoxBE){
            entity.load(itemStack.getOrCreateTagElement("BlockEntityTag"));
        }
    }

    private void change(BlockState state, Level world, BlockPos pos, BlueprintReduceBoxBE entity){
        if(state.getValue(TOP) != entity.test()){
            world.setBlock(pos,state.setValue(TOP,entity.test()),3);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(WATERLOGGED, HORIZONTAL_FACING,TOP);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
        return Shapes.box(0.0625F,0,0.0625F,0.9375F,0.75F,0.9375F);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context){
        return BlockStateHelper.waterloggedRotateBlock(super.getStateForPlacement(context),context);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos){
        if(state.getValue(WATERLOGGED)){
            world.scheduleTick(pos,Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlueprintReduceBoxBE(TileEntityTypeRegistry.BASIC_BLUEPRINT_REDUCE_BOX_BE.get(),pos,state,maxCount);
    }
}
