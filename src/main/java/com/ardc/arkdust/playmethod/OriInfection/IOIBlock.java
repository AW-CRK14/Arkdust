package com.ardc.arkdust.playmethod.OriInfection;

public interface IOIBlock{
    default boolean touchHurt(){return true;}

    float touchTickDamage();

    int touchTickDamageProbability();

    int tickPlayerOILevelAdd();

    int needOIRLevel();

//    int tickPlayerOILevelAddProbability();
}
