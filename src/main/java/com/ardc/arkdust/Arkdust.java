package com.ardc.arkdust;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Utils.MOD_ID)
public class Arkdust {
    public static final Logger LOGGER = LogManager.getLogger(Arkdust.class);

    public Arkdust(){
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());//items类注册入mod主线
        BlockRegistry.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());//items类注册入mod主线
    }
}
