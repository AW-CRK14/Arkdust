package com.ardc.arkdust.registry.item;

import com.ardc.arkdust.Items.EnderEnergyRadar;
import com.ardc.arkdust.Items.pre.OIRVaccineItem;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.gui.screen.menu.MenuTestItem;
import com.ardc.arkdust.obj_property.MaterialTier;
import com.ardc.arkdust.playmethod.oi.OIItem.PreOIBlockItem;
import com.ardc.arkdust.preobject.PreBlockItem;
import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Item$TerraTool {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);


    //WorldMaterial闭环区物品 注册
    //TerraTools泰拉工具工具 注册
    public static final RegistryObject<Item> unnamed_sword = ITEMS.register("unnamed_sword", ()->new SwordItem(MaterialTier.PURE_GOLD, 24, -1.6F, new Item.Properties().fireResistant()));//不知名的刀
    public static final RegistryObject<Item> pau_epee = ITEMS.register("pau_epee", ()->new SwordItem(MaterialTier.PURE_GOLD, 10, -3.4F, new Item.Properties()));//赤金重剑
    public static final RegistryObject<Item> pau_sword = ITEMS.register("pau_sword", ()->new SwordItem(MaterialTier.PURE_GOLD, 2, -2.2F, new Item.Properties()));//赤金剑
    public static final RegistryObject<Item> pau_pickaxe = ITEMS.register("pau_pickaxe", ()->new PickaxeItem(MaterialTier.PURE_GOLD, -1, -2.8F, new Item.Properties()));//赤金镐
    public static final RegistryObject<Item> pau_axe = ITEMS.register("pau_axe", ()->new AxeItem(MaterialTier.PURE_GOLD, 3, -3, new Item.Properties()));//赤金斧
    public static final RegistryObject<Item> pau_hoe = ITEMS.register("pau_hoe", ()->new HoeItem(MaterialTier.PURE_GOLD, -4, -1.5F, new Item.Properties()));//赤金锄
    public static final RegistryObject<Item> pau_shovel = ITEMS.register("pau_shovel", ()->new ShovelItem(MaterialTier.PURE_GOLD, -2, -2.1F, new Item.Properties()));//赤金铲

}
