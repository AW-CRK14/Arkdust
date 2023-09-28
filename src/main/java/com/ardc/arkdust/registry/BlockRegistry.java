package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.blocks.StructureAirBlock;
import com.ardc.arkdust.blocks.Test_block;
import com.ardc.arkdust.blocks.Test_block_b;
import com.ardc.arkdust.blocks.cworld.*;
import com.ardc.arkdust.blocks.ores.PauOre;
import com.ardc.arkdust.blocks.orirock.CreepOIBlock;
import com.ardc.arkdust.blocks.terra_industrial.StructureFrameBlock;
import com.ardc.arkdust.blockstate.DropSelfBlock;
import com.ardc.arkdust.blockstate.DropSelfWhenSilkTouchBlock;
import com.ardc.arkdust.blockstate.FaceCullingWhenSameBlock;
import com.ardc.arkdust.blockstate.ThreeAxisBlock;
import com.ardc.arkdust.enums.TechMaterial;
import com.ardc.arkdust.model.modelblock.LifeBlockModel;
import com.ardc.arkdust.playmethod.oi.OIItem.PreOIBlock;
import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBlock;
import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);

    //闭环区方块
    public static final RegistryObject<Block> pau_ore = BLOCKS.register("pau_ore", PauOre::new);//赤金矿石
    public static final RegistryObject<Block> pau_block = BLOCKS.register("pau_block", ()->new PreBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).strength(3,6).requiresCorrectToolForDrops()));//赤金块
    public static final RegistryObject<Block> pau_fluorescence_glass = BLOCKS.register("pau_fluorescence_glass", ()->new HardGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).strength(2,15).lightLevel((level)->12).noOcclusion()));//赤金光辉玻璃
//    public static final RegistryObject<Block> blue_crystal = BLOCKS.register("blue_crystal", ()->new ArkCrystalBlock(AbstractBlock.Properties.of(Material.GLASS).sound(SoundType.GLASS).strength(2,2).noOcclusion()));//蓝水晶
//    public static final RegistryObject<Block> blaze_rod_plant = BLOCKS.register("blaze_rod_plant", Blaze_rod_plant::new);//
    public static final RegistryObject<Block> common_food_box = BLOCKS.register("common_food_box", ()->new PreBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).strength(3)));//普通食物盒子
    public static final RegistryObject<Block> blackstone_lamp = BLOCKS.register("blackstone_lamp", BlackstoneLamp::new);//黑石灯
    public static final RegistryObject<Block> blackstone_sand = BLOCKS.register("blackstone_sand", ()->new GravelBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL).strength(1)));//黑石沙砾
    public static final RegistryObject<Block> hang_screened_table = BLOCKS.register("hang_screened_table", HangScreenedTable::new);//简易筛矿台
    public static final RegistryObject<Block> blackstone_medical_point = BLOCKS.register("blackstone_medical_point", BlackstoneMedicalPoint::new);//黑石医疗点
    public static final RegistryObject<Block> c_originium_block = BLOCKS.register("c_originium_block", ()->new CreepOIBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE).randomTicks().strength(1,2).requiresCorrectToolForDrops(),2,3,3,true));//c源石
    public static final RegistryObject<Block> d_originium_block = BLOCKS.register("d_originium_block", ()->new PreOIBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE).randomTicks().strength(2,3).requiresCorrectToolForDrops(),1,0,2));//d源石
    public static final RegistryObject<Block> sit_white_skeleton_block = BLOCKS.register("sit_white_skeleton_block", ()->new SkeletonBlock(SkeletonBlock.State.STAND,false));//坐地白色骷髅
    public static final RegistryObject<Block> lay0_white_skeleton_block = BLOCKS.register("lay0_white_skeleton_block", ()->new SkeletonBlock(SkeletonBlock.State.LAY,false));//白色骨架
//    public static final RegistryObject<Block> tombstone_1_1 = BLOCKS.register("tombstone_1_1", ()->new TombstoneBlock(false));//墓碑方块1-1
    public static final RegistryObject<Block> tombstone_2_1 = BLOCKS.register("tombstone_2_1", ()->new TombstoneBlock(true, Shapes.box(0,0,0.375F,1,0.75F,0.625F), Shapes.box(0.375F,0,0,0.75F,0.875F,1)));//墓碑方块2-1
    public static final RegistryObject<Block> tombstone_2_2 = BLOCKS.register("tombstone_2_2", ()->new TombstoneBlock(true,Shapes.box(0,0,0.125F,1,0.625F,0.875F), Shapes.box(0.125F,0,0,0.625F,0.875F,1)));//墓碑方块2-2
    public static final RegistryObject<Block> tombstone_2_3 = BLOCKS.register("tombstone_2_3", ()->new TombstoneBlock(true,Shapes.box(0.125F,0,0.25F,0.875F,0.375F,0.75F), Shapes.box(0.25F,0,0.125F,0.75F,0.375F,0.875F)));//墓碑方块2-3
    public static final RegistryObject<Block> red_caution_light = BLOCKS.register("red_caution_light", CautionLight::new);//红色警示灯
    public static final RegistryObject<Block> c7lab_strock = registerDefaultC7LabStrock("c7lab_strock",true);
    public static final RegistryObject<Block> c7lab_strock_ground_1 = registerDefaultC7LabStrock("c7lab_strock_ground_1",true);
    public static final RegistryObject<Block> c7lab_strock_ground_2 = registerDefaultC7LabStrock("c7lab_strock_ground_2",true);
    public static final RegistryObject<Block> c7lab_strock_ground_3 = registerDefaultC7LabStrock("c7lab_strock_ground_3",true);
    public static final RegistryObject<Block> c7lab_strock_ground_4 = registerDefaultC7LabStrock("c7lab_strock_ground_4",true);
    public static final RegistryObject<Block> c7lab_strock_ground_5 = registerDefaultC7LabStrock("c7lab_strock_ground_5",true);
    public static final RegistryObject<Block> c7lab_strock_ceiling_1 = registerDefaultC7LabStrock("c7lab_strock_ceiling_1",true);
    public static final RegistryObject<Block> c7lab_strock_ceiling_2 = registerDefaultC7LabStrock("c7lab_strock_ceiling_2",true);
    public static final RegistryObject<Block> c7lab_strock_pillar_1 = registerDefaultC7LabStrock("c7lab_strock_pillar_1",true);
    public static final RegistryObject<Block> c7lab_strock_pillar_2 = registerDefaultC7LabStrock("c7lab_strock_pillar_2",true);
    public static final RegistryObject<Block> c7lab_strock_wall_1 = registerDefaultC7LabStrock("c7lab_strock_wall_1",true);
    public static final RegistryObject<Block> c7lab_strock_wall_2 = registerDefaultC7LabStrock("c7lab_strock_wall_2",true);
    public static final RegistryObject<Block> c7lab_strock_wall_3 = registerDefaultC7LabStrock("c7lab_strock_wall_3",true);
    public static final RegistryObject<Block> c7lab_strock_ceiling_lighting = BLOCKS.register("c7lab_strock_ceiling_lighting",()->new C7LabStructureBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).lightLevel((i)->12),false,false));
    public static final RegistryObject<Block> c7lab_strock_glass_1 = BLOCKS.register("c7lab_strock_glass_1",()->new C7LabStructureBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).noOcclusion(),false,true));
    public static final RegistryObject<Block> c7lab_strock_glass_2 = BLOCKS.register("c7lab_strock_glass_2",()->new C7LabStructureBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).noOcclusion(),true,true));
    public static final RegistryObject<Block> rushed_iron_mineshaft_board = BLOCKS.register("rushed_iron_mineshaft_board",()->new MineshaftBoard(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).noOcclusion().strength(2).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> rushed_iron_mineshaft_net = BLOCKS.register("rushed_iron_mineshaft_net",()->new MineshaftBoard(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).noOcclusion().strength(2).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> rushed_iron_block = BLOCKS.register("rushed_iron_block",()->new DropSelfBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3)));
    public static final RegistryObject<Block> dark_rushed_iron_block = BLOCKS.register("dark_rushed_iron_block",()->new DropSelfBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3)));
    public static final RegistryObject<Block> red_paint_rushed_iron_block = BLOCKS.register("red_paint_rushed_iron_block",()->new DropSelfBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().strength(3)));
    public static final RegistryObject<Block> broken_rushed_iron_mineshaft_net = BLOCKS.register("broken_rushed_iron_mineshaft_net",()->new MineshaftBoard(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noCollission().strength(2).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> wasteland_mineshaft_spandrel_girder = BLOCKS.register("wasteland_mineshaft_spandrel_girder",()->new ThreeAxisBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE).strength(7,12).requiresCorrectToolForDrops(),true));
    public static final RegistryObject<Block> dirty_concrete = BLOCKS.register("dirty_concrete",()->new DropSelfBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE).strength(3).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> dirty_concrete_powder = BLOCKS.register("dirty_concrete_powder",()->new ConcretePowderBlock(dirty_concrete.get(),BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE_POWDER).strength(2)));
    public static final RegistryObject<Block> oried_dirty_concrete = BLOCKS.register("oried_dirty_concrete",()->new DropSelfBlock(BlockBehaviour.Properties.copy(dirty_concrete_powder.get()).strength(2),1,()->dirty_concrete_powder.get().asItem()));
    public static final RegistryObject<Block> cracked_dirty_concrete = BLOCKS.register("cracked_dirty_concrete",()->new DropSelfWhenSilkTouchBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE).strength(2),(c)->new ItemStack(dirty_concrete_powder.get().asItem())));
//    public static final RegistryObject<Block> radiant_crystal_ore = BLOCKS.register("radiant_crystal_ore",()->new DropSelfWhenSilkTouchBlock(AbstractBlock.Properties.of(Material.STONE).harvestTool(ToolType.PICKAXE).strength(11).harvestLevel(3).requiresCorrectToolForDrops(),(c)->new ItemStack(ItemRegistry.radiant_crystal_shard.get(),2+ LootHelper.getEnchantment(c, Enchantments.BLOCK_FORTUNE))));
    public static final RegistryObject<Block> basic_blueprint_reduce_box = BLOCKS.register("basic_blueprint_reduce_box", CWorldBlueprintReduceBox::new);
    public static final RegistryObject<Block> stone_oak_log = BLOCKS.register("stone_oak_log", ()->new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2.5F)));
    public static final RegistryObject<Block> stone_oak_wood = BLOCKS.register("stone_oak_wood", ()->new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2.5F)));
    public static final RegistryObject<Block> stone_oak_plank = BLOCKS.register("stone_oak_plank", ()->new PreBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2.5F)));
    public static final RegistryObject<Block> fractured_oak_log = BLOCKS.register("fractured_oak_log", ()->new FracturedOakLog(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(2.0F).noOcclusion(),true));
    public static final RegistryObject<Block> mycelium_pile = BLOCKS.register("mycelium_pile", ()->new BoardShapeBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL).instabreak().noCollission()));
    public static final RegistryObject<Block> se_rcu_machine = BLOCKS.register("se_rcu_machine", SeRcuMachine::new);



    //泰拉能源
//    public static final RegistryObject<Block> e_oir_reactor_control_board = BLOCKS.register("e_oir_reactor_control_board", EOirReactorControlBoard::new);//E类源石反应堆控制方块

    //泰拉工业
    public static final RegistryObject<Block> iron_structure_frame = BLOCKS.register("iron_structure_frame",()->new StructureFrameBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5,40).noOcclusion().requiresCorrectToolForDrops().lightLevel((i)->6), TechMaterial.C_IRON));
    public static final RegistryObject<Block> iron_structure_frame_creator = BLOCKS.register("iron_structure_frame_creator", IronSfblockCreator::new);

    //特殊方块
    public static final RegistryObject<Block> life_model = BLOCKS.register("life_model", LifeBlockModel::new);
    public static final RegistryObject<Block> chaotic_air = BLOCKS.register("chaotic_air", ()->new StructureAirBlock(BlockBehaviour.Properties.copy(Blocks.AIR)));
    public static final RegistryObject<Block> structure_ignore_block = BLOCKS.register("structure_ignore_block", ()->new FaceCullingWhenSameBlock(BlockBehaviour.Properties.copy(Blocks.AIR)));
    public static final RegistryObject<Block> story_point = BLOCKS.register("story_point", StoryPointBlock::new);
//    public static final RegistryObject<Block> projection_block = BLOCKS.register("projection_block", ProjectionBlock::new);


    //test
    public static final RegistryObject<Block> test_block = BLOCKS.register("test_block", Test_block::new);//测试方块
    public static final RegistryObject<Block> test_block_b = BLOCKS.register("test_block_b", Test_block_b::new);//测试方块
//    public static final RegistryObject<Block> test_block_c = BLOCKS.register("test_block_c", Test_block_c::new);//测试方块
//    public static final RegistryObject<Block> test_block_d = BLOCKS.register("test_block_d", Test_block_d::new);//测试方块

    private static RegistryObject<Block> registerDefaultC7LabStrock(String name, boolean unbroken){
        return BLOCKS.register(name,()->new C7LabStructureBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).lightLevel((i)->3),unbroken,false));
    }
}
