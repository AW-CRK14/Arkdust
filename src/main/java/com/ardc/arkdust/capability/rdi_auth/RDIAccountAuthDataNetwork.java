package com.ardc.arkdust.capability.rdi_auth;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class RDIAccountAuthDataNetwork {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID(){
        return ID++;
    }

    public static void registerMessage(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID,"network/rdi_account_auth"),
                ()->VERSION,
                (v)->v.equals(VERSION),
                (v)->v.equals(VERSION)
        );
        INSTANCE.messageBuilder(RDIAccountAuthDataPack.class,nextID())
                .encoder(RDIAccountAuthDataPack::toBytes)
                .decoder(RDIAccountAuthDataPack::new)
                .consumer(RDIAccountAuthDataPack::handler)
                .add();
    }

    public static class RDIAccountAuthDataPack {
        private final int exp;
        private final int sanity;

        public RDIAccountAuthDataPack(int exp, int sanity){
            this.exp = exp;
            this.sanity = sanity;
        }

        public RDIAccountAuthDataPack(PacketBuffer buffer){
            this.exp = buffer.readInt();
            this.sanity = buffer.readInt();
        }

        public void toBytes(PacketBuffer buffer){
            buffer.writeInt(exp).writeInt(sanity);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            context.get().enqueueWork(()-> {
                ClientPlayerEntity e = Minecraft.getInstance().player;
                if(e != null){
                    e.getCapability(CapabilityRegistry.RDI_ACCOUNT_AUTH_CAPABILITY).ifPresent((i)->{
                        i.setAExp(exp);
                        i.setSanity(sanity);
//                        Utils.LOGGER.info("Player#" + e.getName().getString() + " get pack in client");
//                        Utils.LOGGER.info("AccAuthCapInClient:" + i.toString());
                    });
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
