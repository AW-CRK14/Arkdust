package com.ardc.arkdust.registry;

import com.ardc.arkdust.BlockEntity.BlackstoneMedicalPointBE;
import com.ardc.arkdust.BlockEntity.SFBlockCreatorBE;
import com.ardc.arkdust.BlockEntity.ScreenedTableBE;
import com.ardc.arkdust.NewPlayingMethod.story.blockanditem.StoryPointBE;
import com.ardc.arkdust.Utils;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<TileEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Utils.MOD_ID);

    public static final RegistryObject<TileEntityType<SFBlockCreatorBE>> aSFBlockCreatorBE = TE.register("structure_frame_block_creator",()->TileEntityType.Builder.of(SFBlockCreatorBE::new,BlockRegistry.iron_structure_frame_creator.get()).build(null));
    public static final RegistryObject<TileEntityType<ScreenedTableBE>> aScreenedTableBE = TE.register("screened_table",()->TileEntityType.Builder.of(ScreenedTableBE::new,BlockRegistry.hang_screened_table.get()).build(null));
    public static final RegistryObject<TileEntityType<BlackstoneMedicalPointBE>> aBlackstoneMedicalPointBE = TE.register("blackstone_medical_point",()->TileEntityType.Builder.of(BlackstoneMedicalPointBE::new,BlockRegistry.blackstone_medical_point.get()).build(null));
    public static final RegistryObject<TileEntityType<StoryPointBE>> aStoryPointBE = TE.register("story_point",()->TileEntityType.Builder.of(StoryPointBE::new,BlockRegistry.story_point.get()).build(null));
//    public static final RegistryObject<TileEntityType<ProjectionBlockBE>> aProjectionBE = TE.register("projection",()->TileEntityType.Builder.of(ProjectionBlockBE::new,BlockRegistry.blackstone_medical_point.get()).build(null));
}
