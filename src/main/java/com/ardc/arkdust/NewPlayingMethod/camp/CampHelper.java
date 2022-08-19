package com.ardc.arkdust.NewPlayingMethod.camp;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

import java.util.ArrayList;
import java.util.List;

public class CampHelper {
    public static boolean ifEntityBelongToCamp(Camp camp, LivingEntity entity){//ͨ�������ж�ĳʵ���Ƿ���ĳ��Ӫ��
        Class<? extends LivingEntity> entityClass = entity.getClass();
//        if(entity instanceof PlayerEntity) entityClass = PlayerEntity.class;
        for(Class<? extends LivingEntity> i : camp.getCampEntityClassList()){
            if(i == entityClass){
                return true;
            }
        }
        return false;
    }
    public static boolean ifEntityBelongToCamp(List<Camp> camp, LivingEntity entity){//ͨ�������ж�ʵ������Ӫ�б�����Щ��Ӫ��
        for(Camp c:camp){
            if(ifEntityBelongToCamp(c,entity)) return true;
        }
        return false;
    }
    public static List<Camp> getEntityInCamp(LivingEntity entity){//ͨ�������ж�ʵ����������Ӫ����Щ��Ӫ��
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
        ListNBT listNBT = new ListNBT();//�����µ�ListNBT��
//        int i = 0;//���ڼ��㳤�ȣ���ȻҲ����ʹ��listNBT��.size(���Ժ˶Գ���ʹ��)
        if(campList != null) {
            for (Camp camp : campList) {
//                i++;
                listNBT.add(campToStringNBT(camp));//����ΪlistNBT���StringNBT������Ϊ��Ӫ��name
                System.out.println(campToStringNBT(camp));
            }
        }
        System.out.println(listNBT);
        nbt.put(addString + "Camp",listNBT);//��nbt�з���listNBT
//        nbt.putInt(addString + "Num",i);//��nbt�з�������õ�listNBT����
    }



    public static StringNBT campToStringNBT(Camp camp){
        return StringNBT.valueOf(camp.getCampName());//��ȡ��camp�����֣�������StringNBT
    }


    public static List<Camp> loadCampDataFromNBT(String addString, CompoundNBT nbt){
//        System.out.println("run loadCampDataFromNBT. KeyString:" + addString);
        INBT listNBT = nbt.get(addString + "Camp");//ͨ��nbt��ȡINBT��
//        System.out.println("  listNBT:" + listNBT);
        List<Camp> campList = new ArrayList<>();//�����µ�Camp��
        if(listNBT instanceof ListNBT){//���listNBTʵ����ListNBT
            int length = ((ListNBT) listNBT).size();//��ȡ����
//            if(length == nbt.getInt(addString + "Num")){//�����ó���У��
                for(int i = 0; i < length;i++){//����
                    String outString = ((ListNBT) listNBT).getString(i);//��ȡListNBT�е�ÿһ����Ϊ����
                    Camp stringSearchResult = stringToSearchCamp(outString);//ͨ������Ѱ��Camp
                    if(stringSearchResult!=null) campList.add(stringSearchResult);//����ҵ�����Camp�����б�
                }
//            }
        }
        return campList;//����Camp�б�
    }

    public static Camp stringToSearchCamp(String campName){
        for(Camp camp:Camp.values()){//����Camp�е�ÿһ��
            if(camp.getCampName().equals(campName)) return camp;//�ȶ�Name
        }
        return null;
    }

}
