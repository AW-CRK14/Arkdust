package com.ardc.arkdust.capability.health_system;

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
        INSTANCE.messageBuilder(HealthSystemDataPack.class,nextID())
                .encoder(HealthSystemDataPack::toBytes)
                .decoder(HealthSystemDataPack::new)
                .consumer(HealthSystemDataPack::handler)
                .add();
    }

    public static class HealthSystemDataPack {
        private final byte ori$level;
        private final int ori$point;

        public HealthSystemDataPack(byte ori$level, int ori$point){
            this.ori$level = ori$level;
            this.ori$point = ori$point;
        }

        public HealthSystemDataPack(PacketBuffer buffer){
            this.ori$level = buffer.readByte();
            this.ori$point = buffer.readInt();
        }

        public void toBytes(PacketBuffer buffer){
            buffer.writeByte(ori$level).writeInt(ori$point);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            context.get().enqueueWork(()-> {
                ClientPlayerEntity e = Minecraft.getInstance().player;
                if(e != null){
                    e.getCapability(CapabilityRegistry.HEALTH_SYSTEM_CAPABILITY).ifPresent((i)->{
                        i.ORI$setPoint(ori$point);
                        i.ORI$setRLevel(ori$level);
                        Utils.LOGGER.info("Player#" + e.getName().getString() + " get pack in client");
                        Utils.LOGGER.info("ORICapInClient:" + i.toString());
                    });
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
