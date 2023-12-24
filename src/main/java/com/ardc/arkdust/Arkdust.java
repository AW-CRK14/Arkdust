package com.ardc.arkdust;

import com.ardc.arkdust.recipe.RecipeSerializerRegistry;
import com.ardc.arkdust.registry.RecipeTypeRegistry;
import com.ardc.arkdust.registry.*;
import com.ardc.arkdust.registry.worldgen.FeatureRegistry;
import com.ardc.arkdust.resource.Tag;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Utils.MOD_ID)
public class Arkdust {

    public Arkdust(){
        IEventBus BUS = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.registryItemDeferredRegister(BUS);
        BlockRegistry.BLOCKS.register(BUS);//方块注册
        TileEntityTypeRegistry.TE.register(BUS);//方块实体注册
        ContainerRegistry.CONTAINER.register(BUS);//方块容器注册
        ModGroupRegistry.TAB.register(BUS);

        //生物群系注册
//        BiomeRegistry.BIOMES.register(BUS);
//        SurfaceBuilderRegistry.SURFACE_BUILDER.register(BUS);
//        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD,Utils.MOD_ID,);

        //粒子注册
        ParticleRegistry.PARTICLE.register(BUS);
        //ParticleFactoryRegistry工厂注册

        //结构注册
//        StructureRegistryHelper.STRUCTURES.register(BUS);
        FeatureRegistry.FEATURES.register(BUS);
//        PlacementRegistry.PLACEMENT.register(BUS);
//        StructureRegistryHelper.DimensionSettingRegistry();
//        ConfiguredStructures.registryCfedStructure();

        Tag.init();

        //配方注册
        RecipeTypeRegistry.RECIPE.register(BUS);
        RecipeSerializerRegistry.RECIPE.register(BUS);

        //Ard内部信息注册-见FMLFinishEventHandler
    }
}
