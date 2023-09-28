package com.ardc.arkdust.block_entity;

import com.ardc.arkdust.enums.BlueprintTypeEnum;
import com.ardc.arkdust.enums.BlueprintValueEnum;
import com.ardc.arkdust.helper.EnumHelper;
import com.ardc.arkdust.playmethod.blueprint.BlueprintItem;
import com.ardc.arkdust.playmethod.blueprint.IBlueprintItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlueprintReduceBoxBE extends BlockEntity {
    protected BlueprintValueEnum value = BlueprintValueEnum.NULL;
    protected BlueprintTypeEnum type = BlueprintTypeEnum.NULL;
    protected final int maxContain;
    protected int count;

    public BlueprintReduceBoxBE(BlockEntityType<? extends BlueprintReduceBoxBE> type, BlockPos pos,BlockState state, int maxCount) {
        super(type,pos,state);
        this.maxContain = maxCount;
    }


    public int addBlueprint(ItemStack stack, boolean shift){
        if(test() || !(stack.getItem() instanceof BlueprintItem))
            return 0;
        CompoundTag nbt = stack.getOrCreateTagElement("blueprint");
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
            setChanged();
            return reCount;
        }
        return 0;
    }

    public boolean allowToPut(CompoundTag nbt){
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
    public void load(CompoundTag nbt) {
        setValue(EnumHelper.valueOfOrDefault(BlueprintValueEnum.class,nbt.getString("value"),BlueprintValueEnum.NULL));
        setObjType(EnumHelper.valueOfOrDefault(BlueprintTypeEnum.class,nbt.getString("type"),BlueprintTypeEnum.NULL));
        setCount(nbt.getInt("count"));
        test();
        super.load(nbt);
    }

//    @Override
    public void saveAdditional(CompoundTag nbt) {
        nbt.putString("value", getValue().name());
        nbt.putInt("count", getCount());
        nbt.putString("type", getObjType().name());
        super.saveAdditional(nbt);
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
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }


    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}