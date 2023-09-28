package com.ardc.arkdust.capability;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.capability.rdi_auth.RDIAccountAuthDataNetwork;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.security.Provider;
import java.util.function.Function;
import java.util.function.Supplier;

public class AbsCapabilityProvider<T extends AbsCapabilityProvider.CommonEntityCap> implements ICapabilityProvider, INBTSerializable<CompoundTag> {



    public final Capability<T> capType;
    public final Supplier<T> createCap;

    public Function<Direction,T> directionCheckCap = (direction -> getOrCreateCapability());
    private T capability;

    public AbsCapabilityProvider(Capability<T> capability, Supplier<T> create){
        this.capType = capability;
        this.createCap = create;
    }

    public AbsCapabilityProvider(Capability<T> capability, Supplier<T> create, Function<Direction,T> directionCheck){
        this.capType = capability;
        this.createCap = create;
        this.directionCheckCap = directionCheck;
    }

    public <E> @NotNull LazyOptional<E> getCapability(@Nonnull Capability<E> cap, @Nullable Direction side) {
        return cap == capType ? LazyOptional.of(()->directionCheckCap.apply(side)).cast() : LazyOptional.empty();
    }

    @Nonnull
    T getOrCreateCapability(){
        if(capability == null){
            capability = createCap.get();
        }
        return capability;
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }

    public interface CommonEntityCap extends INBTSerializable<CompoundTag>{
        void sendToClient(ServerPlayer player);
    }
}
