package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.config.RandomPosOffsetConfig;
import com.ardc.arkdust.worldgen.placement.RandomOffsetPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PlacementRegistry {
    public static final DeferredRegister<Placement<?>> PLACEMENT = DeferredRegister.create(ForgeRegistries.DECORATORS, Utils.MOD_ID);

    public static final RegistryObject<Placement<RandomPosOffsetConfig>> RANDOM_OFFSET = PLACEMENT.register("random_offset",()-> new RandomOffsetPlacement(RandomPosOffsetConfig.CODEC));
}
