package com.ardc.arkdust.helper;

import com.ardc.arkdust.Utils;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.function.Function;

public class ListAndMapHelper {

    //T[]��List<T>��ӳ��
    public static <T> List<T> toList(T[] l){
        return Arrays.stream(l).collect(ArrayList::new,ArrayList::add,ArrayList::addAll);
    }

    //�����б�
    public static <T> List<T> copyList(List<T> list){
        List<T> newList = new ArrayList<>(list);
        return newList;
    }

    //����б�����
    public static <T> void printList(List<T> list){
        int c = 0;
        StringBuilder output = new StringBuilder();
        for (T obj:list){
            c++;
            output.append("\n").append(c).append(":").append(obj);
        }
        Utils.LOGGER.info("[ArdHelper]"+output);
    }

    //ͨ���ַ���������Դλ���б�
    public static List<ResourceLocation> string2RLList(String... ss){
        List<ResourceLocation> list = new ArrayList<>();
        for(String s:ss){
            list.add(new ResourceLocation(Utils.MOD_ID,s));
        }
        return list;
    }

    //ͨ���ַ���������Դλ���б����ȫ��·��ǰ׺
    public static List<ResourceLocation> string2RLListWithPath(String path, String... ss){
        List<ResourceLocation> list = new ArrayList<>();
        for(String s:ss){
            list.add(new ResourceLocation(Utils.MOD_ID,path + s));
        }
        return list;
    }

    //�����б�
    public static <T> List<T> disorganizeList(Random random, List<T>... lists){
        List<T> list = new ArrayList<>();
        for (List<T> l : lists){
            if(l == null) continue;
            list.addAll(l);
        }
        Collections.shuffle(list,random);
        return list;
    }

    //��-�б� ��map��������б�����
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
