package com.ardc.arkdust.playmethod.health_system.ori_infection;

import com.ardc.arkdust.CodeMigration.RunHelper.AdvancementHelper;
import com.ardc.arkdust.CodeMigration.RunHelper.PosHelper;
import com.ardc.arkdust.CodeMigration.resourcelocation.Damage;
import com.ardc.arkdust.CodeMigration.resourcelocation.Tag;
import com.ardc.arkdust.playmethod.health_system.HealthSystemCapability;
import com.ardc.arkdust.playmethod.health_system.IHealthSystemCapability;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class OISubscriber {//此文件用于监视和源石感染有关的数据
    //拾起物品的方法为仅服务端
    @SubscribeEvent//感染物品丢出事件
    public static void onPlayerGetOIItems(EntityItemPickupEvent event) {
        ItemStack itemStack = event.getItem().getItem();
        PlayerEntity player = event.getPlayer();
        World world = player.level;
        LazyOptional<IHealthSystemCapability> cap = player.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY);

        if (itemStack.getItem() instanceof IOIItem && !player.isCreative()) {//判断是否为感染性物品
            AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/ah"));

            //获取与提升感染数据
            cap.ifPresent((i)->{
                IOIItem item = (IOIItem) itemStack.getItem();
                int countFac= (int) ((itemStack.getCount() - 1) * 0.5F + 1);
                int point = item.guaranteePlayerOIPointAdd();
                float damage = item.guaranteeDamage();

                if (item.getOILevel() > i.ORI$getRLevel()) {
                    damage += item.doDamage();
                    point += item.playerOIPointAdd() * (item.getOILevel() - i.ORI$getRLevel());
                    if (item.ifThrow()) {
                        AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_cry"));
                        player.displayClientMessage(new TranslationTextComponent("pma.oi.getOIItem").withStyle(TextFormatting.RED), false);//发送信息
                        ItemEntity reItem = new ItemEntity(world, player.getX(), player.getY(), player.getZ(),itemStack.copy());//创建掉落物实体
                        reItem.setPickUpDelay(300);//设置拾取延时（十五秒）
                        itemStack.setCount(0);//清除原物品
                        world.addFreshEntity(reItem);//添加掉落物实体
                    }
                } else {
                    AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_camouflage"));
                }

                i.ORI$addPoint(point);
                player.hurt(Damage.ORIROCK_INFECTION, damage * countFac);

                i.sendPackToClient((ServerPlayerEntity) event.getPlayer());
            });

        }

    }


    @Deprecated//TODO
    @SubscribeEvent//实体死于环境源石（弱源石感染）扩散源石事件
    public static void onEntityDieBecauseOfOI(LivingDeathEvent event) {
        Entity entity = event.getEntityLiving();
        if (event.getSource().msgId.equals(Damage.ORIROCK_INFECTION.msgId)) {//如果死亡原因为源石
            int posX = (int) entity.getX();
            int posY = Math.max((int) entity.getY() - 1, 3);//防止放进虚空
            int posZ = (int) entity.getZ();
            for (int x = -2; x <= 2; x++) {//遍历5*5*5区域
                for (int y = -2; y <= 2; y++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos pos = new BlockPos(posX + x, posY + y, posZ + z);//创建新的方块位置
                        if (Tag.Blocks.ALLOW_ORIROCK_SPREAD.contains(entity.level.getBlockState(pos).getBlock())) {//如果此坐标为源石允许的生成点
                            Random r = new Random();//创建新的随机数并测试是否在此处创建源石
                            if (r.nextInt(32) < 1) {
                                entity.level.setBlock(pos, BlockRegistry.c_originium_block.get().defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent//玩家获取成就时扩散感染事件
    public static void onPlayerGetAdvancement(AdvancementEvent event) {
        World world = event.getPlayer().level;
        Random r = new Random();
        ResourceLocation rtest = event.getAdvancement().getId();

        if (rtest.equals(new ResourceLocation("arkdust:world_began/world_began"))) {
            if (!world.isClientSide()) {
                WorldOIData data = WorldOIData.get(world);
                data.worldOIBegin(world);
            }
            event.getPlayer().getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent(IHealthSystemCapability::ORI$addRLevel);
        }

        if (!event.getPlayer().isCreative() && !event.getPlayer().isSpectator()) {

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

            if (!world.isClientSide() && !WorldOIData.get(world).getWorldOIState()) {
                event.getPlayer().displayClientMessage(new TranslationTextComponent("pma.oi.getAdvancement"), false);
            }

        }
    }

    //TODO 源石环境变量自动衰减

    @SubscribeEvent//玩家重生时的源石变动
    public static void onPlayerRebirth(PlayerSetSpawnEvent event) {
        PlayerEntity entity = event.getPlayer();
        entity.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)->{
            if(i.ORI$getRLevel() <= 3){
                i.ORI$resetPoint();
            }else if(i.ORI$getPoint() < HealthSystemCapability.ORI$level2Point(i.ORI$getRLevel()/3)){
                i.ORI$addPoint(20);
            }else {
                i.ORI$addPoint(-20);
            }
        });
    }

    @SubscribeEvent//玩家感染量阈值系统
    public static void onPlayerBeHurt(LivingAttackEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity && event.getSource().equals(Damage.ORIROCK_INFECTION)) {
            PlayerEntity playerEntity = (PlayerEntity) entity;
            playerEntity.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i) -> {
                Block block = entity.level.getBlockState(PosHelper.entityPosToBlock((PlayerEntity)entity)).getBlock();
                if(block instanceof IOIBlock && new Random().nextFloat() <= 0.1F && ((IOIBlock)block).needOIRLevel() > i.ORI$getRLevel())
                     i.ORI$addPoint(((IOIBlock)block).tickPlayerOIPointAdd());


                if (i.ORI$getPoint() >= HealthSystemCapability.ORI$level2Point(i.ORI$getRLevel())) {
                    playerEntity.hurt(Damage.ORIROCK_DEATH, Float.MAX_VALUE);
                    //TODO 创建结晶
                }
//                else if (i.getORIPoint() >= ORICapability.level2Point(i.getORIRLevel() / 2 + 1)) {
//                    //TODO 随机debuff
//                }
            });
        }
    }
}