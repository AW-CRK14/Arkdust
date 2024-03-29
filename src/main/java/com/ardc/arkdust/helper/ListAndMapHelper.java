package com.ardc.arkdust.helper;

import com.ardc.arkdust.Utils;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.function.Function;

public class ListAndMapHelper {

    //T[]到List<T>的映射
    public static <T> List<T> toList(T[] l){
        return Arrays.stream(l).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
    }

    //复制列表
    public static <T> List<T> copyList(List<T> list){
        List<T> newList = new ArrayList<>(list);
        return newList;
    }

    //输出列表内容
    public static <T> void printList(List<T> list){
        int c = 0;
        StringBuilder output = new StringBuilder();
        for (T obj:list){
            c++;
            output.append("\n").append(c).append(":").append(obj);
        }
        Utils.LOGGER.info("[ArdHelper]"+output);
    }

    //通过字符串创建资源位置列表
    public static List<ResourceLocation> string2RLList(String... ss){
        List<ResourceLocation> list = new ArrayList<>();
        for(String s:ss){
            list.add(new ResourceLocation(Utils.MOD_ID,s));
        }
        return list;
    }

    //通过字符串创建资源位置列表并添加全局路径前缀
    public static List<ResourceLocation> string2RLListWithPath(String path, String... ss){
        List<ResourceLocation> list = new ArrayList<>();
        for(String s:ss){
            list.add(new ResourceLocation(Utils.MOD_ID,path + s));
        }
        return list;
    }

    //打乱列表
    public static <T> List<T> disorganizeList(Random random, List<T>... lists){
        List<T> list = new ArrayList<>();
        for (List<T> l : lists){
            if(l == null) continue;
            list.addAll(l);
        }
        Collections.shuffle(list,random);
        return list;
    }

    //键-列表 的map尝试添加列表内容
    public static <T,K> void tryAddElementToMapList(Map<K,List<T>> map,K key,List<T> obj){
        if(map.containsKey(key)) map.get(key).addAll(obj);
        else map.put(key,copyList(obj));
    }

    public static <T,K> void tryDeleteElementToMapList(Map<K,List<T>> map,K key,List<T> obj){
        if(map.containsKey(key)) map.get(key).removeAll(obj);
        else map.put(key,new ArrayList<>());
    }

    public static <T,K,C> void tryTransListToMapList(Map<K,List<T>> map, List<C> list, Function<C,? extends K> createKey,Function<C,T> createValue){
        for (C c:list){
            K key = createKey.apply(c);
            T value = createValue.apply(c);
            if(key!=null && value!=null){
                if(map.containsKey(key)) map.get(key).add(value);
                else {
                    List<T> values = new ArrayList<>();
                    values.add(value);
                    map.put(key,values);
                }
            }
        }
    }


    public static <T,K> List<T> getNonnullListInMap(Map<K,List<T>> map, K key){
        return map.get(key) == null ? Collections.EMPTY_LIST : map.get(key);
    }

    public static <T,K> void tryAddElementToMapList(Map<K,List<T>> map,K key,T... obj){
        tryAddElementToMapList(map,key,Arrays.asList(obj));
    }
}
