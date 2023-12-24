package com.ardc.arkdust.network.blockentity;

import com.ardc.arkdust.Utils;
import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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
                .consumerMainThread(Pack::handler)
                .add();
    }

    public static class Pack {
        private final int x;
        private final int y;
        private final int z;
        private final int count;

        public Pack(BlockPos pos, int count){
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
            this.count = count;
        }

        public Pack(FriendlyByteBuf buffer){
            this.x = buffer.readInt();
            this.y = buffer.readInt();
            this.z = buffer.readInt();
            this.count = buffer.readInt();
        }

        public void toBytes(FriendlyByteBuf buffer){
            buffer.writeInt(x).writeInt(y).writeInt(z).writeInt(count);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            context.get().enqueueWork(()-> {
                ClientLevel world = Minecraft.getInstance().level;
                BlockEntity entity = world.getBlockEntity(new BlockPos(x,y,z));
                if(entity instanceof BlueprintReduceBoxBE){
                    ((BlueprintReduceBoxBE) entity).setCount(count);
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}
