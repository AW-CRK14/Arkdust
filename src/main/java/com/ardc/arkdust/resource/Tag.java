package com.ardc.arkdust.resource;

import com.ardc.arkdust.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class Tag
{
    public static void init ()
    {
        Blocks.init();
        Items.init();
        Fluids.init();
        LivingEntities.init();
        Biomes.init();
    }

    public static class Blocks
    {
        private static void init(){}

//        public static final net.minecraftforge.common.TagKey
//        <Item> BARRELS = tag("barrels");
        public static final TagKey<Block> ALLOW_ORIROCK_SPREAD = tag("allow_originium_spread");
        public static final TagKey
                <Block> NATURAL_BLOCK = tag("natural_blocks");
        public static final TagKey
                <Block> SUPPLY_BLOCK = tag("supply_blocks");
        public static final TagKey
                <Block> STRUCTURE$WASTELAND_MINESHAFT_GROUND = tag("structure/wasteland_mineshaft_ground");
        public static final TagKey
                <Block> STRUCTURE$WASTELAND_MINESHAFT_DIRTY_CONCRETE = tag("structure/wasteland_mineshaft_dirty_concrete");
        public static final TagKey
                <Block> STRUCTURE$WASTELAND_MINESHAFT_PILLAR_DOWN = tag("structure/wasteland_mineshaft_pillar_down");
        public static final TagKey
                <Block> OVERWORLD_ORES = tag("generate_ore/overworld_ore");

        public static final TagKey
                <Block> STRUCTURE$TAG_CHANGE_BLACKLIST = tag("structure/tag_random_change_blacklist");

        private static TagKey<Block> tag(String name)
        {
            return BlockTags.create(new ResourceLocation(Utils.MOD_ID, name));
        }
    }

    public static class Items
    {
        private static void init(){}




        private static TagKey<Item> tag(String name)
        {
            return ItemTags.create(new ResourceLocation(Utils.MOD_ID, name));
        }
    }

    public static class Fluids
    {
        private static void init() {}

//        public static final net.minecraftforge.common.TagKey
//        <Fluid> MILK = tag("milk");

        private static TagKey<Fluid> tag(String name)
        {
            return FluidTags.create(new ResourceLocation(Utils.MOD_ID, name));
        }
    }

    public static class LivingEntities
    {
        private static void init(){}


        public static final TagKey<EntityType<?>> STRUCTURE$WASTELAND_MINESHAFT_DIRTY_CONCRETE = tag("bsmp_treat");



        private static TagKey<EntityType<?>> tag(String name)
        {
           return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Utils.MOD_ID,name));
        }
    }

    public static class Biomes{
        private static void init(){}

        public static final TagKey<Biome> IS_LAND = tag("is_land");
        public static final TagKey<Biome> ALLOW_GWM_GEN = tag("allow_gravel_wasteland_mineshaft_gen");



        private static TagKey<Biome> tag(String name)
        {
            return TagKey.create(Registries.BIOME, new ResourceLocation(Utils.MOD_ID,name));
        }

    }
}
