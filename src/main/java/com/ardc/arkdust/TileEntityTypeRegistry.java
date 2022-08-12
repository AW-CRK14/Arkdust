package com.ardc.arkdust;

import com.ardc.arkdust.BlockEntity.SFBlockCreatorBE;
import com.ardc.arkdust.BlockEntity.ScreenedTableBE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<TileEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Utils.MOD_ID);

    public static final RegistryObject<TileEntityType<SFBlockCreatorBE>> aSFBlockCreatorBE = TE.register("structure_frame_block_creator",()->TileEntityType.Builder.of(SFBlockCreatorBE::new,BlockRegistry.iron_structure_frame_creator.get()).build(null));
    public static final RegistryObject<TileEntityType<ScreenedTableBE>> aScreenedTableBE = TE.register("screened_table",()->TileEntityType.Builder.of(ScreenedTableBE::new,BlockRegistry.hang_screened_table.get()).build(null));
}
