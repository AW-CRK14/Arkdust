package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;

public interface IBlueprintItem {
    BlueprintType blueprintType();

    enum BlueprintType{
        PIECES,
        COMPLETE,
        GENERAL,
        UNKNOWN,
        SPECIAL,
        NULL;
        BlueprintType(){

        }
    }

     static boolean canUseForReduce(String id){
        return !id.equals(BlueprintType.PIECES.name()) && !id.equals(BlueprintType.UNKNOWN.name()) && !id.equals(BlueprintType.NULL.name());
    }
}
