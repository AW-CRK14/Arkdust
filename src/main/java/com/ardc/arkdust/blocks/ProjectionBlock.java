package com.ardc.arkdust.blocks;

import com.ardc.arkdust.blockstate.DropSelfBlock;
import com.ardc.arkdust.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ProjectionBlock extends DropSelfBlock {
    public static final List<Block> NOT_ALLOWED = Arrays.asList(Blocks.BROWN_MUSHROOM_BLOCK,Blocks.RED_MUSHROOM_BLOCK,Blocks.MUSHROOM_STEM,Blocks.COMPOSTER, BlockRegistry.blackstone_lamp.get());
    public ProjectionBlock(){
        super(Properties.of(Material.STONE).strength(5,40).harvestLevel(3).harvestTool(ToolType.PICKAXE).noCollission());
    }

//    public boolean hasTileEntity(BlockState state) {
//        return true;
//    }
//
//    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
//        return new ProjectionBlockBE();
//    }

//    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
//        ItemStack itemStack = player.getMainHandItem();
//        ProjectionBlockBE be = (ProjectionBlockBE) worldIn.getBlockEntity(pos);
//        if(be == null) return ActionResultType.FAIL;
//        if(itemStack.getItem() instanceof BlockItem && protectByName(itemStack) && !NOT_ALLOWED.contains(((BlockItem) itemStack.getItem()).getBlock())){
//            be.set(itemStack);
//            return ActionResultType.SUCCESS;
//        }
//        return ActionResultType.PASS;
//    }

    public boolean protectByName(ItemStack itemStack){
        if(itemStack == null) return false;
        ResourceLocation rl = itemStack.getItem().getRegistryName();
        if(rl ==null) return false;
        String itemName = rl.toString();
        String[] pattern = new String[]{"minecraft:.*","arkdust:.*"};
        for(String s : pattern){
            if(Pattern.matches(s,itemName)){
                return true;
            }
        }
        return false;
    }
}
