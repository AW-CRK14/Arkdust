package com.ardc.arkdust.registry;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.block_entity.SeRcuMachineBE;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Utils.MOD_ID);
    public static final RegistryObject<MenuType<SeRcuMachineBE.Menu>> se_rcu_mac = CONTAINER.register("se_rcu_mac",()-> IForgeMenuType.create(SeRcuMachineBE.Menu::new));
}
