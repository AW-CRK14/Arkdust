package com.ardc.arkdust.code_migration;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

public class Material{

    public static final net.minecraft.block.material.Material TEST1 = (new Builder(MaterialColor.COLOR_BLUE)).flammable().build();
    public static final net.minecraft.block.material.Material TEST2 = (new Builder(MaterialColor.COLOR_ORANGE)).flammable().build();
    public static final net.minecraft.block.material.Material TEST3 = (new Builder(MaterialColor.COLOR_GREEN)).build();
    public static final net.minecraft.block.material.Material TEST4 = (new Builder(MaterialColor.COLOR_RED)).replaceable().build();


    private final MaterialColor color;
    private final PushReaction pushReaction;
    private final boolean blocksMotion;
    private final boolean flammable;
    private final boolean liquid;
    private final boolean solidBlocking;
    private final boolean replaceable;
    private final boolean solid;
    public Material(MaterialColor p_i232146_1_, boolean p_i232146_2_, boolean p_i232146_3_, boolean p_i232146_4_, boolean p_i232146_5_, boolean p_i232146_6_, boolean p_i232146_7_, PushReaction p_i232146_8_) {
        this.color = p_i232146_1_;
        this.liquid = p_i232146_2_;
        this.solid = p_i232146_3_;
        this.blocksMotion = p_i232146_4_;
        this.solidBlocking = p_i232146_5_;
        this.flammable = p_i232146_6_;
        this.replaceable = p_i232146_7_;
        this.pushReaction = p_i232146_8_;
    }

    public boolean isLiquid() {
        return this.liquid;
    }

    public boolean isSolid() {
        return this.solid;
    }

    public boolean blocksMotion() {
        return this.blocksMotion;
    }

    public boolean isFlammable() {
        return this.flammable;
    }

    public boolean isReplaceable() {
        return this.replaceable;
    }

    public boolean isSolidBlocking() {
        return this.solidBlocking;
    }

    public PushReaction getPushReaction() {
        return this.pushReaction;
    }

    public MaterialColor getColor() {
        return this.color;
    }

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
