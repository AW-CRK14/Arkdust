package com.ardc.arkdust;

import com.ardc.arkdust.CodeMigration.Block.ArkCrystalBlock;
import com.ardc.arkdust.CodeMigration.Block.HardGlassBlock;
import com.ardc.arkdust.CodeMigration.Block.terra_industrial.SFBlockCreator;
import com.ardc.arkdust.CodeMigration.Block.terra_industrial.StructureFrameBlock;
import com.ardc.arkdust.CodeMigration.pre.PreBlock;
import com.ardc.arkdust.items.blocks.Blaze_rod_plant;
import com.ardc.arkdust.items.blocks.ores.Pau_ore;
import com.ardc.arkdust.items.blocks.terra_energy.E_oir_reactor_control_board;
import com.ardc.arkdust.type.TechMaterial;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,Utils.MOD_ID);

    //闭环区方块
    public static final RegistryObject<Block> pau_ore = BLOCKS.register("pau_ore", Pau_ore::new);//赤金矿石
    public static final RegistryObject<Block> pau_fluorescence_glass = BLOCKS.register("pau_fluorescence_glass", ()->new HardGlassBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(2,25).lightLevel((level)->12).noOcclusion()));//赤金光辉玻璃
    public static final RegistryObject<Block> blue_crystal = BLOCKS.register("blue_crystal", ()->new ArkCrystalBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(2,25).noOcclusion()));//蓝水晶
    public static final RegistryObject<Block> blaze_rod_plant = BLOCKS.register("blaze_rod_plant", Blaze_rod_plant::new);//普通食物盒子
    public static final RegistryObject<Block> common_food_box = BLOCKS.register("common_food_box", ()->new PreBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).strength(3)));//普通食物盒子

    //泰拉能源
    public static final RegistryObject<Block> e_oir_reactor_control_board = BLOCKS.register("e_oir_reactor_control_board", E_oir_reactor_control_board::new);//E类源石反应堆控制方块

    //泰拉工业
    public static final RegistryObject<Block> iron_structure_frame = BLOCKS.register("iron_structure_frame",()->new StructureFrameBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(5,40).noOcclusion().harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops(), TechMaterial.C_IRON));
    public static final RegistryObject<Block> iron_structure_frame_creator = BLOCKS.register("iron_structure_frame_creator",()->new SFBlockCreator(TechMaterial.C_IRON,BlockRegistry.iron_structure_frame.get().asItem()));


//    //test
//    public static final RegistryObject<Block> test_block = BLOCKS.register("test_block", Test_block::new);//测试方块
//    public static final RegistryObject<Block> test_block_b = BLOCKS.register("test_block_b", Test_block_b::new);//测试方块
//    public static final RegistryObject<Block> test_block_c = BLOCKS.register("test_block_c", Test_block_c::new);//测试方块
//    public static final RegistryObject<Block> test_block_d = BLOCKS.register("test_block_d", Test_block_d::new);//测试方块

}
