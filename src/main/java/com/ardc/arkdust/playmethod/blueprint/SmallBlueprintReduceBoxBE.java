package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.block_entity.BlueprintReduceBoxBE;
import net.minecraft.tileentity.TileEntityType;

public class SmallBlueprintReduceBoxBE extends BlueprintReduceBoxBE {
    public SmallBlueprintReduceBoxBE(TileEntityType<? extends BlueprintReduceBoxBE> type, int maxCount) {
        super(type, maxCount);
    }
}
