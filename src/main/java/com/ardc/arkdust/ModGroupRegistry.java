package com.ardc.arkdust;

import com.ardc.arkdust.ModGroup.*;
import net.minecraft.item.ItemGroup;

//此文件用于注册mod的物品分组
public class ModGroupRegistry {
    public static final ItemGroup worldMaterial = new MainWorldMaterial();
    //第一个worldMaterial是为物品添加进组时调用的名字，第二个对应（此处为mod group中的）对应文件
    public static final ItemGroup terraTools = new TerraTools();
    public static final ItemGroup terraEnergy = new TerraEnergy();
    public static final ItemGroup terraCommonMaterial = new TerraWorldCommonMaterial();
    public static final ItemGroup terraIndustrialMaterial = new TerraIndustrialMaterial();
}

