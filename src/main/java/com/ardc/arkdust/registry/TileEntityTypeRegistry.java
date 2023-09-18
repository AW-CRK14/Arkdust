package com.ardc.arkdust.registry;

import com.ardc.arkdust.block_entity.*;
import com.ardc.arkdust.playmethod.story.blockanditem.StoryPointBE;
import com.ardc.arkdust.Utils;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<TileEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Utils.MOD_ID);

    public static final RegistryObject<TileEntityType<SFBlockCreatorBE>> SF_BLOCK_CREATOR_BE = TE.register("structure_frame_block_creator",()->TileEntityType.Builder.of(SFBlockCreatorBE::new,BlockRegistry.iron_structure_frame_creator.get()).build(null));
    public static final RegistryObject<TileEntityType<ScreenedTableBE>> SCREENED_TABLE_BE = TE.register("screened_table",()->TileEntityType.Builder.of(ScreenedTableBE::new,BlockRegistry.hang_screened_table.get()).build(null));
    public static final RegistryObject<TileEntityType<BlackstoneMedicalPointBE>> BLACKSTONE_MEDICAL_POINT_BE = TE.register("blackstone_medical_point",()->TileEntityType.Builder.of(BlackstoneMedicalPointBE::new,BlockRegistry.blackstone_medical_point.get()).build(null));
    public static final RegistryObject<TileEntityType<StoryPointBE>> STORY_POINT_BE = TE.register("story_point",()->TileEntityType.Builder.of(StoryPointBE::new,BlockRegistry.story_point.get()).build(null));
    public static final RegistryObject<TileEntityType<BlueprintReduceBoxBE>> BASIC_BLUEPRINT_REDUCE_BOX_BE = TE.register("basic_blueprint_reduce_box",()->TileEntityType.Builder.of(()->new BlueprintReduceBoxBE(TileEntityTypeRegistry.BASIC_BLUEPRINT_REDUCE_BOX_BE.get(),32),BlockRegistry.basic_blueprint_reduce_box.get()).build(null));
    public static final RegistryObject<TileEntityType<SeRcuMachineBE>> SE_RCU_MACHINE_BE = TE.register("se_rce_machine_be",()->TileEntityType.Builder.of(SeRcuMachineBE::new,BlockRegistry.se_rcu_machine.get()).build(null));
//    public static final RegistryObject<TileEntityType<ProjectionBlockBE>> aProjectionBE = TE.register("projection",()->TileEntityType.Builder.of(ProjectionBlockBE::new,BlockRegistry.blackstone_medical_point.get()).build(null));
}
