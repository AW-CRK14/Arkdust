package com.ardc.arkdust.NewPlayingMethod.OriInfection;

import com.ardc.arkdust.CodeMigration.resourcelocation.Damage;
import com.ardc.arkdust.CodeMigration.RunHelper.AdvancementHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.PosHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.CodeMigration.resourcelocation.Tag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class OISubscriber {//���ļ����ڼ��Ӻ�Դʯ��Ⱦ�йص�����
    private static int count =0;

    @SubscribeEvent//��Ⱦ��Ʒ�����¼�
    public static void onPlayerGetOIItems(EntityItemPickupEvent event) {
        Item item = event.getItem().getItem().getItem();
        ItemStack itemStack = event.getItem().getItem();
        int count = itemStack.getCount();
        PlayerEntity player = event.getPlayer();
        World world = player.level;
        if (item instanceof IOIItem && !world.isClientSide()) {//�ж�����˿��Լ��Ƿ�Ϊ��Ⱦ����Ʒ
            AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/ah"));
            player.hurt(Damage.ORIROCK_INFECTION, ((IOIItem) item).guaranteeDamage());//��ɱ����˺�
            int playerOIResistanceLevel = new OIMain.EntityOI().getPlayerOIResistanceLevel(player, world);//TODO
            new OIMain.EntityOI().addPlayerOIPoint(player, (int) (((IOIItem) item).guaranteePlayerOIPointAdd() * (1 + (count - 1) * 0.5)));//��Ҹ�Ⱦ����
            if (((IOIItem) item).getOILevel() > playerOIResistanceLevel) {
                float getDamageBonus = (float) new OIMain.WorldOI().getWholeOIDataHere(world, player) / 100 + 1;//�����˺��ӳ�
                float itemCountBonus = (1 + (count - 1) * 0.2F);//������������
                float hurt = ((IOIItem) item).doDamage() * getDamageBonus * itemCountBonus - (float) playerOIResistanceLevel;//�������˺�
                player.hurt(Damage.ORIROCK_INFECTION, hurt);
                if (!player.isCreative() && ((IOIItem) item).ifThrow()) {
                    new OIMain.EntityOI().addPlayerOIPoint(player, (int) (((IOIItem) item).playerOIPointAdd() * (1 + (count - 1) * 0.5)));//��Ҹ�Ⱦ����
                    AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_cry"));
                    player.displayClientMessage(new TranslationTextComponent("pma.oi.getOIItem").withStyle(TextFormatting.RED), false);//������Ϣ
                    Random r = new Random();//����������Թ��ж��Ƿ���ʧ��Ʒ
                    ItemEntity reItem = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), r.nextBoolean() ? itemStack.copy() : ItemStack.EMPTY);//����������ʵ��
                    reItem.setPickUpDelay(300);//����ʰȡ��ʱ��ʮ���룩
                    itemStack.setCount(0);//���ԭ��Ʒ
                    world.addFreshEntity(reItem);//��ӵ�����ʵ��
                }
            } else {
                AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_camouflage"));
            }
        }

    }

    @SubscribeEvent//ʵ�����ڻ���Դʯ����Դʯ��Ⱦ����ɢԴʯ�¼�
    public static void onEntityDieBecauseOfOI(LivingDeathEvent event) {
        Entity entity = event.getEntityLiving();
        if (event.getSource().msgId.equals(Damage.ORIROCK_INFECTION.msgId)) {//�������ԭ��ΪԴʯ
            int posX = (int) entity.getX();
            int posY = Math.max((int) entity.getY() - 1, 3);//��ֹ�Ž����
            int posZ = (int) entity.getZ();
            for (int x = -2; x <= 2; x++) {//����5*5*5����
                for (int y = -2; y <= 2; y++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);//�����µķ���λ��
                        if (Tag.Blocks.ALLOW_ORIROCK_SPREAD.contains(entity.level.getBlockState(pos).getBlock())) {//���������ΪԴʯ��������ɵ�
                            Random r = new Random();//�����µ�������������Ƿ��ڴ˴�����Դʯ
                            if (r.nextInt(32) < 1) {
                                entity.level.setBlock(pos, BlockRegistry.c_originium_block.get().defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent//��һ�ȡ�ɾ�ʱ��ɢ��Ⱦ�¼�
    public static void onPlayerGetAdvancement(AdvancementEvent event) {
        World world = event.getPlayer().level;
        if (world.isClientSide) return;
        Random r = new Random();
        ResourceLocation rtest = event.getAdvancement().getId();
        if (rtest.equals(new ResourceLocation("arkdust:world_began/world_began"))) {
            WorldOIData data = WorldOIData.get(world);
            data.worldOIBegin(world);
            new OIMain.EntityOI().setPlayerOIResistanceLevel(event.getPlayer(),1,world);
        }
        if (r.nextInt(32) > 3 && !new OIMain.WorldOI().ifWorldOIRun(world)) return;
        BlockPos pos = PosHelper.entityPosToBlock(event.getPlayer());
        int blockCount = world.getDifficulty().getId() + 1;
        for (int testCount = blockCount * 2 + 2; testCount >= 0; testCount--) {
            BlockPos newPos = PosHelper.getRandomPosNearPos(pos, 16, 16, 4, 128);
            if (Tag.Blocks.ALLOW_ORIROCK_SPREAD.contains(world.getBlockState(newPos).getBlock())) {
                world.setBlock(newPos, BlockRegistry.c_originium_block.get().defaultBlockState(), 3);
                System.out.println(newPos);
                if (blockCount <= 0) testCount = -1;
                else blockCount--;
            }
        }
        if (!world.isClientSide()) {
            if (!new OIMain.WorldOI().ifWorldOIRun(world)) {
                event.getPlayer().displayClientMessage(new TranslationTextComponent("pma.oi.getAdvancement"), false);
            }
        }
    }

    //TODO Դʯ���������Զ�˥��

    @SubscribeEvent//�������ʱ��Դʯ�䶯
    public static void onPlayerDie(PlayerSetSpawnEvent event) {
        World worldIn = event.getEntity().level;
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity && !worldIn.isClientSide()) {
            WorldOIData data = WorldOIData.get(worldIn);
            int aOIPoint = data.getPlayerOIPoint((PlayerEntity) entity);
            int rLevel = data.getPlayerOIRLevel((PlayerEntity) entity);
            int level = data.getOIDataList((PlayerEntity) entity).get(0);
            if (aOIPoint > new OIMain.EntityOI().getBoundary(rLevel, 3))
                data.setPlayerOIPoint((PlayerEntity) entity, new OIMain.EntityOI().getBoundary(rLevel - 1, 3));
            if (aOIPoint < new OIMain.EntityOI().getBoundary(rLevel, 2))
                data.addPlayerOIPoint((PlayerEntity) entity, (int) (rLevel * 2.5 - level + 3.5));
            else data.addPlayerOIPoint((PlayerEntity) entity, (int) (-rLevel * 1.5 - level));
        }
    }

    @SubscribeEvent//��Ҹ�Ⱦ����ֵϵͳ
    public static void onPlayerBeHurt(LivingAttackEvent event) {
        Entity entity = event.getEntity();
        World world = event.getEntity().level;
        if(count < 20){
            count++;
            return;
        }
        if (entity instanceof PlayerEntity && ((PlayerEntity) entity).getHealth()>0 && !world.isClientSide && !event.getSource().equals(Damage.ORIROCK_DEATH)) {
            count = 0;
            WorldOIData data = WorldOIData.get(world);
            PlayerEntity playerEntity = (PlayerEntity) entity;
            int playerOIPoint = data.getPlayerOIPoint(playerEntity);
            int playerOIRLevel = data.getPlayerOIRLevel(playerEntity);
            OIMain.EntityOI main = new OIMain.EntityOI();
            int a = main.getBoundary(playerOIRLevel, 3);
            if (playerOIPoint > a) {
                playerEntity.hurt(Damage.ORIROCK_DEATH,Float.MAX_VALUE);
                //TODO �����ᾧ
            }
            if (playerOIPoint > main.getBoundary(playerOIRLevel, 1)) {
                System.out.println("���Ч������");
                //TODO ���debuff
            }
        }
    }
}