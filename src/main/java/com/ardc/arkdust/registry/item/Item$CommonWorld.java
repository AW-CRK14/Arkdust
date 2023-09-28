package com.ardc.arkdust.registry.item;

import com.ardc.arkdust.Items.EnderEnergyRadar;
import com.ardc.arkdust.Items.pre.OIRVaccineItem;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.screen.menu.MenuTestItem;
import com.ardc.arkdust.playmethod.oi.OIItem.PreOIBlockItem;
import com.ardc.arkdust.preobject.PreBlockItem;
import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Item$CommonWorld {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final Item.Properties P_CW = new Item.Properties();

    //WorldMaterial闭环区物品 注册
    public static final RegistryObject<Item> dt_crystal = ITEMS.register("dt_crystal", ()->new PreItem(P_CW.rarity(ItemRegistry.Rar.TREASURE)));//静谧水晶
    public static final RegistryObject<Item> pau_ore = ITEMS.register("pau_ore",()->new PreBlockItem(BlockRegistry.pau_ore.get(), P_CW));//赤金矿石
    public static final RegistryObject<Item> pau_block = ITEMS.register("pau_block",()->new PreBlockItem(BlockRegistry.pau_block.get(), P_CW));//赤金块
    public static final RegistryObject<Item> pau_ingot = ITEMS.register("pau_ingot", ()->new PreItem(P_CW.rarity(ItemRegistry.Rar.SPECIAL)));//赤金锭
    public static final RegistryObject<Item> pau_nugget = ITEMS.register("pau_nugget", ()->new PreItem(P_CW));//赤金粒
    public static final RegistryObject<Item> pau_fluorescence_glass = ITEMS.register("pau_fluorescence_glass",()->new PreBlockItem(BlockRegistry.pau_fluorescence_glass.get(), P_CW));//赤金辉光玻璃
    public static final RegistryObject<Item> common_food_bag = ITEMS.register("common_food_bag", ()->new PreItem(P_CW));//普通食物包
    //    public static final RegistryObject<Item> blue_crystal = ITEMS.register("blue_crystal",()->new PreBlockItem(BlockRegistry.blue_crystal.get(), P_CW));//赤金辉光玻璃
    //    public static final RegistryObject<Item> blaze_rod_plant = ITEMS.register("blaze_rod_plant",()->new PreBlockItem(BlockRegistry.blaze_rod_plant.get(), P_CW));//烈焰苗
    public static final RegistryObject<Item> common_food_box = ITEMS.register("common_food_box",()->new PreBlockItem(BlockRegistry.common_food_box.get(), P_CW));//普通食物盒子
    public static final RegistryObject<Item> blackstone_lamp = ITEMS.register("blackstone_lamp",()->new PreBlockItem(BlockRegistry.blackstone_lamp.get(), P_CW));//黑石灯
    public static final RegistryObject<Item> blackstone_sand = ITEMS.register("blackstone_sand",()->new PreBlockItem(BlockRegistry.blackstone_sand.get(), P_CW));//黑石沙
    public static final RegistryObject<Item> blackstone_medical_point = ITEMS.register("blackstone_medical_point",()->new PreBlockItem(BlockRegistry.blackstone_medical_point.get(), P_CW));//黑石沙
    public static final RegistryObject<Item> hang_screened_table = ITEMS.register("hang_screened_table",()->new PreBlockItem(BlockRegistry.hang_screened_table.get(), P_CW));//简易筛矿台
    public static final RegistryObject<Item> netherite_nugget = ITEMS.register("netherite_nugget", ()->new PreItem(P_CW.rarity(ItemRegistry.Rar.SPECIAL)));//下届合金粒
    //    public static final RegistryObject<Item> oitest_item = ITEMS.register("oitest_item", ()->new PreOIItem(P_CW,false,2,3,0,2,0));//测试物品
    public static final RegistryObject<Item> c_originium_block = ITEMS.register("c_originium_block", ()->new PreOIBlockItem(BlockRegistry.c_originium_block.get(), P_CW,false,2,2,0,5,2));//c型活性源石
    public static final RegistryObject<Item> d_originium_block = ITEMS.register("d_originium_block", ()->new PreOIBlockItem(BlockRegistry.d_originium_block.get(), P_CW,false,2,2,0,3,0));//d型失活源石
    public static final RegistryObject<Item> l1_oir_vaccine_a = ITEMS.register("l1_oir_vaccine_a", ()->new OIRVaccineItem(false,20));//1-1源石疫苗
    public static final RegistryObject<Item> l2_oir_vaccine_a = ITEMS.register("l2_oir_vaccine_a", ()->new OIRVaccineItem(false,45));//2-1源石疫苗
    public static final RegistryObject<Item> broken_ender_energy_radar = ITEMS.register("broken_ender_energy_radar", ()->new EnderEnergyRadar(false));//损坏末影雷达
    public static final RegistryObject<Item> ender_energy_radar = ITEMS.register("ender_energy_radar", ()->new EnderEnergyRadar(true));//末影雷达
    public static final RegistryObject<Item> sealed_energy_substrate = ITEMS.register("sealed_energy_substrate", ()->new PreItem(P_CW,true));
    public static final RegistryObject<Item> se_rcu_machine = ITEMS.register("se_rcu_machine", ()->new PreBlockItem(BlockRegistry.se_rcu_machine.get(), P_CW));
    public static final RegistryObject<Item> sit_white_skeleton_block = ITEMS.register("sit_white_skeleton_block", ()->new PreBlockItem(BlockRegistry.sit_white_skeleton_block.get(), P_CW));//坐地白色骷髅
    public static final RegistryObject<Item> lay0_white_skeleton_block = ITEMS.register("lay0_white_skeleton_block", ()->new PreBlockItem(BlockRegistry.lay0_white_skeleton_block.get(), P_CW));//白色骨架
    //    public static final RegistryObject<Item> tombstone_1_1 = ITEMS.register("tombstone_1_1", ()->new PreBlockItem(BlockRegistry.tombstone_1_1.get(), P_CW));//墓碑1-1
    public static final RegistryObject<Item> tombstone_2_1 = ITEMS.register("tombstone_2_1", ()->new PreBlockItem(BlockRegistry.tombstone_2_1.get(), P_CW));//墓碑2-1
    public static final RegistryObject<Item> tombstone_2_2 = ITEMS.register("tombstone_2_2", ()->new PreBlockItem(BlockRegistry.tombstone_2_2.get(), P_CW));//墓碑2-2
    public static final RegistryObject<Item> tombstone_2_3 = ITEMS.register("tombstone_2_3", ()->new PreBlockItem(BlockRegistry.tombstone_2_3.get(), P_CW));//墓碑2-3
    public static final RegistryObject<Item> red_caution_light = ITEMS.register("red_caution_light", ()->new PreBlockItem(BlockRegistry.red_caution_light.get(), P_CW));//红色应急灯
    public static final RegistryObject<Item> menu_test = ITEMS.register("menu_test", MenuTestItem::new);
    public static final RegistryObject<Item> c7lab_strock = ITEMS.register("c7lab_strock",()->new PreBlockItem(BlockRegistry.c7lab_strock.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ground_1 = ITEMS.register("c7lab_strock_ground_1",()->new PreBlockItem(BlockRegistry.c7lab_strock_ground_1.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ground_2 = ITEMS.register("c7lab_strock_ground_2",()->new PreBlockItem(BlockRegistry.c7lab_strock_ground_2.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ground_3 = ITEMS.register("c7lab_strock_ground_3",()->new PreBlockItem(BlockRegistry.c7lab_strock_ground_3.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ground_4 = ITEMS.register("c7lab_strock_ground_4",()->new PreBlockItem(BlockRegistry.c7lab_strock_ground_4.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ground_5 = ITEMS.register("c7lab_strock_ground_5",()->new PreBlockItem(BlockRegistry.c7lab_strock_ground_5.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ceiling_1 = ITEMS.register("c7lab_strock_ceiling_1",()->new PreBlockItem(BlockRegistry.c7lab_strock_ceiling_1.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ceiling_2 = ITEMS.register("c7lab_strock_ceiling_2",()->new PreBlockItem(BlockRegistry.c7lab_strock_ceiling_2.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_pillar_1 = ITEMS.register("c7lab_strock_pillar_1",()->new PreBlockItem(BlockRegistry.c7lab_strock_pillar_1.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_pillar_2 = ITEMS.register("c7lab_strock_pillar_2",()->new PreBlockItem(BlockRegistry.c7lab_strock_pillar_2.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_wall_1 = ITEMS.register("c7lab_strock_wall_1",()->new PreBlockItem(BlockRegistry.c7lab_strock_wall_1.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_wall_2 = ITEMS.register("c7lab_strock_wall_2",()->new PreBlockItem(BlockRegistry.c7lab_strock_wall_2.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_wall_3 = ITEMS.register("c7lab_strock_wall_3",()->new PreBlockItem(BlockRegistry.c7lab_strock_wall_3.get(), P_CW.fireResistant()));
    public static final RegistryObject<Item> c7lab_strock_ceiling_lighting = ITEMS.register("c7lab_strock_ceiling_lighting",()->new PreBlockItem(BlockRegistry.c7lab_strock_ceiling_lighting.get(), P_CW));
    public static final RegistryObject<Item> c7lab_strock_glass_1 = ITEMS.register("c7lab_strock_glass_1",()->new PreBlockItem(BlockRegistry.c7lab_strock_glass_1.get(), P_CW));
    public static final RegistryObject<Item> c7lab_strock_glass_2 = ITEMS.register("c7lab_strock_glass_2",()->new PreBlockItem(BlockRegistry.c7lab_strock_glass_2.get(), P_CW));
    public static final RegistryObject<Item> rushed_iron_mineshaft_board = ITEMS.register("rushed_iron_mineshaft_board",()->new PreBlockItem(BlockRegistry.rushed_iron_mineshaft_board.get(), P_CW));
    public static final RegistryObject<Item> rushed_iron_mineshaft_net = ITEMS.register("rushed_iron_mineshaft_net",()->new PreBlockItem(BlockRegistry.rushed_iron_mineshaft_net.get(), P_CW));
    public static final RegistryObject<Item> dark_rushed_iron_block = ITEMS.register("dark_rushed_iron_block",()->new PreBlockItem(BlockRegistry.dark_rushed_iron_block.get(), P_CW));
    public static final RegistryObject<Item> rushed_iron_block = ITEMS.register("rushed_iron_block",()->new PreBlockItem(BlockRegistry.rushed_iron_block.get(), P_CW));
    public static final RegistryObject<Item> red_paint_rushed_iron_block = ITEMS.register("red_paint_rushed_iron_block",()->new PreBlockItem(BlockRegistry.red_paint_rushed_iron_block.get(), P_CW));
    public static final RegistryObject<Item> broken_rushed_iron_mineshaft_net = ITEMS.register("broken_rushed_iron_mineshaft_net",()->new PreBlockItem(BlockRegistry.broken_rushed_iron_mineshaft_net.get(), P_CW));
    public static final RegistryObject<Item> dirty_concrete = ITEMS.register("dirty_concrete",()->new PreBlockItem(BlockRegistry.dirty_concrete.get(), P_CW));
    //    public static final RegistryObject<Item> dirty_concrete_light = ITEMS.register("dirty_concrete_light",()->new PreBlockItem(BlockRegistry.dirty_concrete_light.get(), P_CW));
    public static final RegistryObject<Item> dirty_concrete_powder = ITEMS.register("dirty_concrete_powder",()->new PreBlockItem(BlockRegistry.dirty_concrete_powder.get(), P_CW));
    public static final RegistryObject<Item> cracked_dirty_concrete = ITEMS.register("cracked_dirty_concrete",()->new PreBlockItem(BlockRegistry.cracked_dirty_concrete.get(), P_CW));
    public static final RegistryObject<Item> wasteland_mineshaft_spandrel_girder = ITEMS.register("wasteland_mineshaft_spandrel_girder",()->new PreBlockItem(BlockRegistry.wasteland_mineshaft_spandrel_girder.get(), P_CW));
    public static final RegistryObject<Item> oried_dirty_concrete = ITEMS.register("oried_dirty_concrete",()->new PreBlockItem(BlockRegistry.oried_dirty_concrete.get(), P_CW));
    public static final RegistryObject<Item> radiant_crystal_shard = ITEMS.register("radiant_crystal_shard",()->new PreItem(P_CW));
    //    public static final RegistryObject<Item> radiant_crystal_ore = ITEMS.register("radiant_crystal_ore",()->new PreBlockItem(BlockRegistry.radiant_crystal_ore.get(), P_CW));
    public static final RegistryObject<Item> basic_blueprint_reduce_box = ITEMS.register("basic_blueprint_reduce_box",()->new PreBlockItem(BlockRegistry.basic_blueprint_reduce_box.get(), P_CW));
    public static final RegistryObject<Item> stone_oak_log = ITEMS.register("stone_oak_log",()->new PreBlockItem(BlockRegistry.stone_oak_log.get(), P_CW));
    public static final RegistryObject<Item> stone_oak_wood = ITEMS.register("stone_oak_wood",()->new PreBlockItem(BlockRegistry.stone_oak_wood.get(), P_CW));
    public static final RegistryObject<Item> stone_oak_plank = ITEMS.register("stone_oak_plank",()->new PreBlockItem(BlockRegistry.stone_oak_plank.get(), P_CW));
    public static final RegistryObject<Item> fractured_oak_log = ITEMS.register("fractured_oak_log",()->new PreBlockItem(BlockRegistry.fractured_oak_log.get(), P_CW));
    public static final RegistryObject<Item> stone_wood_stick = ITEMS.register("stone_wood_stick",()->new PreItem(P_CW));
    public static final RegistryObject<Item> mycelium_pile = ITEMS.register("mycelium_pile",()->new PreBlockItem(BlockRegistry.mycelium_pile.get(), P_CW));

    //TerraIndustrialMaterial泰拉工业材料 注册
    public static final RegistryObject<Item> compressed_iron_ingot = ITEMS.register("compressed_iron_ingot", ()->new PreItem(new Item.Properties()));//压缩铁
    public static final RegistryObject<Item> iron_structure_frame = ITEMS.register("iron_structure_frame",()->new PreBlockItem(BlockRegistry.iron_structure_frame.get(), new Item.Properties()));//铁质结构框架
    public static final RegistryObject<Item> iron_structure_frame_creator = ITEMS.register("iron_structure_frame_creator",()->new PreBlockItem(BlockRegistry.iron_structure_frame_creator.get(), new Item.Properties()));//铁质结构框架搭建器
//    public static final RegistryObject<Item> projection_block = ITEMS.register("projection_block",()->new PreBlockItem(BlockRegistry.projection_block.get(), new Item.Properties().tab(ModGroupRegistry.terraIndustrialMaterial)));//投影方块
}
