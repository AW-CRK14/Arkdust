package com.ardc.arkdust.playmethod.oi.ori_infection;

import com.ardc.arkdust.capability.health_system.HealthSystemCapability;
import com.ardc.arkdust.helper.AdvancementHelper;
import com.ardc.arkdust.helper.PosHelper;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.CapabilityRegistry;
import com.ardc.arkdust.resource.DamageTypes;
import com.ardc.arkdust.resource.Tag;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class OISubscriber {//���ļ����ڼ��Ӻ�Դʯ��Ⱦ�йص�����
    //ʰ����Ʒ�ķ���Ϊ�������
    @SubscribeEvent//��Ⱦ��Ʒ�����¼�
    public static void onPlayerGetOIItems(EntityItemPickupEvent event) {
        ItemStack itemStack = event.getItem().getItem();
        Player player = event.getEntity();
        Level world = player.level();
        LazyOptional<HealthSystemCapability> cap = player.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY);

        if (itemStack.getItem() instanceof IOIItem && !player.isCreative()) {//�ж��Ƿ�Ϊ��Ⱦ����Ʒ
            AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/ah"));

            //��ȡ��������Ⱦ����
            cap.ifPresent((i)->{
                IOIItem item = (IOIItem) itemStack.getItem();
                int countFac= (int) ((itemStack.getCount() - 1) * 0.5F + 1);
                int point = item.guaranteePlayerOIPointAdd();
                float damage = item.guaranteeDamage();

                if (item.getOILevel() > i.ORI$point2Level().first) {
                    damage += item.doDamage();
                    point += item.playerOIPointAdd() * (item.getOILevel() - i.ORI$point2Level().first);
                    if (item.ifThrow()) {
                        AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_cry"));
                        player.displayClientMessage(Component.translatable("pma.oi.getOIItem").withStyle(ChatFormatting.RED), false);//������Ϣ
                        event.setCanceled(true);
                        event.getItem().setPickUpDelay(200);
                    }
                } else {
                    AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_camouflage"));
                }

                i.ORI$addPoint(point);
                player.hurt(new DamageSource(DamageTypes.createDamageSource(player,DamageTypes.ORIROCK_INFECTION)), damage * countFac);

                i.sendToClient((ServerPlayer)event.getEntity());
            });

        }

    }


    @Deprecated//TODO
    @SubscribeEvent//ʵ�����ڻ���Դʯ����Դʯ��Ⱦ����ɢԴʯ�¼�
    public static void onEntityDieBecauseOfOI(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player && event.getSource().is(DamageTypes.ORIROCK_INFECTION)) {//�������ԭ��ΪԴʯ
            for (int count = 0; count <= 5; count++){
                Random r = new Random();//�����µ�������������Ƿ��ڴ˴�����Դʯ
                BlockPos pos = new BlockPos((int) (entity.position().x + r.nextInt(-2,3)), PosHelper.limitY((int) (entity.position().y + r.nextInt(-2,3))), (int) (entity.position().z + r.nextInt(-2,3)));//�����µķ���λ��
                if (entity.level().getBlockState(pos).is(Tag.Blocks.ALLOW_ORIROCK_SPREAD)) {//���������ΪԴʯ��������ɵ�
                        if (r.nextInt(32) < 1) {
                        entity.level().setBlock(pos, BlockRegistry.c_originium_block.get().defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    @SubscribeEvent//��һ�ȡ�ɾ�ʱ��ɢ��Ⱦ�¼�
    public static void onPlayerGetAdvancement(AdvancementEvent event) {
        Level world = event.getEntity().level();
        Random r = new Random();
        ResourceLocation rtest = event.getAdvancement().getId();

        boolean configFlag = true;//TODO �����ļ�����

//        if (rtest.equals(new ResourceLocation("arkdust:world_began/world_began"))) {
//            event.getEntity().getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent(HealthSystemCapability::ORI$addRLevel);
//        }

        if (!event.getEntity().isCreative() && !event.getEntity().isSpectator() && configFlag) {

            BlockPos pos = PosHelper.entityPosToBlock(event.getEntity());
            int blockCount = world.getDifficulty().getId() + 1;
            for (int testCount = blockCount * 2 + 2; testCount >= 0; testCount--) {
                BlockPos newPos = PosHelper.getRandomPosNearPos(pos, 16, 16, 4, 128);
                if (world.getBlockState(newPos).is(Tag.Blocks.ALLOW_ORIROCK_SPREAD)) {
                    world.setBlock(newPos, BlockRegistry.c_originium_block.get().defaultBlockState(), 3);
                    System.out.println(newPos);
                    if (blockCount <= 0) testCount = -1;
                    else blockCount--;
                }
            }
        }
    }

    //TODO Դʯ���������Զ�˥��

    @SubscribeEvent//�������ʱ��Դʯ�䶯
    public static void onPlayerRebirth(PlayerEvent.PlayerRespawnEvent event) {
        event.getEntity().getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent(HealthSystemCapability::rebirth);
    }

    @SubscribeEvent//��Ҹ�Ⱦ����ֵϵͳ
    public static void onPlayerBeHurt(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player && event.getSource().is(DamageTypes.ORIROCK_INFECTION)) {
            player.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i) -> {
                Block block = entity.level().getBlockState(PosHelper.entityPosToBlock(player)).getBlock();
                if(block instanceof IOIBlock && new Random().nextFloat() <= 0.1F && ((IOIBlock)block).needOIRLevel() < i.ORI$point2Level().first)
                     i.ORI$addPoint(((IOIBlock)block).tickPlayerOIPointAdd());


//                if (i.ORI$getPoint() >= HealthSystemCapability.ORI$level2Point(i.ORI$getRLevel())) {
//                    player.hurt(new DamageSource(DamageTypes.createDamageSource(player,DamageTypes.ORIROCK_DEATH)), Float.MAX_VALUE);
//                }
//                else if (i.getORIPoint() >= ORICapability.level2Point(i.getORIRLevel() / 2 + 1)) {
//                    //TODO ���debuff
//                }
            });
        }
    }
}