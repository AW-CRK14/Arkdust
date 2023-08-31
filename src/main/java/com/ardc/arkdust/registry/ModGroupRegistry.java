package com.ardc.arkdust.registry;

import com.ardc.arkdust.item_group.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModGroupRegistry {
    public static final ItemGroup WORLD_MATERIAL = new MainWorldMaterial();
    public static final ItemGroup TERRA_TOOLS = new TerraTools();
    public static final ItemGroup TERRA_ENERGY = new TerraEnergy();
    public static final ItemGroup TERRA_WORLD_COMMON_MATERIAL = new TerraWorldCommonMaterial();
    public static final ItemGroup TERRA_INDUSTRIAL_MATERIAL = new TerraIndustrialMaterial();
    public static final ItemGroup TERRA_BLUEPRINT = new GroupGenerator("terra_blueprint",()->new ItemStack(Item$BlueprintRegistry.e_t_experiment_data::get));
}

