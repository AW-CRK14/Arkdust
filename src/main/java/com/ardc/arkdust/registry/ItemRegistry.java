package com.ardc.arkdust.registry;

import com.ardc.arkdust.Items.sugar.Industrial_sugar;
import com.ardc.arkdust.Items.sugar.Sugar_lump;
import com.ardc.arkdust.Items.sugar.Sugar_pack;
import com.ardc.arkdust.Items.sugar.Sugar_substitute;
import com.ardc.arkdust.obj_property.MaterialTier;
import com.ardc.arkdust.Items.EnderEnergyRadar;
import com.ardc.arkdust.gui.screen.menu.MenuTestItem;
import com.ardc.arkdust.Items.pre.OIRVaccineItem;
import com.ardc.arkdust.playmethod.oi.OIItem.PreOIBlockItem;
import com.ardc.arkdust.playmethod.oi.OIItem.PreOIItem;
import com.ardc.arkdust.preobject.PreBlockItem;
import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.Items.Bc;
import com.ardc.arkdust.Utils;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> bc = ITEMS.register("bc_block", Bc::new);//注册物品bc_block

    //预定义Properties
    public static final Item.Properties P_CW = new Item.Properties().tab(ModGroupRegistry.WORLD_MATERIAL);
    public static final Item.Properties P_TERRA_MAT = new Item.Properties().tab(ModGroupRegistry.TERRA_WORLD_COMMON_MATERIAL);


    public static class Rar{
        public static final Rarity NULL = Rarity.create("ard_null", TextFormatting.DARK_GRAY);
        public static final Rarity COMMON = Rarity.create("ard_common", TextFormatting.WHITE);
        public static final Rarity SPECIAL = Rarity.create("ard_special", TextFormatting.DARK_GREEN);
        public static final Rarity VALUABLE = Rarity.create("ard_valuable", TextFormatting.BLUE);
        public static final Rarity TREASURE = Rarity.create("ard_treasure", TextFormatting.DARK_PURPLE);
        public static final Rarity RARE = Rarity.create("ard_rare", TextFormatting.YELLOW);
        public static final Rarity EPIC = Rarity.create("ard_epic", TextFormatting.GOLD);
    }


    //TerraCommonMaterial泰拉普料 注册
    public static final RegistryObject<Item> originium = ITEMS.register("originium", ()->new PreOIItem(P_TERRA_MAT,true,3,2,3,12,4));//源石
    public static final RegistryObject<Item> originium_dust = ITEMS.register("originium_dust", ()->new PreOIItem(P_TERRA_MAT,false,2,0,1,32,8));//源石尘e1
    public static final RegistryObject<Item> originium_shard = ITEMS.register("originium_shard", ()->new PreOIItem(P_TERRA_MAT,false,2,1,0,4,1));//源石碎片
    public static final RegistryObject<Item> poor_originium = ITEMS.register("poor_originium", ()->new PreOIItem(P_TERRA_MAT,false,1,0,0,0,2));//贫瘠源石
    public static final RegistryObject<Item> ester = ITEMS.register("ester", ()->new PreItem(P_TERRA_MAT));//一级酯
    public static final RegistryObject<Item> polyester = ITEMS.register("polyester", ()->new PreItem(P_TERRA_MAT.rarity(Rar.SPECIAL)));//二级酯
    public static final RegistryObject<Item> polyester_pack = ITEMS.register("polyester_pack", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//三级酯
    public static final RegistryObject<Item> polyester_lump = ITEMS.register("polyester_lump", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//四级酯
    public static final RegistryObject<Item> diketon = ITEMS.register("diketon", ()->new PreItem(P_TERRA_MAT));//一级酮
    public static final RegistryObject<Item> polyketon = ITEMS.register("polyketon", ()->new PreItem(P_TERRA_MAT.rarity(Rar.SPECIAL)));//二级酮
    public static final RegistryObject<Item> aketon = ITEMS.register("aketon", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//三级酮
    public static final RegistryObject<Item> keton_colloid = ITEMS.register("keton_colloid", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//四级酮
    public static final RegistryObject<Item> oriron_shard = ITEMS.register("oriron_shard", ()->new PreItem(P_TERRA_MAT));//一级异铁
    public static final RegistryObject<Item> oriron = ITEMS.register("oriron", ()->new PreItem(P_TERRA_MAT.rarity(Rar.SPECIAL)));//二级异铁
    public static final RegistryObject<Item> oriron_cluster = ITEMS.register("oriron_cluster", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//三级异铁
    public static final RegistryObject<Item> oriron_block = ITEMS.register("oriron_block", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//四级异铁
    public static final RegistryObject<Item> crystalline_component = ITEMS.register("crystalline_component", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级电子晶体
    public static final RegistryObject<Item> crystalline_circuit = ITEMS.register("crystalline_circuit", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级电子晶体
    public static final RegistryObject<Item> crystalline_electronic_unit = ITEMS.register("crystalline_electronic_unit", ()->new PreItem(P_TERRA_MAT));//三级电子晶体
    public static final RegistryObject<Item> sugar_substitute = ITEMS.register("sugar_substitute", Sugar_substitute::new);//一级糖
    public static final RegistryObject<Item> industrial_sugar = ITEMS.register("industrial_sugar", Industrial_sugar::new);//二级糖
    public static final RegistryObject<Item> sugar_pack = ITEMS.register("sugar_pack", Sugar_pack::new);//三级糖
    public static final RegistryObject<Item> sugar_lump = ITEMS.register("sugar_lump", Sugar_lump::new);//四级糖
    public static final RegistryObject<Item> cutting_fluid_solution = ITEMS.register("cutting_fluid_solution", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级切削液
    public static final RegistryObject<Item> compound_cutting_fluid = ITEMS.register("compound_cutting_fluid", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级切削液
    public static final RegistryObject<Item> damaged_device = ITEMS.register("damaged_device", ()->new PreItem(P_TERRA_MAT));//一级装置
    public static final RegistryObject<Item> device = ITEMS.register("device", ()->new PreItem(P_TERRA_MAT.rarity(Rar.SPECIAL)));//二级装置
    public static final RegistryObject<Item> integrated_device = ITEMS.register("integrated_device", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//三级装置
    public static final RegistryObject<Item> optimized_device = ITEMS.register("optimized_device",()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//四级装置
    public static final RegistryObject<Item> coagulating_gel = ITEMS.register("coagulating_gel", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级凝胶
    public static final RegistryObject<Item> polymerized_gel = ITEMS.register("polymerized_gel", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级凝胶
    public static final RegistryObject<Item> grindstone = ITEMS.register("grindstone", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级研磨石
    public static final RegistryObject<Item> grindstone_pentahydrate = ITEMS.register("grindstone_pentahydrate", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级研磨石
    public static final RegistryObject<Item> incandescent_alloy = ITEMS.register("incandescent_alloy", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级炽合金
    public static final RegistryObject<Item> incandescent_alloy_block = ITEMS.register("incandescent_alloy_block", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级炽合金
    public static final RegistryObject<Item> loxic_kohl = ITEMS.register("loxic_kohl", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级醇
    public static final RegistryObject<Item> white_horse_kohl = ITEMS.register("white_horse_kohl",()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级醇
    public static final RegistryObject<Item> manganese_ore = ITEMS.register("manganese_ore", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级锰
    public static final RegistryObject<Item> manganese_trihydrate = ITEMS.register("manganese_trihydrate", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级锰
    public static final RegistryObject<Item> orirock = ITEMS.register("orirock", ()->new PreItem(P_TERRA_MAT));//一级源岩
    public static final RegistryObject<Item> orirock_cube = ITEMS.register("orirock_cube", ()->new PreItem(P_TERRA_MAT.rarity(Rar.SPECIAL)));//二级源岩
    public static final RegistryObject<Item> orirock_cluster = ITEMS.register("orirock_cluster", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//三级源岩
    public static final RegistryObject<Item> orirock_concentration = ITEMS.register("orirock_concentration", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//四级源岩
    public static final RegistryObject<Item> refined_solvent = ITEMS.register("refined_solvent", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级溶剂
    public static final RegistryObject<Item> semi_synthetic_solvent = ITEMS.register("semi_synthetic_solvent", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级溶剂
    public static final RegistryObject<Item> rma70_12 = ITEMS.register("rma70_12", ()->new PreItem(P_TERRA_MAT.rarity(Rar.VALUABLE)));//一级海胆
    public static final RegistryObject<Item> rma70_24 = ITEMS.register("rma70_24", ()->new PreItem(P_TERRA_MAT.rarity(Rar.TREASURE)));//二级海胆
    public static final RegistryObject<Item> d32_steel = ITEMS.register("d32_steel", ()->new PreItem(P_TERRA_MAT.rarity(Rar.RARE)));//D32钢
    public static final RegistryObject<Item> bipolar_nanoflake = ITEMS.register("bipolar_nanoflake", ()->new PreItem(P_TERRA_MAT.rarity(Rar.RARE)));//纳米片
    public static final RegistryObject<Item> polymerization_preparation = ITEMS.register("polymerization_preparation", ()->new PreItem(P_TERRA_MAT.rarity(Rar.RARE)));//聚合剂

    //WorldMaterial闭环区物品 注册
    public static final RegistryObject<Item> dt_crystal = ITEMS.register("dt_crystal", ()->new PreItem(P_CW.rarity(Rar.TREASURE)));//静谧水晶
    public static final RegistryObject<Item> pau_ore = ITEMS.register("pau_ore",()->new PreBlockItem(BlockRegistry.pau_ore.get(), P_CW));//赤金矿石
    public static final RegistryObject<Item> pau_block = ITEMS.register("pau_block",()->new PreBlockItem(BlockRegistry.pau_block.get(), P_CW));//赤金块
    public static final RegistryObject<Item> pau_ingot = ITEMS.register("pau_ingot", ()->new PreItem(P_CW.rarity(Rar.SPECIAL)));//赤金锭
    public static final RegistryObject<Item> pau_nugget = ITEMS.register("pau_nugget", ()->new PreItem(P_CW));//赤金粒
    public static final RegistryObject<Item> pau_fluorescence_glass = ITEMS.register("pau_fluorescence_glass",()->new BlockItem(BlockRegistry.pau_fluorescence_glass.get(), P_CW));//赤金辉光玻璃
    public static final RegistryObject<Item> common_food_bag = ITEMS.register("common_food_bag", ()->new PreItem(P_CW));//普通食物包
    public static final RegistryObject<Item> blue_crystal = ITEMS.register("blue_crystal",()->new PreBlockItem(BlockRegistry.blue_crystal.get(), P_CW));//赤金辉光玻璃
    //    public static final RegistryObject<Item> blaze_rod_plant = ITEMS.register("blaze_rod_plant",()->new PreBlockItem(BlockRegistry.blaze_rod_plant.get(), P_CW));//烈焰苗
    public static final RegistryObject<Item> common_food_box = ITEMS.register("common_food_box",()->new PreBlockItem(BlockRegistry.common_food_box.get(), P_CW));//普通食物盒子
    public static final RegistryObject<Item> blackstone_lamp = ITEMS.register("blackstone_lamp",()->new PreBlockItem(BlockRegistry.blackstone_lamp.get(), P_CW));//黑石灯
    public static final RegistryObject<Item> blackstone_sand = ITEMS.register("blackstone_sand",()->new PreBlockItem(BlockRegistry.blackstone_sand.get(), P_CW));//黑石沙
    public static final RegistryObject<Item> blackstone_medical_point = ITEMS.register("blackstone_medical_point",()->new PreBlockItem(BlockRegistry.blackstone_medical_point.get(), P_CW));//黑石沙
    public static final RegistryObject<Item> hang_screened_table = ITEMS.register("hang_screened_table",()->new PreBlockItem(BlockRegistry.hang_screened_table.get(), P_CW));//简易筛矿台
    public static final RegistryObject<Item> netherite_nugget = ITEMS.register("netherite_nugget", ()->new PreItem(P_CW.rarity(Rar.SPECIAL)));//下届合金粒
//    public static final RegistryObject<Item> oitest_item = ITEMS.register("oitest_item", ()->new PreOIItem(P_CW,false,2,3,0,2,0));//测试物品
    public static final RegistryObject<Item> c_originium_block = ITEMS.register("c_originium_block", ()->new PreOIBlockItem(BlockRegistry.c_originium_block.get(), P_CW,false,2,2,0,5,2));//c型活性源石
    public static final RegistryObject<Item> d_originium_block = ITEMS.register("d_originium_block", ()->new PreOIBlockItem(BlockRegistry.d_originium_block.get(), P_CW,false,2,2,0,3,0));//d型失活源石
    public static final RegistryObject<Item> l1_oir_vaccine_a = ITEMS.register("l1_oir_vaccine_a", ()->new OIRVaccineItem(false,1,0.8F));//1-1源石疫苗
    public static final RegistryObject<Item> l2_oir_vaccine_a = ITEMS.register("l2_oir_vaccine_a", ()->new OIRVaccineItem(false,2,1.2F));//2-1源石疫苗
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
    public static final RegistryObject<Item> menu_test = ITEMS.register("menu_test",MenuTestItem::new);
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
    public static final RegistryObject<Item> radiant_crystal_ore = ITEMS.register("radiant_crystal_ore",()->new PreBlockItem(BlockRegistry.radiant_crystal_ore.get(), P_CW));
    public static final RegistryObject<Item> basic_blueprint_reduce_box = ITEMS.register("basic_blueprint_reduce_box",()->new PreBlockItem(BlockRegistry.basic_blueprint_reduce_box.get(), P_CW));
    public static final RegistryObject<Item> stone_oak_log = ITEMS.register("stone_oak_log",()->new PreBlockItem(BlockRegistry.stone_oak_log.get(), P_CW));
    public static final RegistryObject<Item> stone_oak_wood = ITEMS.register("stone_oak_wood",()->new PreBlockItem(BlockRegistry.stone_oak_wood.get(), P_CW));
    public static final RegistryObject<Item> stone_oak_plank = ITEMS.register("stone_oak_plank",()->new PreBlockItem(BlockRegistry.stone_oak_plank.get(), P_CW));
    public static final RegistryObject<Item> fractured_oak_log = ITEMS.register("fractured_oak_log",()->new PreBlockItem(BlockRegistry.fractured_oak_log.get(), P_CW));
    public static final RegistryObject<Item> stone_wood_stick = ITEMS.register("stone_wood_stick",()->new PreItem(P_CW));
    public static final RegistryObject<Item> mycelium_pile = ITEMS.register("mycelium_pile",()->new PreBlockItem(BlockRegistry.mycelium_pile.get(), P_CW));


    //TerraTools泰拉工具工具 注册
    public static final RegistryObject<Item> unnamed_sword = ITEMS.register("unnamed_sword", ()->new SwordItem(MaterialTier.PURE_GOLD, 24, -1.6F, new Item.Properties().fireResistant().tab(ModGroupRegistry.TERRA_TOOLS)));//不知名的刀
    public static final RegistryObject<Item> pau_epee = ITEMS.register("pau_epee", ()->new SwordItem(MaterialTier.PURE_GOLD, 10, -3.4F, new Item.Properties().tab(ModGroupRegistry.TERRA_TOOLS)));//赤金重剑
    public static final RegistryObject<Item> pau_sword = ITEMS.register("pau_sword", ()->new SwordItem(MaterialTier.PURE_GOLD, 2, -2.2F, new Item.Properties().tab(ModGroupRegistry.TERRA_TOOLS)));//赤金剑
    public static final RegistryObject<Item> pau_pickaxe = ITEMS.register("pau_pickaxe", ()->new PickaxeItem(MaterialTier.PURE_GOLD, -1, -2.8F, new Item.Properties().tab(ModGroupRegistry.TERRA_TOOLS)));//赤金镐
    public static final RegistryObject<Item> pau_axe = ITEMS.register("pau_axe", ()->new AxeItem(MaterialTier.PURE_GOLD, 3, -3, new Item.Properties().tab(ModGroupRegistry.TERRA_TOOLS)));//赤金斧
    public static final RegistryObject<Item> pau_hoe = ITEMS.register("pau_hoe", ()->new HoeItem(MaterialTier.PURE_GOLD, -4, -1.5F, new Item.Properties().tab(ModGroupRegistry.TERRA_TOOLS)));//赤金锄
    public static final RegistryObject<Item> pau_shovel = ITEMS.register("pau_shovel", ()->new ShovelItem(MaterialTier.PURE_GOLD, -2, -2.1F, new Item.Properties().tab(ModGroupRegistry.TERRA_TOOLS)));//赤金铲

    //TerraEnergy泰拉能源 注册
    public static final RegistryObject<Item> e_oir_reactor_control_board = ITEMS.register("e_oir_reactor_control_board",()->new PreBlockItem(BlockRegistry.e_oir_reactor_control_board.get(), new Item.Properties().tab(ModGroupRegistry.TERRA_ENERGY)));//E类源石反应堆控制面板

    //TerraIndustrialMaterial泰拉工业材料 注册
    public static final RegistryObject<Item> compressed_iron_ingot = ITEMS.register("compressed_iron_ingot", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.TERRA_INDUSTRIAL_MATERIAL)));//压缩铁
    public static final RegistryObject<Item> iron_structure_frame = ITEMS.register("iron_structure_frame",()->new PreBlockItem(BlockRegistry.iron_structure_frame.get(), new Item.Properties().tab(ModGroupRegistry.TERRA_INDUSTRIAL_MATERIAL)));//铁质结构框架
    public static final RegistryObject<Item> iron_structure_frame_creator = ITEMS.register("iron_structure_frame_creator",()->new PreBlockItem(BlockRegistry.iron_structure_frame_creator.get(), new Item.Properties().tab(ModGroupRegistry.TERRA_INDUSTRIAL_MATERIAL)));//铁质结构框架搭建器
//    public static final RegistryObject<Item> projection_block = ITEMS.register("projection_block",()->new PreBlockItem(BlockRegistry.projection_block.get(), new Item.Properties().tab(ModGroupRegistry.terraIndustrialMaterial)));//投影方块

    //TerraTechMaterial泰拉科技材料 注册
    public static final RegistryObject<Item> muddy_silicon = ITEMS.register("muddy_silicon",()->new PreItem(new Item.Properties()));

    //辅助性方块注册
    public static final RegistryObject<Item> chaotic_air = ITEMS.register("chaotic_air",()->new PreBlockItem(BlockRegistry.chaotic_air.get(), new Item.Properties()));
    public static final RegistryObject<Item> structure_ignore_block = ITEMS.register("structure_ignore_block",()->new PreBlockItem(BlockRegistry.structure_ignore_block.get(), new Item.Properties()));
    public static final RegistryObject<Item> story_point = ITEMS.register("story_point",()->new PreBlockItem(BlockRegistry.story_point.get(), new Item.Properties()));

//    //test
//    public static final RegistryObject<Item> test_block = ITEMS.register("test_block",()->new BlockItem(BlockRegistry.test_block.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块
    public static final RegistryObject<Item> test_block_b = ITEMS.register("test_block_b",()->new BlockItem(BlockRegistry.test_block_b.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));//测试方块
//    public static final RegistryObject<Item> test_block_c = ITEMS.register("test_block_c",()->new BlockItem(BlockRegistry.test_block_c.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块
//    public static final RegistryObject<Item> test_block_d = ITEMS.register("test_block_d",()->new BlockItem(BlockRegistry.test_block_d.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块

}
