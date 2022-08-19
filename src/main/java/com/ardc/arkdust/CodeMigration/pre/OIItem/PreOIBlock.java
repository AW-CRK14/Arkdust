package com.ardc.arkdust.CodeMigration.pre.OIItem;

import com.ardc.arkdust.CodeMigration.Damage;
import com.ardc.arkdust.CodeMigration.pre.PreBlock;
import com.ardc.arkdust.NewPlayingMethod.OriInfection.IOIBlock;
import com.ardc.arkdust.NewPlayingMethod.OriInfection.OIMain;
import net.minecraft.advancements.criterion.PlacedBlockTrigger;
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
    public PreOIBlock(Properties properties, float touchTickDamage,int tickPlayerOILevelAdd) {
        super(properties);
        this.touchTickDamage = touchTickDamage;
        this.tickPlayerOILevelAdd = tickPlayerOILevelAdd;
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
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if(!(entity instanceof LivingEntity)) return;
        Random r = new Random();
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
