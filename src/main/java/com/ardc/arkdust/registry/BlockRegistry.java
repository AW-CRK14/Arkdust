package com.ardc.arkdust.registry;

import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBlock;
import com.ardc.arkdust.preobject.Block.*;
import com.ardc.arkdust.preobject.BlockState.FaceCullingWhenSameBlock;
import com.ardc.arkdust.preobject.pre.OIItem.PreOIBlock;
import com.ardc.arkdust.Items.blocks.*;
import com.ardc.arkdust.preobject.Block.terra_industrial.StructureFrameBlock;
import com.ardc.arkdust.preobject.pre.PreBlock;
import com.ardc.arkdust.Items.blocks.ores.Pau_ore;
import com.ardc.arkdust.Items.blocks.terra_energy.E_oir_reactor_control_board;
import com.ardc.arkdust.Enums.TechMaterial;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.model.modelblock.LifeBlockModel;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);

    //闭环区方块
    public static final RegistryObject<Block> pau_ore = BLOCKS.register("pau_ore", Pau_ore::new);//赤金矿石
    public static final RegistryObject<Block> pau_block = BLOCKS.register("pau_block", ()->new PreBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(3,6).harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops()));//赤金块
    public static final RegistryObject<Block> pau_fluorescence_glass = BLOCKS.register("pau_fluorescence_glass", ()->new HardGlassBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(2,15).lightLevel((level)->12).noOcclusion()));//赤金光辉玻璃
    public static final RegistryObject<Block> blue_crystal = BLOCKS.register("blue_crystal", ()->new ArkCrystalBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(2,2).noOcclusion()));//蓝水晶
    public static final RegistryObject<Block> blaze_rod_plant = BLOCKS.register("blaze_rod_plant", Blaze_rod_plant::new);//
    public static final RegistryObject<Block> common_food_box = BLOCKS.register("common_food_box", ()->new PreBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).strength(3)));//普通食物盒子
    public static final RegistryObject<Block> blackstone_lamp = BLOCKS.register("blackstone_lamp", BlackstoneLamp::new);//黑石灯
    public static final RegistryObject<Block> blackstone_sand = BLOCKS.register("blackstone_sand", ()->new GravelBlock(AbstractBlock.Properties.of(Material.SAND, MaterialColor.STONE).harvestTool(ToolType.SHOVEL).strength(1)));//黑石沙砾
    public static final RegistryObject<Block> hang_screened_table = BLOCKS.register("hang_screened_table", HangScreenedTable::new);//简易筛矿台
    public static final RegistryObject<Block> blackstone_medical_point = BLOCKS.register("blackstone_medical_point", BlackstoneMedicalPoint::new);//黑石基座
    public static final RegistryObject<Block> c_originium_block = BLOCKS.register("c_originium_block", ()->new CreepOIBlock(AbstractBlock.Properties.of(Material.METAL).randomTicks().sound(SoundType.METAL).strength(1,2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops(),2,3,3,true));//c源石
    public static final RegistryObject<Block> d_originium_block = BLOCKS.register("d_originium_block", ()->new PreOIBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(2,5).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops(),1,0,2));//d源石
    public static final RegistryObject<Block> sit_white_skeleton_block = BLOCKS.register("sit_white_skeleton_block", ()->new SkeletonBlock(SkeletonBlock.State.STAND,false));//坐地白色骷髅
    public static final RegistryObject<Block> lay0_white_skeleton_block = BLOCKS.register("lay0_white_skeleton_block", ()->new SkeletonBlock(SkeletonBlock.State.LAY,false));//白色骨架
//    public static final RegistryObject<Block> tombstone_1_1 = BLOCKS.register("tombstone_1_1", ()->new TombstoneBlock(false));//墓碑方块1-1
    public static final RegistryObject<Block> tombstone_2_1 = BLOCKS.register("tombstone_2_1", ()->new TombstoneBlock(true,VoxelShapes.box(0,0,0.375F,1,0.75F,0.625F), VoxelShapes.box(0.375F,0,0,0.75F,0.875F,1)));//墓碑方块2-1
    public static final RegistryObject<Block> tombstone_2_2 = BLOCKS.register("tombstone_2_2", ()->new TombstoneBlock(true,VoxelShapes.box(0,0,0.125F,1,0.625F,0.875F), VoxelShapes.box(0.125F,0,0,0.625F,0.875F,1)));//墓碑方块2-2
    public static final RegistryObject<Block> tombstone_2_3 = BLOCKS.register("tombstone_2_3", ()->new TombstoneBlock(true,VoxelShapes.box(0.125F,0,0.25F,0.875F,0.375F,0.75F), VoxelShapes.box(0.25F,0,0.125F,0.75F,0.375F,0.875F)));//墓碑方块2-3
    public static final RegistryObject<Block> red_caution_light = BLOCKS.register("red_caution_light", CautionLight::new);//红色警示灯

    //泰拉能源
    public static final RegistryObject<Block> e_oir_reactor_control_board = BLOCKS.register("e_oir_reactor_control_board", E_oir_reactor_control_board::new);//E类源石反应堆控制方块

    //泰拉工业
    public static final RegistryObject<Block> iron_structure_frame = BLOCKS.register("iron_structure_frame",()->new StructureFrameBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(5,40).noOcclusion().harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops(), TechMaterial.C_IRON));
    public static final RegistryObject<Block> iron_structure_frame_creator = BLOCKS.register("iron_structure_frame_creator", Iron_sfblock_creator::new);

    //特殊方块
    public static final RegistryObject<Block> life_model = BLOCKS.register("life_model", LifeBlockModel::new);
    public static final RegistryObject<Block> chaotic_air = BLOCKS.register("chaotic_air", ()->new StructureAirBlock(AbstractBlock.Properties.of(com.ardc.arkdust.CodeMigration.Material.STATIC_STRUCTURE_AIR).noCollission()));
    public static final RegistryObject<Block> structure_ignore_block = BLOCKS.register("structure_ignore_block", ()->new FaceCullingWhenSameBlock(AbstractBlock.Properties.of(com.ardc.arkdust.CodeMigration.Material.STATIC_STRUCTURE_AIR).noCollission()));
    public static final RegistryObject<Block> story_point = BLOCKS.register("story_point", StoryPointBlock::new);
//    public static final RegistryObject<Block> projection_block = BLOCKS.register("projection_block", ProjectionBlock::new);


//    //test
    public static final RegistryObject<Block> test_block = BLOCKS.register("test_block", Test_block::new);//测试方块
    public static final RegistryObject<Block> test_block_b = BLOCKS.register("test_block_b", Test_block_b::new);//测试方块
//    public static final RegistryObject<Block> test_block_c = BLOCKS.register("test_block_c", Test_block_c::new);//测试方块
//    public static final RegistryObject<Block> test_block_d = BLOCKS.register("test_block_d", Test_block_d::new);//测试方块

}
