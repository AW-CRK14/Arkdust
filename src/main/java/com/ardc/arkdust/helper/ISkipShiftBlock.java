package com.ardc.arkdust.helper;

import net.minecraft.util.Hand;

public interface ISkipShiftBlock {
    default boolean acceptHand(Hand hand){return hand.equals(Hand.MAIN_HAND);}
}
