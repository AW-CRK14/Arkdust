package com.ardc.arkdust.registry.item;

import com.ardc.arkdust.Items.sugar.Industrial_sugar;
import com.ardc.arkdust.Items.sugar.SugarLump;
import com.ardc.arkdust.Items.sugar.SugarPack;
import com.ardc.arkdust.Items.sugar.Sugar_substitute;
import com.ardc.arkdust.Utils;
import com.ardc.arkdust.playmethod.oi.OIItem.PreOIItem;
import com.ardc.arkdust.preobject.PreItem;
import com.ardc.arkdust.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Item$TerraMaterial {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final Item.Properties P_TERRA_MAT = new Item.Properties();

    //TerraCommonMaterial̩������ ע��
    public static final RegistryObject<Item> originium = ITEMS.register("originium", ()->new PreOIItem(P_TERRA_MAT,true,3,2,3,12,4));//Դʯ
    public static final RegistryObject<Item> originium_dust = ITEMS.register("originium_dust", ()->new PreOIItem(P_TERRA_MAT,false,2,0,1,32,8));//Դʯ��e1
    public static final RegistryObject<Item> originium_shard = ITEMS.register("originium_shard", ()->new PreOIItem(P_TERRA_MAT,false,2,1,0,4,1));//Դʯ��Ƭ
    public static final RegistryObject<Item> poor_originium = ITEMS.register("poor_originium", ()->new PreOIItem(P_TERRA_MAT.requiredFeatures(),false,1,0,0,0,2));//ƶ�Դʯ
    public static final RegistryObject<Item> ester = ITEMS.register("ester", ()->new PreItem(P_TERRA_MAT));//һ����
    public static final RegistryObject<Item> polyester = ITEMS.register("polyester", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.SPECIAL)));//������
    public static final RegistryObject<Item> polyester_pack = ITEMS.register("polyester_pack", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//������
    public static final RegistryObject<Item> polyester_lump = ITEMS.register("polyester_lump", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//�ļ���
    public static final RegistryObject<Item> diketon = ITEMS.register("diketon", ()->new PreItem(P_TERRA_MAT));//һ��ͪ
    public static final RegistryObject<Item> polyketon = ITEMS.register("polyketon", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.SPECIAL)));//����ͪ
    public static final RegistryObject<Item> aketon = ITEMS.register("aketon", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//����ͪ
    public static final RegistryObject<Item> keton_colloid = ITEMS.register("keton_colloid", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//�ļ�ͪ
    public static final RegistryObject<Item> oriron_shard = ITEMS.register("oriron_shard", ()->new PreItem(P_TERRA_MAT));//һ������
    public static final RegistryObject<Item> oriron = ITEMS.register("oriron", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.SPECIAL)));//��������
    public static final RegistryObject<Item> oriron_cluster = ITEMS.register("oriron_cluster", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//��������
    public static final RegistryObject<Item> oriron_block = ITEMS.register("oriron_block", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//�ļ�����
    public static final RegistryObject<Item> crystalline_component = ITEMS.register("crystalline_component", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ�����Ӿ���
    public static final RegistryObject<Item> crystalline_circuit = ITEMS.register("crystalline_circuit", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//�������Ӿ���
    public static final RegistryObject<Item> crystalline_electronic_unit = ITEMS.register("crystalline_electronic_unit", ()->new PreItem(P_TERRA_MAT));//�������Ӿ���
    public static final RegistryObject<Item> sugar_substitute = ITEMS.register("sugar_substitute", Sugar_substitute::new);//һ����
    public static final RegistryObject<Item> industrial_sugar = ITEMS.register("industrial_sugar", Industrial_sugar::new);//������
    public static final RegistryObject<Item> sugar_pack = ITEMS.register("sugar_pack", SugarPack::new);//������
    public static final RegistryObject<Item> sugar_lump = ITEMS.register("sugar_lump", SugarLump::new);//�ļ���
    public static final RegistryObject<Item> cutting_fluid_solution = ITEMS.register("cutting_fluid_solution", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ������Һ
    public static final RegistryObject<Item> compound_cutting_fluid = ITEMS.register("compound_cutting_fluid", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//��������Һ
    public static final RegistryObject<Item> damaged_device = ITEMS.register("damaged_device", ()->new PreItem(P_TERRA_MAT));//һ��װ��
    public static final RegistryObject<Item> device = ITEMS.register("device", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.SPECIAL)));//����װ��
    public static final RegistryObject<Item> integrated_device = ITEMS.register("integrated_device", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//����װ��
    public static final RegistryObject<Item> optimized_device = ITEMS.register("optimized_device",()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//�ļ�װ��
    public static final RegistryObject<Item> coagulating_gel = ITEMS.register("coagulating_gel", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ������
    public static final RegistryObject<Item> polymerized_gel = ITEMS.register("polymerized_gel", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//��������
    public static final RegistryObject<Item> grindstone = ITEMS.register("grindstone", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ����ĥʯ
    public static final RegistryObject<Item> grindstone_pentahydrate = ITEMS.register("grindstone_pentahydrate", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//������ĥʯ
    public static final RegistryObject<Item> incandescent_alloy = ITEMS.register("incandescent_alloy", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ����Ͻ�
    public static final RegistryObject<Item> incandescent_alloy_block = ITEMS.register("incandescent_alloy_block", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//������Ͻ�
    public static final RegistryObject<Item> loxic_kohl = ITEMS.register("loxic_kohl", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ����
    public static final RegistryObject<Item> white_horse_kohl = ITEMS.register("white_horse_kohl",()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//������
    public static final RegistryObject<Item> manganese_ore = ITEMS.register("manganese_ore", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ����
    public static final RegistryObject<Item> manganese_trihydrate = ITEMS.register("manganese_trihydrate", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//������
    public static final RegistryObject<Item> orirock = ITEMS.register("orirock", ()->new PreItem(P_TERRA_MAT));//һ��Դ��
    public static final RegistryObject<Item> orirock_cube = ITEMS.register("orirock_cube", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.SPECIAL)));//����Դ��
    public static final RegistryObject<Item> orirock_cluster = ITEMS.register("orirock_cluster", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//����Դ��
    public static final RegistryObject<Item> orirock_concentration = ITEMS.register("orirock_concentration", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//�ļ�Դ��
    public static final RegistryObject<Item> refined_solvent = ITEMS.register("refined_solvent", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ���ܼ�
    public static final RegistryObject<Item> semi_synthetic_solvent = ITEMS.register("semi_synthetic_solvent", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//�����ܼ�
    public static final RegistryObject<Item> rma70_12 = ITEMS.register("rma70_12", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.VALUABLE)));//һ������
    public static final RegistryObject<Item> rma70_24 = ITEMS.register("rma70_24", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.TREASURE)));//��������
    public static final RegistryObject<Item> d32_steel = ITEMS.register("d32_steel", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.RARE)));//D32��
    public static final RegistryObject<Item> bipolar_nanoflake = ITEMS.register("bipolar_nanoflake", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.RARE)));//����Ƭ
    public static final RegistryObject<Item> polymerization_preparation = ITEMS.register("polymerization_preparation", ()->new PreItem(P_TERRA_MAT.rarity(ItemRegistry.Rar.RARE)));//�ۺϼ�

}
