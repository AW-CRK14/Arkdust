package com.ardc.arkdust;

import com.ardc.arkdust.BlockEntity.SFBlockCreatorBE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<TileEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Utils.MOD_ID);

    public static final RegistryObject<TileEntityType<SFBlockCreatorBE>> aSFBlockCreatorBE = TE.register("structure_frame_block_creator",()->TileEntityType.Builder.of(SFBlockCreatorBE::new,BlockRegistry.iron_structure_frame_creator.get()).build(null));
}
