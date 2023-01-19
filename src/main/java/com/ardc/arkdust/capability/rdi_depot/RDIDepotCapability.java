package com.ardc.arkdust.capability.rdi_depot;

import com.ibm.icu.impl.Pair;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collections;
import java.util.List;

public class RDIDepotCapability implements IRDIDepotCapability {
    private CompoundNBT nbt;

    public RDIDepotCapability(){
        this.nbt = new CompoundNBT();
    }

    public RDIDepotCapability(CompoundNBT nbt){
        this.nbt = nbt;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    @Override
    public CompoundNBT getNbt() {
        return nbt;
    }

    public int putObject(DepotObject object, int num){
        nbt.putInt(object.NAME,Math.max((nbt.contains(object.NAME) ? nbt.getInt(object.NAME) : 0) + num,0));
        return nbt.getInt(object.NAME);
    }

    public int getObject(DepotObject object){
        return nbt.getInt(object.NAME);
    }

    public int reduceObject(DepotObject object, int num){
        return putObject(object,-num);
    }

    @Override
    public void createNBT(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public List<Pair<DepotObject,Integer>> getDepotObjList(){
        List<Pair<DepotObject,Integer>> list = new java.util.ArrayList<>(Collections.emptyList());
        for(DepotObject obj:DepotObject.values()){
            int num = nbt.getInt(obj.NAME);
            if(num > 0){
                list.add(Pair.of(obj,num));
            }
        }
        return list;
    }

    public enum DepotObject {
        TEST_OBJ("test_obj"),
        LMB("lmb"),
        ORIGINITE_PRIME("originite_prime"),
        ORUNDUM("orundum"),
        ORIGINIUM_INGOT("originium_ingot");

        public final String NAME;
        DepotObject(String name){
            this.NAME = name;
        }

        public TranslationTextComponent getNameTrans(){
            return new TranslationTextComponent("depot_obj/" + NAME);
        }

        public static DepotObject getObj(String name){
            for (DepotObject obj : DepotObject.values()){
                if(obj.NAME.equals(name))
                    return obj;
            }
            return null;
        }
    }
}
