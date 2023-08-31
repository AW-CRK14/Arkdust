package com.ardc.arkdust.helper;

import com.ardc.arkdust.playmethod.story.Story;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.StackLocatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArdRegHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    public static class RegistryContainer<T>{
        List<T> list = new ArrayList<>();
        Class<?> regFrom;
        Map<ResourceLocation,T> map;
        IGetRegObjName<T> nameSupplier;

        public RegistryContainer(Map<ResourceLocation,T> map,IGetRegObjName<T> nameSupplier){
            this.regFrom = StackLocatorUtil.getCallerClass(2);
            this.map = map;
            this.nameSupplier = nameSupplier;
        }

        public T register(T t){
            list.add(t);
            return t;
        }

        public void pushToRegister(){
            LOGGER.info("[ArdReg-RegContainer]RegCont push {} {}",list.size(),regFrom);
            registry(regFrom,list,map,nameSupplier);
        }

        public interface IPushToRegister<B>{
            void call(List<B> list,Class<?> regFrom);
        }
    }

    public static <T> void registry(Class<?> regFrom, List<T> objects, Map<ResourceLocation,T> map,IGetRegObjName<T> nameSupplier){
        if(objects.isEmpty()) return;
        String c = objects.get(0).getClass().getSimpleName();
        int counter = 0;
        for (T o: objects){
            ResourceLocation name = nameSupplier.call(o);
            if(map.containsKey(name)) {
                LOGGER.warn("[ArdReg-{}]{}({}) can't be registered as it's name was used.",c,c,name);
                continue;
            }
            map.put(name,o);
            counter++;
            LOGGER.debug("[ArdReg-{}]{}({}) registered.",c,c,name);
        }
        LOGGER.info("[ArdReg-{}]{} objects have been registered from {}",c,counter,regFrom);
    }

    public interface IGetRegObjName<T>{
        ResourceLocation call(T obj);
    }

}
