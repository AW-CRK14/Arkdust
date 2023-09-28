package com.ardc.arkdust.helper;

import net.minecraft.world.InteractionHand;

public interface ISkipShiftBlock {
    default boolean acceptHand(InteractionHand hand){return hand.equals(InteractionHand.MAIN_HAND);}
}
