package com.ardc.arkdust.playmethod.blueprint;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlueprintTargetHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Map<ResourceLocation, BlueprintTarget> map = new HashMap<>();


    public static BlueprintTarget createInfo(ResourceLocation id, BlueprintTypeEnum type, BlueprintValueEnum value, int level, List<ResourceLocation> dependence){
        return new BlueprintTarget(id,type,value,level,dependence);
    }

    public static BlueprintTarget createInfo(ResourceLocation id, BlueprintTypeEnum type, BlueprintValueEnum value, int level){
        return new BlueprintTarget(id,type,value,level, Collections.emptyList());
    }

    //    public static void register(Collection<RegistryObject<Item>> items){
//        prepared.addAll(items);
//        LOGGER.info("[ArdReg-GeneralBlueprint]Put {} objects to prepare register list",items.size());
//    }

//    @SubscribeEvent
//    public static void registerObjects(FMLLoadCompleteEvent event){
//        int counter = 0;
//        for (RegistryObject<Item> item : prepared){
//            if(item.get() instanceof GeneralBlueprintItem){
//                GeneralBlueprintItem i = (GeneralBlueprintItem) item.get();
//                map.put(Pair.of(i.type,i.value),i);
//                counter++;
//                LOGGER.info("[ArdReg-GeneralBlueprint]Registered a general blueprint item{name:{}} for pair{type:{},value:{}}",i.getRegistryName(),i.type,i.value);
//            }else {
//                LOGGER.warn("[ArdReg-GeneralBlueprint]Item{name:{}} can't be registered,as it's not an instance of Class<GeneralBlueprintItem>",item.getId());
//            }
//        }
//        LOGGER.info("[ArdReg-GeneralBlueprint]Registered {} general blueprint item",counter);
//    }
}
