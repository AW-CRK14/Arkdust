package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.EnumHelper;
import com.ardc.arkdust.playmethod.blueprint.IBlueprintItem;
import com.ardc.arkdust.playmethod.blueprint.BlueprintItem;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BlueprintReduceBoxBE extends TileEntity {
    protected BlueprintValueEnum value;
    protected BlueprintTypeEnum type;
    protected final int maxContain;
    protected int count;

    public BlueprintReduceBoxBE(TileEntityType<? extends BlueprintReduceBoxBE> type, int maxCount) {
        super(type);
        initialize();
        this.maxContain = maxCount;
    }

    private void initialize(){
        setValue(BlueprintValueEnum.NULL);
        setObjType(BlueprintTypeEnum.NULL);
    }

    public int addBlueprint(ItemStack stack,boolean shift){
        if(test() || !(stack.getItem() instanceof BlueprintItem))
            return 0;
        CompoundNBT nbt = stack.getOrCreateTagElement("blueprint");
        if(allowToPut(nbt)){
            int askedCount = shift ? Math.min((int) Math.ceil((double) (getMaxContain() - getCount()) / nbt.getInt("weight")),stack.getCount()) : 1;
            if(value == BlueprintValueEnum.NULL || type == BlueprintTypeEnum.NULL){
                setValue(EnumHelper.valueOfOrDefault(BlueprintValueEnum.class,nbt.getString("value"),BlueprintValueEnum.COMMON));
                setObjType(EnumHelper.valueOfOrDefault(BlueprintTypeEnum.class,nbt.getString("type"), BlueprintTypeEnum.OBJ));
            }
            stack.shrink(askedCount);
            setCount(getCount() + askedCount * nbt.getInt("weight"));
            int reCount = Math.max(getCount() - getMaxContain(),0);
            test();
            return reCount;
        }
        return 0;
    }

    public boolean allowToPut(CompoundNBT nbt){
//        return !item.pieces() && (getValue() == BlueprintValueEnum.NULL || getValue() == item.value()) && (getBlueprintType() == BlueprintTypeEnum.NULL || getBlueprintType() == item.type());
        return  IBlueprintItem.canUseForReduce(nbt.getString("blueprint_type")) &&
                (getValue() == BlueprintValueEnum.NULL || getValue().name().equals(nbt.getString("value"))) &&
                (getObjType() == BlueprintTypeEnum.NULL || getObjType().name().equals(nbt.getString("type")) ||
                        (nbt.getString("type").equals(BlueprintTypeEnum.NULL.name()) && getObjType() != BlueprintTypeEnum.NULL));
    }

    public boolean test(){
        if(getCount() >= getMaxContain()){
            setCount(getMaxContain());
            return true;
        }
        return false;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        setValue(EnumHelper.valueOfOrDefault(BlueprintValueEnum.class,nbt.getString("value"),BlueprintValueEnum.NULL));
        setObjType(EnumHelper.valueOfOrDefault(BlueprintTypeEnum.class,nbt.getString("type"),BlueprintTypeEnum.NULL));
        setCount(nbt.getInt("count"));
        test();
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putString("value", getValue().name());
        nbt.putInt("count", getCount());
        nbt.putString("type", getObjType().name());
        return super.save(nbt);
    }


    public BlueprintValueEnum getValue() {
        return value;
    }

    public void setValue(BlueprintValueEnum value) {
        this.value = value;
    }

    public BlueprintTypeEnum getObjType() {
        return type;
    }

    public void setObjType(BlueprintTypeEnum type) {
        this.type = type;
    }

    public int getMaxContain() {
        return maxContain;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state,tag);
        super.handleUpdateTag(state, tag);
    }
}