package com.ardc.arkdust.playmethod.oi.ori_infection;

public interface IOIBlock{
    default boolean touchHurt(){return true;}

    float touchTickDamage();

    float touchTickDamageProbability();

    int tickPlayerOIPointAdd();

    int needOIRLevel();

//    int tickPlayerOILevelAddProbability();
}
