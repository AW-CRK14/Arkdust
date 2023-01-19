package com.ardc.arkdust.capability.rdi_auth;

import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RDIAccountAuthCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    private IRDIAccountAuthCapability iCapability;

    @Nonnull
    IRDIAccountAuthCapability getOrCreateCapability(){
        if(iCapability == null){
            iCapability = new RDIAccountAuthCapability();
        }
        return iCapability;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }
}
