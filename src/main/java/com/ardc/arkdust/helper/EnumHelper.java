package com.ardc.arkdust.helper;

public class EnumHelper {
    public static <T extends Enum<T>> T valueOfOrDefault(Class<T> clazz,String name,T defaultObj){
        try{
            return Enum.valueOf(clazz,name);
        } catch (Throwable a){
            return defaultObj;
        }
    }
}
