package com.ardc.arkdust.playmethod.story.blockanditem;

import com.ardc.arkdust.capability.story.StorySaveCapability;
import com.ardc.arkdust.preobject.PreBlock;
import com.ardc.arkdust.registry.CapabilityRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StoryPointBlock extends PreBlock implements EntityBlock {
    private static final Logger LOGGER = LogManager.getLogger();

    public StoryPointBlock() {
        super(Properties.copy(Blocks.OBSIDIAN).strength(-1,3600).noOcclusion());
    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player playerEntity, InteractionHand hand, BlockHitResult ray) {
        StoryPointBE blockEntity = (StoryPointBE)world.getBlockEntity(pos);
        if(blockEntity != null){
//            System.out.println("StoryPointTest:" + ((StoryPointBE) blockEntity).dataToNBT().toString());
            if(!world.isClientSide()){
                if(blockEntity.checkBagMode()) {
                    LazyOptional<StorySaveCapability> cap = playerEntity.getCapability(CapabilityRegistry.STORY_CAPABILITY);
                    cap.ifPresent((i) -> {
                        if(i.contains(blockEntity.bag, blockEntity.stage) || i.add(blockEntity.bag,blockEntity.stage,playerEntity)) {
                            playerEntity.displayClientMessage(blockEntity.getTitleContext(blockEntity.stage), false);
                            playerEntity.displayClientMessage(blockEntity.getContext(), false);
                        }else {
                            playerEntity.displayClientMessage(Component.translatable("story.info.locked"), false);
                        }
                    });
                }else {
                    playerEntity.displayClientMessage(blockEntity.getTitleContext(blockEntity.stage), false);
                    playerEntity.displayClientMessage(blockEntity.getContext(), false);
                }
            }
            return InteractionResult.SUCCESS;
        }
        LOGGER.error("[ArdInGame-StoryPoint]Can't get StoryPointBlock block entity at {} , in world {}.",pos,world);
        return InteractionResult.FAIL;
    }

    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        BlockEntity preBe = world.getBlockEntity(pos);
        if (preBe instanceof StoryPointBE) {
            StoryPointBE be = (StoryPointBE)preBe;
            if (!world.isClientSide) {
                ItemStack itemstack = new ItemStack(ItemRegistry.story_point.get());
                //由方块实体获取存储在物品里的nbt，并判断非空时将它加入物品
                CompoundTag nbt = new CompoundTag();
                be.saveAdditional(nbt);
                itemstack.addTagElement("BlockEntityTag", nbt);

                ItemEntity itementity = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                world.addFreshEntity(itementity);
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, BlockGetter iBlockReader, List<Component> textComponentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, iBlockReader, textComponentList, tooltipFlag);
        CompoundTag nbt = itemStack.getTagElement("BlockEntityTag");
        if (nbt != null) {
            textComponentList.add(Component.translatable("story.info.wrote"));
            textComponentList.add(Component.translatable("story.info.sm").append(String.valueOf(nbt.getBoolean("bagMode"))));
            textComponentList.add(Component.translatable("story.info.title").append(nbt.getString("title")));
            textComponentList.add(Component.translatable("story.info.trans_key").append(nbt.getString("transKey")));
            textComponentList.add(Component.translatable("story.info.bag").append(nbt.getString("bag")));
            textComponentList.add(Component.translatable("story.info.level").append(String.valueOf(nbt.getInt("level"))));
        }
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack itemStack) {
        BlockEntity entity = world.getBlockEntity(pos);
        if(entity instanceof StoryPointBE){
            ((StoryPointBE) entity).copyFromItemStack(itemStack);
        }
    }

    public ItemStack getCloneItemStack(BlockGetter reader, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getCloneItemStack(reader, pos, state);
        BlockEntity entity = reader.getBlockEntity(pos);
        if(entity instanceof StoryPointBE){
            CompoundTag nbt = new CompoundTag();
            ((StoryPointBE) entity).saveAdditional(nbt);
            itemstack.addTagElement("BlockEntityTag", nbt);
        }
        return itemstack;
    }


    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StoryPointBE(pos,state);
    }
}
