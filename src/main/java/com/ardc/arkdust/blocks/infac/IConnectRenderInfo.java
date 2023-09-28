package com.ardc.arkdust.blocks.infac;

import net.minecraft.world.level.block.Block;

public interface IConnectRenderInfo {
    ConnectRenderInfo getInfo();

    static boolean ignoreOrNot(Block block1, Block block2){
        if(block1 instanceof IConnectRenderInfo && block2 instanceof IConnectRenderInfo){
            ConnectRenderInfo a = ((IConnectRenderInfo) block1).getInfo();
            ConnectRenderInfo b = ((IConnectRenderInfo) block2).getInfo();
            return a.type == b.type && a.index == b.index;
        }
        return false;
    }

    class ConnectRenderInfo {
        public final ConnectRenderType type;
        public final int index;
        public ConnectRenderInfo(ConnectRenderType type, int index){
            this.type = type;
            this.index = index;
        }

        public boolean equals(Object obj){
            if(obj instanceof IConnectRenderInfo){
                ConnectRenderInfo info = ((IConnectRenderInfo) obj).getInfo();
                return info.type == this.type && info.index == this.index;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return type.hashCode() * 32 + index;
        }

        @Override
        public String toString() {
            return "ConnectRenderInfo{" +
                    "type=" + type +
                    ", index=" + index +
                    "}[hashcode:" + hashCode() +
                    ']';
        }
    }

    enum ConnectRenderType{
        IGNORE,
        DIRECTION_STATE
    }
}
