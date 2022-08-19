package com.ardc.arkdust.NewPlayingMethod.camp;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

import java.util.ArrayList;
import java.util.List;

public class CampHelper {
    public static boolean ifEntityBelongToCamp(Camp camp, LivingEntity entity){//通过遍历判断某实体是否在某阵营中
        Class<? extends LivingEntity> entityClass = entity.getClass();
//        if(entity instanceof PlayerEntity) entityClass = PlayerEntity.class;
        for(Class<? extends LivingEntity> i : camp.getCampEntityClassList()){
            if(i == entityClass){
                return true;
            }
        }
        return false;
    }
    public static boolean ifEntityBelongToCamp(List<Camp> camp, LivingEntity entity){//通过遍历判断实体在阵营列表中哪些阵营里
        for(Camp c:camp){
            if(ifEntityBelongToCamp(c,entity)) return true;
        }
        return false;
    }
    public static List<Camp> getEntityInCamp(LivingEntity entity){//通过遍历判断实体在所有阵营中哪些阵营里
        Class<? extends LivingEntity> entityClass = entity.getClass();
        List<Camp> list = new ArrayList<>();
        for(Camp camp : Camp.values()){
            for(Class<? extends LivingEntity> i : camp.getCampEntityClassList()){
                if(i == entityClass){
                    list.add(camp);
                }
            }
        }
        return list;
    }

    public static void saveCampDataToNBT(String addString, CompoundNBT nbt, List<Camp> campList){
        ListNBT listNBT = new ListNBT();//创建新的ListNBT表
//        int i = 0;//用于计算长度，当然也可以使用listNBT的.size(测试核对长度使用)
        if(campList != null) {
            for (Camp camp : campList) {
//                i++;
                listNBT.add(campToStringNBT(camp));//遍历为listNBT添加StringNBT，内容为阵营的name
                System.out.println(campToStringNBT(camp));
            }
        }
        System.out.println(listNBT);
        nbt.put(addString + "Camp",listNBT);//向nbt中放入listNBT
//        nbt.putInt(addString + "Num",i);//向nbt中放入测试用的listNBT长度
    }



    public static StringNBT campToStringNBT(Camp camp){
        return StringNBT.valueOf(camp.getCampName());//获取此camp的名字，并放入StringNBT
    }


    public static List<Camp> loadCampDataFromNBT(String addString, CompoundNBT nbt){
//        System.out.println("run loadCampDataFromNBT. KeyString:" + addString);
        INBT listNBT = nbt.get(addString + "Camp");//通过nbt获取INBT表
//        System.out.println("  listNBT:" + listNBT);
        List<Camp> campList = new ArrayList<>();//创建新的Camp表
        if(listNBT instanceof ListNBT){//如果listNBT实现了ListNBT
            int length = ((ListNBT) listNBT).size();//获取长度
//            if(length == nbt.getInt(addString + "Num")){//测试用长度校对
                for(int i = 0; i < length;i++){//遍历
                    String outString = ((ListNBT) listNBT).getString(i);//获取ListNBT中的每一项作为文字
                    Camp stringSearchResult = stringToSearchCamp(outString);//通过文字寻找Camp
                    if(stringSearchResult!=null) campList.add(stringSearchResult);//如果找到，将Camp加入列表
                }
//            }
        }
        return campList;//返回Camp列表
    }

    public static Camp stringToSearchCamp(String campName){
        for(Camp camp:Camp.values()){//遍历Camp中的每一项
            if(camp.getCampName().equals(campName)) return camp;//比对Name
        }
        return null;
    }

}
