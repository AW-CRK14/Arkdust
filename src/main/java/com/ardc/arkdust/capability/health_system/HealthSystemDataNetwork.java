package com.ardc.arkdust.capability.health_system;

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

public class HealthSystemDataNetwork {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID(){
        return ID++;
    }

    public static void registerMessage(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID,"network/health_system"),
                ()->VERSION,
                (v)->v.equals(VERSION),
                (v)->v.equals(VERSION)
        );
        INSTANCE.messageBuilder(Pack.class,nextID())
                .encoder(Pack::toBytes)
                .decoder(Pack::new)
                .consumerMainThread(Pack::handler)
                .add();
    }

    public static class Pack {
        private final CompoundTag tag;

        public Pack(CompoundTag tag){
            this.tag = tag;
        }

        public Pack(FriendlyByteBuf buffer){
            this.tag = buffer.readNbt();
        }

        public void toBytes(FriendlyByteBuf buffer){
            buffer.writeNbt(tag);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            context.get().enqueueWork(()-> {
                LocalPlayer e = Minecraft.getInstance().player;
                if(e != null){
                    e.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)->{
                        i.deserializeNBT(tag);
                        Utils.LOGGER.debug("[ArdNetwork-HealthSystem]Player#" + e.getName().getString() + " get pack from client");
                        Utils.LOGGER.debug("[ArdNetwork-HealthSystem]ORICapInClient:" + i);
                    });
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
