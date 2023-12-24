package com.ardc.arkdust.playmethod.oi.OIItem;

import com.ardc.arkdust.playmethod.oi.ori_infection.IOIBlock;
import com.ardc.arkdust.preobject.PreBlock;
import com.ardc.arkdust.resource.DamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if(!(entity instanceof LivingEntity) || !this.touchHurt()) return;
        if(new Random().nextFloat() <= touchTickDamageProbability()){
            entity.hurt(new DamageSource(DamageTypes.createDamageSource(entity,DamageTypes.ORIROCK_INFECTION)),touchTickDamage);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

}
