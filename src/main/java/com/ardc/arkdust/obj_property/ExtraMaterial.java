package com.ardc.arkdust.obj_property;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class ExtraMaterial {

    public static final net.minecraft.block.material.Material TEST1 = (new Builder(MaterialColor.COLOR_BLUE)).flammable().build();
    public static final net.minecraft.block.material.Material STATIC_STRUCTURE_AIR = (new Builder(MaterialColor.NONE)).noCollider().notSolidBlocking().nonSolid().replaceable().build();
    public static final net.minecraft.block.material.Material LAB_STROCK = (new Builder(MaterialColor.COLOR_GRAY).notPushable().build());

    public static class Builder {
        private PushReaction pushReaction = PushReaction.NORMAL;
        private boolean blocksMotion = true;
        private boolean flammable;
        private boolean liquid;
        private boolean replaceable;
        private boolean solid = true;
        private final MaterialColor color;
        private boolean solidBlocking = true;

        public Builder(MaterialColor p_i48270_1_) {
            this.color = p_i48270_1_;
        }

        public Builder liquid() {
            this.liquid = true;
            return this;
        }

        public Builder nonSolid() {
            this.solid = false;
            return this;
        }

        public Builder noCollider() {
            this.blocksMotion = false;
            return this;
        }

        private Builder notSolidBlocking() {
            this.solidBlocking = false;
            return this;
        }

        protected Builder flammable() {
            this.flammable = true;
            return this;
        }

        public Builder replaceable() {
            this.replaceable = true;
            return this;
        }

        protected Builder destroyOnPush() {
            this.pushReaction = PushReaction.DESTROY;
            return this;
        }

        protected Builder notPushable() {
            this.pushReaction = PushReaction.BLOCK;
            return this;
        }

        public net.minecraft.block.material.Material build() {
            return new net.minecraft.block.material.Material(this.color, this.liquid, this.solid, this.blocksMotion, this.solidBlocking, this.flammable, this.replaceable, this.pushReaction);
        }
    }
}
