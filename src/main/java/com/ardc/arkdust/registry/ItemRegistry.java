package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.preobject.PreBlockItem;
import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.registry.item.Item$BlueprintRegistry;
import com.ardc.arkdust.registry.item.Item$TerraMaterial;
import com.ardc.arkdust.registry.item.Item$CommonWorld;
import com.ardc.arkdust.registry.item.Item$TerraTool;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static void registryItemDeferredRegister(final IEventBus BUS){
        ItemRegistry.ITEMS.register(BUS);
        Item$TerraMaterial.ITEMS.register(BUS);
        Item$BlueprintRegistry.ITEMS.register(BUS);
        Item$CommonWorld.ITEMS.register(BUS);
        Item$TerraTool.ITEMS.register(BUS);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
//    public static final RegistryObject<Item> bc = ITEMS.register("bc_block", Bc::new);//注册物品bc_block

    //预定义Properties



    public static class Rar{
        public static final Rarity NULL = Rarity.create("ard_null", ChatFormatting.DARK_GRAY);
        public static final Rarity COMMON = Rarity.create("ard_common", ChatFormatting.WHITE);
        public static final Rarity SPECIAL = Rarity.create("ard_special", ChatFormatting.DARK_GREEN);
        public static final Rarity VALUABLE = Rarity.create("ard_valuable", ChatFormatting.BLUE);
        public static final Rarity TREASURE = Rarity.create("ard_treasure", ChatFormatting.DARK_PURPLE);
        public static final Rarity RARE = Rarity.create("ard_rare", ChatFormatting.YELLOW);
        public static final Rarity EPIC = Rarity.create("ard_epic", ChatFormatting.GOLD);
    }
    //TerraEnergy泰拉能源 注册
//    public static final RegistryObject<Item> e_oir_reactor_control_board = ITEMS.register("e_oir_reactor_control_board",()->new PreBlockItem(BlockRegistry.e_oir_reactor_control_board.get(), new Item.Properties().tab(ModGroupRegistry.TERRA_ENERGY)));//E类源石反应堆控制面板


    //TerraTechMaterial泰拉科技材料 注册
    public static final RegistryObject<Item> muddy_silicon = ITEMS.register("muddy_silicon",()->new PreItem(new Item.Properties()));

    //辅助性方块注册
    public static final RegistryObject<Item> chaotic_air = ITEMS.register("chaotic_air",()->new PreBlockItem(BlockRegistry.chaotic_air.get(), new Item.Properties()));
    public static final RegistryObject<Item> structure_ignore_block = ITEMS.register("structure_ignore_block",()->new PreBlockItem(BlockRegistry.structure_ignore_block.get(), new Item.Properties()));
    public static final RegistryObject<Item> story_point = ITEMS.register("story_point",()->new PreBlockItem(BlockRegistry.story_point.get(), new Item.Properties()));

//    //test
//    public static final RegistryObject<Item> test_block = ITEMS.register("test_block",()->new BlockItem(BlockRegistry.test_block.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块
    public static final RegistryObject<Item> test_block_b = ITEMS.register("test_block_b",()->new BlockItem(BlockRegistry.test_block_b.get(), new Item.Properties()));//测试方块
//    public static final RegistryObject<Item> test_block_c = ITEMS.register("test_block_c",()->new BlockItem(BlockRegistry.test_block_c.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块
//    public static final RegistryObject<Item> test_block_d = ITEMS.register("test_block_d",()->new BlockItem(BlockRegistry.test_block_d.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块

}
