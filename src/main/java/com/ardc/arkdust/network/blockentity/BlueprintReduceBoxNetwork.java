package com.ardc.arkdust.network.blockentity;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import com.ardc.arkdust.registry.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class BlueprintReduceBoxNetwork {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID(){
        return ID++;
    }

    public static void registerMessage(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID,"network/block_entity/blueprint_reduce"),
                ()->VERSION,
                (v)->v.equals(VERSION),
                (v)->v.equals(VERSION)
        );
        INSTANCE.messageBuilder(Pack.class,nextID())
                .encoder(Pack::toBytes)
                .decoder(Pack::new)
                .consumer(Pack::handler)
                .add();
    }

    public static class Pack {
        private final int x;
        private final int y;
        private final int z;
        private final int count;

        public Pack(BlockPos pos,int count){
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            this.count = count;
        }

        public Pack(PacketBuffer buffer){
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
            this.count = buffer.readInt();
        }

        public void toBytes(PacketBuffer buffer){
            buffer.writeInt(x).writeInt(y).writeInt(z).writeInt(count);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            context.get().enqueueWork(()-> {
                ClientWorld world = Minecraft.getInstance().level;
                TileEntity entity = world.getBlockEntity(new BlockPos(x,y,z));
                if(entity instanceof BlueprintReduceBoxBE){
                    ((BlueprintReduceBoxBE) entity).setCount(count);
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
