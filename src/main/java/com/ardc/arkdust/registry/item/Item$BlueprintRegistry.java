package com.ardc.arkdust.registry.item;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.playmethod.blueprint.CWC7BlueprintItem;
import com.ardc.arkdust.playmethod.blueprint.GeneralBlueprintItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Item$BlueprintRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> e_t_experiment_data = ITEMS.register("e_t_experiment_data", ()->new CWC7BlueprintItem(false,3));//末地-泰拉折跃实验数据
    public static final RegistryObject<Item> e_t_theory_data = ITEMS.register("e_t_theory_data", ()->new CWC7BlueprintItem(false,2));//末地-泰拉折跃理论数据
    public static final RegistryObject<Item> general_blueprint = ITEMS.register("general_blueprint", GeneralBlueprintItem::new);


}
