package com.ardc.arkdust.registry;

import com.ardc.arkdust.CodeMigration.MaterialTier;
import com.ardc.arkdust.Items.items.EnderEnergyRadar;
import com.ardc.arkdust.preobject.item.BaseBlueprintItem;
import com.ardc.arkdust.preobject.item.OIRVaccineItem;
import com.ardc.arkdust.preobject.pre.OIItem.PreOIBlockItem;
import com.ardc.arkdust.preobject.pre.OIItem.PreOIItem;
import com.ardc.arkdust.preobject.pre.PreBlockItem;
import com.ardc.arkdust.preobject.pre.PreItem;
import com.ardc.arkdust.Items.items.Bc;
import com.ardc.arkdust.Items.items.sugar.*;
import com.ardc.arkdust.Utils;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> bc = ITEMS.register("bc_block", Bc::new);//注册物品bc_block


    //TerraCommonMaterial泰拉普料 注册
    public static final RegistryObject<Item> originium = ITEMS.register("originium", ()->new PreOIItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial),true,3,2,3,12,4));//源石
    public static final RegistryObject<Item> originium_dust = ITEMS.register("originium_dust", ()->new PreOIItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial),false,2,0,1,32,8));//源石尘e1
    public static final RegistryObject<Item> originium_shard = ITEMS.register("originium_shard", ()->new PreOIItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial),false,2,1,0,4,1));//源石碎片
    public static final RegistryObject<Item> poor_originium = ITEMS.register("poor_originium", ()->new PreOIItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial),false,1,0,0,0,2));//贫瘠源石
    public static final RegistryObject<Item> ester = ITEMS.register("ester", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级酯
    public static final RegistryObject<Item> polyester = ITEMS.register("polyester", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级酯
    public static final RegistryObject<Item> polyester_pack = ITEMS.register("polyester_pack", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//三级酯
    public static final RegistryObject<Item> polyester_lump = ITEMS.register("polyester_lump", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//四级酯
    public static final RegistryObject<Item> diketon = ITEMS.register("diketon", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级酮
    public static final RegistryObject<Item> polyketon = ITEMS.register("polyketon", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级酮
    public static final RegistryObject<Item> aketon = ITEMS.register("aketon", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//三级酮
    public static final RegistryObject<Item> keton_colloid = ITEMS.register("keton_colloid", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//四级酮
    public static final RegistryObject<Item> oriron_shard = ITEMS.register("oriron_shard", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级异铁
    public static final RegistryObject<Item> oriron = ITEMS.register("oriron", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级异铁
    public static final RegistryObject<Item> oriron_cluster = ITEMS.register("oriron_cluster", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//三级异铁
    public static final RegistryObject<Item> oriron_block = ITEMS.register("oriron_block", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//四级异铁
    public static final RegistryObject<Item> crystalline_component = ITEMS.register("crystalline_component", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级电子晶体
    public static final RegistryObject<Item> crystalline_circuit = ITEMS.register("crystalline_circuit", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级电子晶体
    public static final RegistryObject<Item> crystalline_electronic_unit = ITEMS.register("crystalline_electronic_unit", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//三级电子晶体
    public static final RegistryObject<Item> sugar_substitute = ITEMS.register("sugar_substitute", Sugar_substitute::new);//一级糖
    public static final RegistryObject<Item> industrial_sugar = ITEMS.register("industrial_sugar", Industrial_sugar::new);//二级糖
    public static final RegistryObject<Item> sugar_pack = ITEMS.register("sugar_pack", Sugar_pack::new);//三级糖
    public static final RegistryObject<Item> sugar_lump = ITEMS.register("sugar_lump", Sugar_lump::new);//四级糖
    public static final RegistryObject<Item> cutting_fluid_solution = ITEMS.register("cutting_fluid_solution", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级切削液
    public static final RegistryObject<Item> compound_cutting_fluid = ITEMS.register("compound_cutting_fluid", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级切削液
    public static final RegistryObject<Item> damaged_device = ITEMS.register("damaged_device", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级装置
    public static final RegistryObject<Item> device = ITEMS.register("device", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级装置
    public static final RegistryObject<Item> integrated_device = ITEMS.register("integrated_device", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//三级装置
    public static final RegistryObject<Item> optimized_device = ITEMS.register("optimized_device",()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//四级装置
    public static final RegistryObject<Item> coagulating_gel = ITEMS.register("coagulating_gel", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级凝胶
    public static final RegistryObject<Item> polymerized_gel = ITEMS.register("polymerized_gel", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级凝胶
    public static final RegistryObject<Item> grindstone = ITEMS.register("grindstone", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级研磨石
    public static final RegistryObject<Item> grindstone_pentahydrate = ITEMS.register("grindstone_pentahydrate", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级研磨石
    public static final RegistryObject<Item> incandescent_alloy = ITEMS.register("incandescent_alloy", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级炽合金
    public static final RegistryObject<Item> incandescent_alloy_block = ITEMS.register("incandescent_alloy_block", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级炽合金
    public static final RegistryObject<Item> loxic_kohl = ITEMS.register("loxic_kohl", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级醇
    public static final RegistryObject<Item> white_horse_kohl = ITEMS.register("white_horse_kohl",()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级醇
    public static final RegistryObject<Item> manganese_ore = ITEMS.register("manganese_ore", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级锰
    public static final RegistryObject<Item> manganese_trihydrate = ITEMS.register("manganese_trihydrate", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级锰
    public static final RegistryObject<Item> orirock = ITEMS.register("orirock", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级源岩
    public static final RegistryObject<Item> orirock_cube = ITEMS.register("orirock_cube", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级源岩
    public static final RegistryObject<Item> orirock_cluster = ITEMS.register("orirock_cluster", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//三级源岩
    public static final RegistryObject<Item> orirock_concentration = ITEMS.register("orirock_concentration", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//四级源岩
    public static final RegistryObject<Item> refined_solvent = ITEMS.register("refined_solvent", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级溶剂
    public static final RegistryObject<Item> semi_synthetic_solvent = ITEMS.register("semi_synthetic_solvent", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级溶剂
    public static final RegistryObject<Item> rma70_12 = ITEMS.register("rma70_12", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//一级海胆
    public static final RegistryObject<Item> rma70_24 = ITEMS.register("rma70_24", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//二级海胆
    public static final RegistryObject<Item> d32_steel = ITEMS.register("d32_steel", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//D32钢
    public static final RegistryObject<Item> bipolar_nanoflake = ITEMS.register("bipolar_nanoflake", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//纳米片
    public static final RegistryObject<Item> polymerization_preparation = ITEMS.register("polymerization_preparation", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraCommonMaterial)));//聚合剂

    //WorldMaterial闭环区物品 注册
    public static final RegistryObject<Item> dt_crystal = ITEMS.register("dt_crystal", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//静谧水晶
    public static final RegistryObject<Item> pau_ore = ITEMS.register("pau_ore",()->new PreBlockItem(BlockRegistry.pau_ore.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//赤金矿石
    public static final RegistryObject<Item> pau_block = ITEMS.register("pau_block",()->new PreBlockItem(BlockRegistry.pau_block.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//赤金矿石
    public static final RegistryObject<Item> pau_ingot = ITEMS.register("pau_ingot", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//赤金锭
    public static final RegistryObject<Item> pau_nugget = ITEMS.register("pau_nugget", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//赤金粒
    public static final RegistryObject<Item> pau_fluorescence_glass = ITEMS.register("pau_fluorescence_glass",()->new BlockItem(BlockRegistry.pau_fluorescence_glass.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//赤金辉光玻璃
    public static final RegistryObject<Item> e_t_experiment_data = ITEMS.register("e_t_experiment_data", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//末地-泰拉折跃实验数据
    public static final RegistryObject<Item> e_t_theory_data = ITEMS.register("e_t_theory_data", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//末地-泰拉折跃理论数据
    public static final RegistryObject<Item> common_food_bag = ITEMS.register("common_food_bag", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//普通食物包
    public static final RegistryObject<Item> blue_crystal = ITEMS.register("blue_crystal",()->new PreBlockItem(BlockRegistry.blue_crystal.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//赤金辉光玻璃
    //    public static final RegistryObject<Item> blaze_rod_plant = ITEMS.register("blaze_rod_plant",()->new PreBlockItem(BlockRegistry.blaze_rod_plant.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//烈焰苗
    public static final RegistryObject<Item> common_food_box = ITEMS.register("common_food_box",()->new PreBlockItem(BlockRegistry.common_food_box.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//普通食物盒子
    public static final RegistryObject<Item> blackstone_lamp = ITEMS.register("blackstone_lamp",()->new PreBlockItem(BlockRegistry.blackstone_lamp.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//黑石灯
    public static final RegistryObject<Item> blackstone_sand = ITEMS.register("blackstone_sand",()->new PreBlockItem(BlockRegistry.blackstone_sand.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//黑石沙
    public static final RegistryObject<Item> blackstone_medical_point = ITEMS.register("blackstone_medical_point",()->new PreBlockItem(BlockRegistry.blackstone_medical_point.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//黑石沙
    public static final RegistryObject<Item> hang_screened_table = ITEMS.register("hang_screened_table",()->new PreBlockItem(BlockRegistry.hang_screened_table.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//简易筛矿台
    public static final RegistryObject<Item> netherite_nugget = ITEMS.register("netherite_nugget", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//下届合金粒
//    public static final RegistryObject<Item> oitest_item = ITEMS.register("oitest_item", ()->new PreOIItem(new Item.Properties().tab(ModGroupRegistry.worldMaterial),false,2,3,0,2,0));//测试物品
    public static final RegistryObject<Item> c_originium_block = ITEMS.register("c_originium_block", ()->new PreOIBlockItem(BlockRegistry.c_originium_block.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial),false,2,2,0,5,2));//c型活性源石
    public static final RegistryObject<Item> d_originium_block = ITEMS.register("d_originium_block", ()->new PreOIBlockItem(BlockRegistry.d_originium_block.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial),false,2,2,0,3,0));//d型失活源石
    public static final RegistryObject<Item> l1_oir_vaccine_a = ITEMS.register("l1_oir_vaccine_a", ()->new OIRVaccineItem(false,1,0.8F));//1-1源石疫苗
    public static final RegistryObject<Item> l2_oir_vaccine_a = ITEMS.register("l2_oir_vaccine_a", ()->new OIRVaccineItem(false,2,1.2F));//2-1源石疫苗
    public static final RegistryObject<Item> broken_ender_energy_radar = ITEMS.register("broken_ender_energy_radar", ()->new EnderEnergyRadar(false));//损坏末影雷达
    public static final RegistryObject<Item> ender_energy_radar = ITEMS.register("ender_energy_radar", ()->new EnderEnergyRadar(true));//末影雷达


    //TerraTools泰拉工具工具 注册
    public static final RegistryObject<Item> unnamed_sword = ITEMS.register("unnamed_sword", ()->new SwordItem(MaterialTier.PURE_GOLD, 24, -1.6F, new Item.Properties().fireResistant().tab(ModGroupRegistry.terraTools)));//不知名的刀
    public static final RegistryObject<Item> pau_epee = ITEMS.register("pau_epee", ()->new SwordItem(MaterialTier.PURE_GOLD, 10, -3.4F, new Item.Properties().tab(ModGroupRegistry.terraTools)));//赤金重剑
    public static final RegistryObject<Item> pau_sword = ITEMS.register("pau_sword", ()->new SwordItem(MaterialTier.PURE_GOLD, 2, -2.2F, new Item.Properties().tab(ModGroupRegistry.terraTools)));//赤金剑
    public static final RegistryObject<Item> pau_pickaxe = ITEMS.register("pau_pickaxe", ()->new PickaxeItem(MaterialTier.PURE_GOLD, -1, -2.8F, new Item.Properties().tab(ModGroupRegistry.terraTools)));//赤金镐
    public static final RegistryObject<Item> pau_axe = ITEMS.register("pau_axe", ()->new AxeItem(MaterialTier.PURE_GOLD, 3, -3, new Item.Properties().tab(ModGroupRegistry.terraTools)));//赤金斧
    public static final RegistryObject<Item> pau_hoe = ITEMS.register("pau_hoe", ()->new HoeItem(MaterialTier.PURE_GOLD, -4, -1.5F, new Item.Properties().tab(ModGroupRegistry.terraTools)));//赤金锄
    public static final RegistryObject<Item> pau_shovel = ITEMS.register("pau_shovel", ()->new ShovelItem(MaterialTier.PURE_GOLD, -2, -2.1F, new Item.Properties().tab(ModGroupRegistry.terraTools)));//赤金铲

    //TerraEnergy泰拉能源 注册
    public static final RegistryObject<Item> e_oir_reactor_control_board = ITEMS.register("e_oir_reactor_control_board",()->new PreBlockItem(BlockRegistry.e_oir_reactor_control_board.get(), new Item.Properties().tab(ModGroupRegistry.terraEnergy)));//E类源石反应堆控制面板

    //TerraIndustrialMaterial泰拉工业材料 注册
    public static final RegistryObject<Item> compressed_iron_ingot = ITEMS.register("compressed_iron_ingot", ()->new PreItem(new Item.Properties().tab(ModGroupRegistry.terraIndustrialMaterial)));//压缩铁
    public static final RegistryObject<Item> iron_structure_frame = ITEMS.register("iron_structure_frame",()->new PreBlockItem(BlockRegistry.iron_structure_frame.get(), new Item.Properties().tab(ModGroupRegistry.terraIndustrialMaterial)));//铁质结构框架
    public static final RegistryObject<Item> iron_structure_frame_creator = ITEMS.register("iron_structure_frame_creator",()->new PreBlockItem(BlockRegistry.iron_structure_frame_creator.get(), new Item.Properties().tab(ModGroupRegistry.terraIndustrialMaterial)));//铁质结构框架搭建器
//    public static final RegistryObject<Item> projection_block = ITEMS.register("projection_block",()->new PreBlockItem(BlockRegistry.projection_block.get(), new Item.Properties().tab(ModGroupRegistry.terraIndustrialMaterial)));//投影方块

    //TerraTechMaterial泰拉科技材料 注册
    public static final RegistryObject<Item> muddy_silicon = ITEMS.register("muddy_silicon",()->new PreItem(new Item.Properties()));

    //TerraBlueprintItem泰拉蓝图物品 注册（暂时归于工业）
    public static final RegistryObject<Item> l1_empty_blueprint = ITEMS.register("l1_empty_blueprint",()->new BaseBlueprintItem(false,1,1));
    public static final RegistryObject<Item> l1_blank_blueprint = ITEMS.register("l1_blank_blueprint",()->new BaseBlueprintItem(false,1,0));


    //辅助性方块注册
    public static final RegistryObject<Item> chaotic_air = ITEMS.register("chaotic_air",()->new PreBlockItem(BlockRegistry.chaotic_air.get(), new Item.Properties()));

//    //test
//    public static final RegistryObject<Item> test_block = ITEMS.register("test_block",()->new BlockItem(BlockRegistry.test_block.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块
//    public static final RegistryObject<Item> test_block_b = ITEMS.register("test_block_b",()->new BlockItem(BlockRegistry.test_block_b.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块
//    public static final RegistryObject<Item> test_block_c = ITEMS.register("test_block_c",()->new BlockItem(BlockRegistry.test_block_c.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块
//    public static final RegistryObject<Item> test_block_d = ITEMS.register("test_block_d",()->new BlockItem(BlockRegistry.test_block_d.get(), new Item.Properties().tab(ModGroupRegistry.worldMaterial)));//测试方块

}
