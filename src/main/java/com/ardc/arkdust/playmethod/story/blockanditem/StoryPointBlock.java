package com.ardc.arkdust.playmethod.story.blockanditem;

import com.ardc.arkdust.capability.story.IStorySaveCapability;
import com.ardc.arkdust.preobject.PreBlock;
import com.ardc.arkdust.registry.CapabilityRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class StoryPointBlock extends PreBlock {
    private static final Logger LOGGER = LogManager.getLogger();

    public StoryPointBlock() {
        super(Properties.of(Material.HEAVY_METAL).strength(-1,3600000).noOcclusion());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new StoryPointBE();
    }


    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult ray) {
        StoryPointBE blockEntity = (StoryPointBE)world.getBlockEntity(pos);
        if(blockEntity != null){
//            System.out.println("StoryPointTest:" + ((StoryPointBE) blockEntity).dataToNBT().toString());
            if(!world.isClientSide()){
                if(blockEntity.bagMode) {
                    LazyOptional<IStorySaveCapability> cap = playerEntity.getCapability(CapabilityRegistry.STORY_CAPABILITY);
                    cap.ifPresent((i) -> {
                        if(i.contains(blockEntity.bag, blockEntity.stage) || i.add(blockEntity.bag,blockEntity.stage,playerEntity)) {
                            playerEntity.displayClientMessage(blockEntity.getTitleContext(blockEntity.stage), false);
                            playerEntity.displayClientMessage(blockEntity.getContext(), false);
                        }else {
                            playerEntity.displayClientMessage(new TranslationTextComponent("story.info.locked"), false);
                        }
                    });
                }else {
                    playerEntity.displayClientMessage(blockEntity.getTitleContext(blockEntity.stage), false);
                    playerEntity.displayClientMessage(blockEntity.getContext(), false);
                }
            }
            return ActionResultType.SUCCESS;
        }
        LOGGER.error("[ArdInGame-StoryPoint]Can't get StoryPointBlock block entity at {} , in world {}.",pos,world);
        return ActionResultType.FAIL;
    }

    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity preBe = world.getBlockEntity(pos);
        if (preBe instanceof StoryPointBE) {
            StoryPointBE be = (StoryPointBE)preBe;
            if (!world.isClientSide) {
                ItemStack itemstack = new ItemStack(ItemRegistry.story_point.get());
                //由方块实体获取存储在物品里的nbt，并判断非空时将它加入物品
                CompoundNBT nbt = be.createSaveData(new CompoundNBT());
                itemstack.addTagElement("BlockEntityTag", nbt);

                ItemEntity itementity = new ItemEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                world.addFreshEntity(itementity);
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader iBlockReader, List<ITextComponent> textComponentList, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, iBlockReader, textComponentList, tooltipFlag);
        CompoundNBT nbt = itemStack.getTagElement("BlockEntityTag");
        if (nbt != null) {
            textComponentList.add(new TranslationTextComponent("story.info.wrote"));
            textComponentList.add(new TranslationTextComponent("story.info.sm").append(String.valueOf(nbt.getBoolean("bagMode"))));
            textComponentList.add(new TranslationTextComponent("story.info.title").append(nbt.getString("title")));
            textComponentList.add(new TranslationTextComponent("story.info.trans_key").append(nbt.getString("transKey")));
            textComponentList.add(new TranslationTextComponent("story.info.bag").append(nbt.getString("bag")));
            textComponentList.add(new TranslationTextComponent("story.info.level").append(String.valueOf(nbt.getInt("level"))));
        }
    }

    public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity entity = world.getBlockEntity(pos);
        if(entity instanceof StoryPointBE){
            ((StoryPointBE) entity).copyFromItemStack(itemStack);
        }
    }

    public ItemStack getCloneItemStack(IBlockReader reader, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getCloneItemStack(reader, pos, state);
        TileEntity entity = reader.getBlockEntity(pos);
        if(entity instanceof StoryPointBE){
            CompoundNBT nbt = ((StoryPointBE) entity).createSaveData(new CompoundNBT());
            itemstack.addTagElement("BlockEntityTag", nbt);
        }
        return itemstack;
    }
}
