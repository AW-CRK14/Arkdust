package com.ardc.arkdust.NewPlayingMethod.OriInfection;

public interface IOIBlock{
    default boolean touchHurt(){return true;}

    float touchTickDamage();

    int touchTickDamageProbability();

    int tickPlayerOILevelAdd();

//    int tickPlayerOILevelAddProbability();
}
