package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.block_entity.*;
import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Utils.MOD_ID);

    public static final RegistryObject<BlockEntityType<SFBlockCreatorBE>> SF_BLOCK_CREATOR_BE = TE.register("structure_frame_block_creator",()->BlockEntityType.Builder.of(SFBlockCreatorBE::new,BlockRegistry.iron_structure_frame_creator.get()).build(null));
    public static final RegistryObject<BlockEntityType<ScreenedTableBE>> SCREENED_TABLE_BE = TE.register("screened_table",()->BlockEntityType.Builder.of(ScreenedTableBE::new,BlockRegistry.hang_screened_table.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlackstoneMedicalPointBE>> BLACKSTONE_MEDICAL_POINT_BE = TE.register("blackstone_medical_point",()->BlockEntityType.Builder.of(BlackstoneMedicalPointBE::new,BlockRegistry.blackstone_medical_point.get()).build(null));
    public static final RegistryObject<BlockEntityType<StoryPointBE>> STORY_POINT_BE = TE.register("story_point",()->BlockEntityType.Builder.of(StoryPointBE::new,BlockRegistry.story_point.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlueprintReduceBoxBE>> BASIC_BLUEPRINT_REDUCE_BOX_BE = TE.register("basic_blueprint_reduce_box",()->BlockEntityType.Builder.of((pos,state)->new BlueprintReduceBoxBE(TileEntityTypeRegistry.BASIC_BLUEPRINT_REDUCE_BOX_BE.get(),pos,state,32),BlockRegistry.basic_blueprint_reduce_box.get()).build(null));
    public static final RegistryObject<BlockEntityType<OERIMachineBE>> SE_RCU_MACHINE_BE = TE.register("se_rce_machine_be",()->BlockEntityType.Builder.of(OERIMachineBE::new,BlockRegistry.se_rcu_machine.get()).build(null));
//    public static final RegistryObject<TileEntityType<ProjectionBlockBE>> aProjectionBE = TE.register("projection",()->TileEntityType.Builder.of(ProjectionBlockBE::new,BlockRegistry.blackstone_medical_point.get()).build(null));
}
