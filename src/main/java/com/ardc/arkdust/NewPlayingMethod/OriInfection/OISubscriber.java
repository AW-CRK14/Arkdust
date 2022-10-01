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
public class OISubscriber {//此文件用于监视和源石感染有关的数据
    private static int count =0;

    @SubscribeEvent//感染物品丢出事件
    public static void onPlayerGetOIItems(EntityItemPickupEvent event) {
        Item item = event.getItem().getItem().getItem();
        ItemStack itemStack = event.getItem().getItem();
        int count = itemStack.getCount();
        PlayerEntity player = event.getPlayer();
        World world = player.level;
        if (item instanceof IOIItem && !world.isClientSide()) {//判断世界端口以及是否为感染性物品
            AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/ah"));
            player.hurt(Damage.ORIROCK_INFECTION, ((IOIItem) item).guaranteeDamage());//造成保底伤害
            int playerOIResistanceLevel = new OIMain.EntityOI().getPlayerOIResistanceLevel(player, world);//TODO
            new OIMain.EntityOI().addPlayerOIPoint(player, (int) (((IOIItem) item).guaranteePlayerOIPointAdd() * (1 + (count - 1) * 0.5)));//玩家感染提升
            if (((IOIItem) item).getOILevel() > playerOIResistanceLevel) {
                float getDamageBonus = (float) new OIMain.WorldOI().getWholeOIDataHere(world, player) / 100 + 1;//计算伤害加成
                float itemCountBonus = (1 + (count - 1) * 0.2F);//计算数量叠加
                float hurt = ((IOIItem) item).doDamage() * getDamageBonus * itemCountBonus - (float) playerOIResistanceLevel;//计算总伤害
                player.hurt(Damage.ORIROCK_INFECTION, hurt);
                if (!player.isCreative() && ((IOIItem) item).ifThrow()) {
                    new OIMain.EntityOI().addPlayerOIPoint(player, (int) (((IOIItem) item).playerOIPointAdd() * (1 + (count - 1) * 0.5)));//玩家感染提升
                    AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_cry"));
                    player.displayClientMessage(new TranslationTextComponent("pma.oi.getOIItem").withStyle(TextFormatting.RED), false);//发送信息
                    Random r = new Random();//创建随机数以供判断是否消失物品
                    ItemEntity reItem = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), r.nextBoolean() ? itemStack.copy() : ItemStack.EMPTY);//创建掉落物实体
                    reItem.setPickUpDelay(300);//设置拾取延时（十五秒）
                    itemStack.setCount(0);//清除原物品
                    world.addFreshEntity(reItem);//添加掉落物实体
                }
            } else {
                AdvancementHelper.tryAddAdvancementToPlayer(player, new ResourceLocation("arkdust:world_began/i_camouflage"));
            }
        }

    }

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

    //TODO 源石环境变量自动衰减

    @SubscribeEvent//玩家重生时的源石变动
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

    @SubscribeEvent//玩家感染量阈值系统
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
                //TODO 创建结晶
            }
            if (playerOIPoint > main.getBoundary(playerOIRLevel, 1)) {
                System.out.println("随机效果触发");
                //TODO 随机debuff
            }
        }
    }
}