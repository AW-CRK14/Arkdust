package com.ardc.arkdust.CodeMigration.resourcelocation;

import com.ardc.arkdust.Utils;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class Tag
{
    public static void init ()
    {
        Blocks.init();
        Items.init();
        Fluids.init();
    }

    public static class Blocks
    {
        private static void init(){}

//        public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Item> BARRELS = tag("barrels");
        public static final Tags.IOptionalNamedTag<Block> ALLOW_ORIROCK_SPREAD = tag("allow_originium_spread");
        public static final Tags.IOptionalNamedTag<Block> NATURAL_BLOCK = tag("natural_blocks");

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Block> tag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(Utils.MOD_ID, name));
        }
    }

    public static class Items
    {
        private static void init(){}




        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> tag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation(Utils.MOD_ID, name));
        }
    }

    public static class Fluids
    {
        private static void init() {}

//        public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Fluid> MILK = tag("milk");

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Fluid> tag(String name)
        {
            return FluidTags.createOptional(new ResourceLocation(Utils.MOD_ID, name));
        }
    }
}
