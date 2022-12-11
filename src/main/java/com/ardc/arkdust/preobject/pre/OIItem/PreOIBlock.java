package com.ardc.arkdust.preobject.pre.OIItem;

import com.ardc.arkdust.CodeMigration.resourcelocation.Damage;
import com.ardc.arkdust.preobject.pre.PreBlock;
import com.ardc.arkdust.playmethod.OriInfection.IOIBlock;
import com.ardc.arkdust.playmethod.OriInfection.OIMain;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
    public int touchTickDamageProbability() {
        return 100;
    }

    @Override
    public int tickPlayerOILevelAdd() {
        return tickPlayerOILevelAdd;
    }

    @Override
    public int needOIRLevel() {
        return needOIRLevel;
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if(!(entity instanceof LivingEntity)) return;
        Random r = new Random();
        if(!world.isClientSide() && entity instanceof PlayerEntity && new OIMain.EntityOI().getPlayerOIResistanceLevel((PlayerEntity) entity,world) >= this.needOIRLevel){
            if(r.nextInt(100)+1 <= touchTickDamageProbability()) entity.hurt(Damage.ORIROCK_INFECTION,0.2F);
            return;
        }
        if(r.nextInt(100)+1 <= touchTickDamageProbability()) entity.hurt(Damage.ORIROCK_INFECTION,touchTickDamage());
        if(entity instanceof PlayerEntity && r.nextInt(10)==0)
            new OIMain.EntityOI().addPlayerOIPoint(entity,tickPlayerOILevelAdd);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

}
