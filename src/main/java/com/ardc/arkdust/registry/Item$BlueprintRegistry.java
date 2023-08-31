package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.playmethod.blueprint.BlueprintItem;
import com.ardc.arkdust.playmethod.blueprint.BlueprintTargetHandler;
import com.ardc.arkdust.playmethod.blueprint.CWC7BlueprintItem;
import com.ardc.arkdust.playmethod.blueprint.GeneralBlueprintItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class Item$BlueprintRegistry {
    public static final DeferredRegister<Item> BLUEPRINTS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> e_t_experiment_data = BLUEPRINTS.register("e_t_experiment_data", ()->new CWC7BlueprintItem(false,3));//末地-泰拉折跃实验数据
    public static final RegistryObject<Item> e_t_theory_data = BLUEPRINTS.register("e_t_theory_data", ()->new CWC7BlueprintItem(false,2));//末地-泰拉折跃理论数据
    public static final RegistryObject<Item> general_blueprint = BLUEPRINTS.register("general_blueprint", GeneralBlueprintItem::new);


}
