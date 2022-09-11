package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SurfaceBuilderInit {
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDER = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Utils.MOD_ID);
}
