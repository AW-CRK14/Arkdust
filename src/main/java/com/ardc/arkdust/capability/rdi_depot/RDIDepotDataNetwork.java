package com.ardc.arkdust.capability.rdi_depot;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class RDIDepotDataNetwork {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID(){
        return ID++;
    }

    public static void registerMessage(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID,"network/rdi_depot"),
                ()->VERSION,
                (v)->v.equals(VERSION),
                (v)->v.equals(VERSION)
        );
        INSTANCE.messageBuilder(RDIDepotDataPack.class,nextID())
                .encoder(RDIDepotDataPack::toBytes)
                .decoder(RDIDepotDataPack::new)
                .consumerMainThread(RDIDepotDataPack::handler)
                .add();
    }

    public static class RDIDepotDataPack {
        private final CompoundTag nbt;

        public RDIDepotDataPack(CompoundTag nbt){
            this.nbt = nbt;
        }

        public RDIDepotDataPack(FriendlyByteBuf buffer){
            this.nbt = buffer.readNbt();
        }

        public void toBytes(FriendlyByteBuf buffer){
            buffer.writeNbt(nbt);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            context.get().enqueueWork(()-> {
                LocalPlayer e = Minecraft.getInstance().player;
                if(e != null){
                    e.getCapability(CapabilityRegistry.RDI_DEPOT_CAPABILITY).ifPresent((i)->{
                        i.createNBT(nbt);
//                        Utils.LOGGER.info("Player#" + e.getName().getString() + " get pack in client");
//                        Utils.LOGGER.info("AccAuthCapInClient:" + i.toString());
                    });
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
