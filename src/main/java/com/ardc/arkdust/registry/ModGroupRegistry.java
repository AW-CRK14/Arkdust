package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.item.Item$BlueprintRegistry;
import com.ardc.arkdust.registry.item.Item$CommonWorld;
import com.ardc.arkdust.registry.item.Item$TerraMaterial;
import com.ardc.arkdust.registry.item.Item$TerraTool;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModGroupRegistry {
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WORLD_MATERIAL = tabOf(()->new ItemStack(Item$CommonWorld.dt_crystal.get()),"common_world",Item$CommonWorld.ITEMS);
    public static final RegistryObject<CreativeModeTab> TERRA_WORLD_MATERIAL = tabOf(()->new ItemStack(Item$TerraMaterial.rma70_24.get()),"terra_world_material",Item$TerraMaterial.ITEMS);
    public static final RegistryObject<CreativeModeTab> TERRA_TOOL = tabOf(()->new ItemStack(Item$TerraTool.pau_epee.get()),"terra_tool",Item$TerraTool.ITEMS);
    public static final RegistryObject<CreativeModeTab> BLUEPRINT = tabOf(()->new ItemStack(Item$BlueprintRegistry.e_t_experiment_data.get()),"blueprint",Item$BlueprintRegistry.ITEMS);
    public static final RegistryObject<CreativeModeTab> DEFAULT = tabOf(()->new ItemStack(ItemRegistry.structure_ignore_block.get()),"default",ItemRegistry.ITEMS);


    public static RegistryObject<CreativeModeTab> tabOf(Supplier<ItemStack> icon, String name, DeferredRegister<Item> reg){
        return TAB.register(name,()-> CreativeModeTab.builder().icon(icon).title(Component.translatable("tab.arkdust." + name)).displayItems((parameters,output)->reg.getEntries().forEach((o)->output.accept(o.get()))).build());
    }
}

