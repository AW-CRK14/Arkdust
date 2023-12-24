package com.ardc.arkdust.worldgen.structure;

import com.ardc.arkdust.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.Collections;

public class ExtraStructureJigsawPool {
    public static void bootstrap(BootstapContext<StructureTemplatePool> context){
        final StructureTemplatePool EMPTY = new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), Collections.emptyList());
        context.register(TEST_BRIDGE,EMPTY);
    }

    public static final ResourceKey<StructureTemplatePool> TEST_BRIDGE = createKey("cw/test_bridge/main");

    public static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation(Utils.MOD_ID,name));
    }
}
