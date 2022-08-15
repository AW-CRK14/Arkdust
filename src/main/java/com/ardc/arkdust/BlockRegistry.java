package com.ardc.arkdust;

import com.ardc.arkdust.CodeMigration.Block.ArkCrystalBlock;
import com.ardc.arkdust.CodeMigration.Block.HardGlassBlock;
import com.ardc.arkdust.Items.blocks.*;
import com.ardc.arkdust.CodeMigration.Block.terra_industrial.StructureFrameBlock;
import com.ardc.arkdust.CodeMigration.pre.PreBlock;
import com.ardc.arkdust.Items.blocks.ores.Pau_ore;
import com.ardc.arkdust.Items.blocks.terra_energy.E_oir_reactor_control_board;
import com.ardc.arkdust.Enums.TechMaterial;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,Utils.MOD_ID);

    //�ջ�������
    public static final RegistryObject<Block> pau_ore = BLOCKS.register("pau_ore", Pau_ore::new);//����ʯ
    public static final RegistryObject<Block> pau_block = BLOCKS.register("pau_block", ()->new PreBlock(AbstractBlock.Properties.of(Material.METAL).sound(SoundType.METAL).strength(3,6).harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops()));//����
    public static final RegistryObject<Block> pau_fluorescence_glass = BLOCKS.register("pau_fluorescence_glass", ()->new HardGlassBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(2,15).lightLevel((level)->12).noOcclusion()));//����Բ���
    public static final RegistryObject<Block> blue_crystal = BLOCKS.register("blue_crystal", ()->new ArkCrystalBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(2,2).noOcclusion()));//��ˮ��
    public static final RegistryObject<Block> blaze_rod_plant = BLOCKS.register("blaze_rod_plant", Blaze_rod_plant::new);//
    public static final RegistryObject<Block> common_food_box = BLOCKS.register("common_food_box", ()->new PreBlock(AbstractBlock.Properties.of(Material.WOOD).harvestTool(ToolType.AXE).strength(3)));//��ͨʳ�����
    public static final RegistryObject<Block> blackstone_lamp = BLOCKS.register("blackstone_lamp", Blackstone_lamp::new);//��ʯ��
    public static final RegistryObject<Block> blackstone_sand = BLOCKS.register("blackstone_sand", ()->new GravelBlock(AbstractBlock.Properties.of(Material.SAND, MaterialColor.STONE).harvestTool(ToolType.SHOVEL).strength(1)));//��ʯɳ��
    public static final RegistryObject<Block> hang_screened_table = BLOCKS.register("hang_screened_table", Hand_screened_table::new);//����ɸ��̨
    public static final RegistryObject<Block> blackstone_medical_point = BLOCKS.register("blackstone_medical_point", BlackstoneMedicalPoint::new);//��ʯ����

    //̩����Դ
    public static final RegistryObject<Block> e_oir_reactor_control_board = BLOCKS.register("e_oir_reactor_control_board", E_oir_reactor_control_board::new);//E��Դʯ��Ӧ�ѿ��Ʒ���

    //̩����ҵ
    public static final RegistryObject<Block> iron_structure_frame = BLOCKS.register("iron_structure_frame",()->new StructureFrameBlock(AbstractBlock.Properties.of(Material.HEAVY_METAL).strength(5,40).noOcclusion().harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops(), TechMaterial.C_IRON));
    public static final RegistryObject<Block> iron_structure_frame_creator = BLOCKS.register("iron_structure_frame_creator", Iron_sfblock_creator::new);


//    //test
//    public static final RegistryObject<Block> test_block = BLOCKS.register("test_block", Test_block::new);//���Է���
//    public static final RegistryObject<Block> test_block_b = BLOCKS.register("test_block_b", Test_block_b::new);//���Է���
//    public static final RegistryObject<Block> test_block_c = BLOCKS.register("test_block_c", Test_block_c::new);//���Է���
//    public static final RegistryObject<Block> test_block_d = BLOCKS.register("test_block_d", Test_block_d::new);//���Է���

}
