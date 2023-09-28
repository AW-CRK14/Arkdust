package com.ardc.arkdust.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CapabilityHelper {
    public static <Q extends INBTSerializable<CompoundTag>> void playerCloneDefaultProgress(Capability<Q> capability, PlayerEvent.Clone event, boolean skipDeathClean){
        if (skipDeathClean || !event.isWasDeath()){
            LazyOptional<Q> oldCap = event.getOriginal().getCapability(capability);
            LazyOptional<Q> newCap = event.getEntity().getCapability(capability);
            if(oldCap.isPresent() && newCap.isPresent()){
                newCap.ifPresent((n)->oldCap.ifPresent((o)->n.deserializeNBT(o.serializeNBT())));
            }
        }
    }
}
