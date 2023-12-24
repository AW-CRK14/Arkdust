package com.ardc.arkdust.network.blockentity;

import com.ardc.arkdust.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class NoticeBlockSaveDataNetwork {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;

    public static int nextID(){
        return ID++;
    }

    public static void registerMessage(){
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Utils.MOD_ID,"block_save"),
                ()->VERSION,
                (v)->v.equals(VERSION),
                (v)->v.equals(VERSION)
        );
        INSTANCE.messageBuilder(Pack.class,nextID())
                .encoder(Pack::toBytes)
                .decoder(Pack::new)
                .consumerNetworkThread(Pack::handler)
                .add();
    }

    public static class Pack {
        private static final Logger LOGGER = LogManager.getLogger(Utils.getLogName("Network:NoticeBlockSave"));
        private final BlockPos pos;

        public Pack(BlockPos pos){
            this.pos = pos;
        }

        public Pack(FriendlyByteBuf buffer){
            this.pos = buffer.readBlockPos();
        }

        public void toBytes(FriendlyByteBuf buffer){
            buffer.writeBlockPos(pos);
        }

        public void handler(Supplier<NetworkEvent.Context> context){
            ServerPlayer player = context.get().getSender();
//            if(player != null){
//                BlockEntity be = player.level().getExistingBlockEntity(pos);
//                if(be != null) {
//                    be.setChanged();
//                    LOGGER.info("Server noticed block entity:{} at pos:{} in level:{} by player:{}",be, pos, player.level().dimension().location(), player.getName());
//                }else {
//                    LOGGER.warn("Server can't notice block entity at pos:{} in level:{} by player:{}", pos, player.level().dimension().location(), player.getName());
//                }
//            }
        }
    }
}
