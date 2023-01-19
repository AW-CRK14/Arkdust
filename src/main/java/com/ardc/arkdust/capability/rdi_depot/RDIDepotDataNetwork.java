package com.ardc.arkdust.capability.rdi_depot;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

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
                .consumer(RDIDepotDataPack::handler)
                .add();
    }

    public static class RDIDepotDataPack {
        private final CompoundNBT nbt;

        public RDIDepotDataPack(CompoundNBT nbt){
            this.nbt = nbt;
        }

        public RDIDepotDataPack(PacketBuffer buffer){
            this.nbt = buffer.readNbt();
        }

        public void toBytes(PacketBuffer buffer){
            buffer.writeNbt(nbt);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            context.get().enqueueWork(()-> {
                ClientPlayerEntity e = Minecraft.getInstance().player;
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
