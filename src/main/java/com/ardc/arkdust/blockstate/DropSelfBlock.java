package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.preobject.PreBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class DropSelfBlock extends PreBlock {
    public final ICreateDrops i;

    public DropSelfBlock(Properties properties){
        super(properties);
        i = (s,c)->Collections.singletonList(new ItemStack(this));
    }

    public DropSelfBlock(Properties properties, int number, Supplier<Item> item){
        super(properties);
        i = (s,c)->Collections.singletonList(new ItemStack(item.get(),number));
    }

    public DropSelfBlock(Properties properties, int number){
        super(properties);
        i = (s,c)->Collections.singletonList(new ItemStack(this,number));
    }

    public DropSelfBlock(Properties properties,ICreateDrops iCreateDrops){
        super(properties);
        i = iCreateDrops;
    }

    public interface ICreateDrops{
        List<ItemStack> create(BlockState state, LootParams context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {

        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return i.create(state,builder.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK));
    }
}
