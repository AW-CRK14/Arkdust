package com.ardc.arkdust;

import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.registry.ItemRegistry;
import com.ardc.arkdust.registry.TileEntityTypeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Utils.MOD_ID)
public class Arkdust {
    public static final Logger LOGGER = LogManager.getLogger(Arkdust.class);

    public Arkdust(){
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());//items类注册入mod主线
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());//blocks类注册入mod主线
        TileEntityTypeRegistry.TE.register(FMLJavaModLoadingContext.get().getModEventBus());//方块实体注册入mod主线
    }
}
