package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.worldgen.biome.surface_builder.GravellyWastelandSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.LazyValue;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SurfaceBuilderRegistry {

    public static final SurfaceBuilderConfig FUNGUS = new SurfaceBuilderConfig(Blocks.MYCELIUM.defaultBlockState(), Blocks.COARSE_DIRT.defaultBlockState(),Blocks.ANDESITE.defaultBlockState());
    public static final SurfaceBuilderConfig SURFACE_SAND = new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(), Blocks.STONE.defaultBlockState(),Blocks.SANDSTONE.defaultBlockState());
    public static final SurfaceBuilderConfig COBBLESTONE_COVER = new SurfaceBuilderConfig(Blocks.COBBLESTONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(),Blocks.GRAVEL.defaultBlockState());

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDER = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Utils.MOD_ID);
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GRAVELLY_WASTELAND = SURFACE_BUILDER.register("gravelly_wasteland",()-> new GravellyWastelandSurfaceBuilder(SurfaceBuilderConfig.CODEC));

    public static final LazyValue<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> CW$FAULT_LINE = new LazyValue<>(()->register("cw/fault_line",GRAVELLY_WASTELAND.get().configured(SurfaceBuilder.CONFIG_BADLANDS)));

    private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER,Utils.MOD_ID + ":" + name, configuredSurfaceBuilder);
    }
}
