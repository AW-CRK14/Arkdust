package com.ardc.arkdust.playmethod.oi.OIItem;

import com.ardc.arkdust.resourcelocation.Damage;
import com.ardc.arkdust.preobject.PreBlock;
import com.ardc.arkdust.playmethod.oi.ori_infection.IOIBlock;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PreOIBlock extends PreBlock implements IOIBlock {
    private final float touchTickDamage;
    private final int tickPlayerOILevelAdd;
    private final int needOIRLevel;
    public PreOIBlock(Properties properties, float touchTickDamage,int tickPlayerOILevelAdd,int needOIRLevel) {
        super(properties);
        this.touchTickDamage = touchTickDamage;
        this.tickPlayerOILevelAdd = tickPlayerOILevelAdd;
        this.needOIRLevel = needOIRLevel;
    }

    @Override
    public float touchTickDamage() {
        return touchTickDamage;
    }

    @Override
    public float touchTickDamageProbability() {
        return 1;
    }

    @Override
    public int tickPlayerOIPointAdd() {
        return tickPlayerOILevelAdd;
    }

    @Override
    public int needOIRLevel() {
        return needOIRLevel;
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if(!(entity instanceof LivingEntity) || !this.touchHurt()) return;
        AtomicInteger rLevel = new AtomicInteger();
        entity.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)-> rLevel.set(i.ORI$getRLevel()));
        if(new Random().nextFloat() <= touchTickDamageProbability()){
            entity.hurt(Damage.ORIROCK_INFECTION, rLevel.get() >= this.needOIRLevel ? 0.2F : touchTickDamage);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

}
