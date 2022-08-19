package com.ardc.arkdust.NewPlayingMethod.OriInfection;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldOIData extends WorldSavedData {
    private static final String NAME = "ArdWorldOI";
    private boolean WorldOIState = false;
    private double WorldOILevelConstant;//世界感染常量
    private double WorldOILevelVariable;//世界感染变量
    private CompoundNBT PlayerDataNbt = new CompoundNBT();//存储玩家感染与抗性信息
    private boolean debugMode = false;

    public WorldOIData(){
        super(NAME);
    }

    public static WorldOIData get(World worldIn){
        if (worldIn instanceof ServerWorld){
            ServerWorld world = worldIn.getServer().getLevel(World.OVERWORLD);
            DimensionSavedDataManager s = world.getDataStorage();
            return s.computeIfAbsent(WorldOIData::new,NAME);
        }
        throw new RuntimeException("you shouldn't use the client world");
    }

    public void load(CompoundNBT nbt){
        WorldOIState = nbt.getBoolean("WorldOIOn");
        WorldOILevelConstant = nbt.getDouble("WorldOILevelConstant");
        WorldOILevelVariable = nbt.getDouble("WorldOILevelVariable");
        INBT nbt1 = nbt.get("PlayerOINBT");
        if(nbt1 instanceof CompoundNBT){
            PlayerDataNbt = ((CompoundNBT) nbt1).copy();
        }else{
            PlayerDataNbt = new CompoundNBT();
        }
    }
    
    public CompoundNBT save(CompoundNBT nbt){
        nbt.putBoolean("WorldOIOn", WorldOIState);
        nbt.putDouble("WorldOILevelConstant",WorldOILevelConstant);
        nbt.putDouble("WorldOILevelVariable",WorldOILevelVariable);
        nbt.put("PlayerOINBT",PlayerDataNbt);
        return nbt;
    }

    public void worldOIBegin(World world){
        if(!WorldOIState){//世界OI状态为否时执行
            WorldOIState = true;//设置世界OI状态
            setDirty();
            if (!world.isClientSide()){//发包告知全体玩家世界OI已启用
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                if(server != null){server.getPlayerList().broadcastMessage(new TranslationTextComponent("pma.oi.worldOIOn").withStyle(TextFormatting.RED), ChatType.CHAT, Util.NIL_UUID);}
            }
        }
    }

    public void worldOIReset(World world){
        if(debugMode) {
            if (WorldOIState) {
                WorldOIState = false;
                WorldOILevelVariable = 0;
                setDirty();
            }
            System.out.println("世界源石感染已重置。当前状态：" + WorldOIState);
            return;
        }
        System.out.println("DebugMode尚未启用。");
    }

    public void setWorldOILevelVariableForTest(World world,double addNum){
        if(debugMode) {
            WorldOILevelVariable += addNum;
        }
    }

    public boolean getWorldOIState(){
        return WorldOIState;
    }

    public double getEnvironmentOILevel(double x,double y,double z){
        return getWorldOILevel() + getBiomeOILevel(x, y, z);
    }

    public double getWorldOILevel(){
        return WorldOILevelConstant + WorldOILevelVariable;
    }

    public double getWorldOILevelConstant(){
        return WorldOILevelConstant;
    }

    public double getWorldOILevelVariable(){
        return WorldOILevelVariable;
    }

    public double getBiomeOILevel(double x,double y,double z){//TODO
        return 0;
    }

    @Deprecated
    public double getPlayerOILevel(PlayerEntity player){//弃用，转移至玩家nbt
        if(player == null)
            return 0;
        return 0;
    }

    public void addWorldOILevelConstant(double add){
        WorldOILevelConstant += add;
        setDirty();
    }

    public void addWorldOILevelVariable(double add){
        WorldOILevelVariable += add;
        setDirty();
    }

    public void minusWorldOILevelVariable(double minus){
        WorldOILevelVariable -= minus;
        setDirty();
    }

    public CompoundNBT getPlayerOINBT(PlayerEntity entity){
        if(entity != null){
            UUID playerUUID = entity.getUUID();
            if(PlayerDataNbt != null && PlayerDataNbt.contains(playerUUID.toString()))
                return PlayerDataNbt.getCompound(playerUUID.toString());
            CompoundNBT newNBT = new CompoundNBT();
            newNBT.putInt("ResistanceLevel",0);
            newNBT.putInt("OIPoint",0);
            newNBT.putInt("ResistanceLevel",0);
            newNBT.putInt("OILevel",0);
            newNBT.putInt("OIPointOverflow",0);
            newNBT.putInt("OILevelUpRequire",new OIMain.EntityOI().run1(1));
            return newNBT;
        }
        return null;
    }

    public void savePlayerOINBT(PlayerEntity entity,CompoundNBT nbt){
        if(entity == null) return;
        UUID playerUUID = entity.getUUID();
        PlayerDataNbt.put(playerUUID.toString(),nbt);
        setDirty();
    }

    public int getPlayerOIRLevel(PlayerEntity entity){
        CompoundNBT nbt = getPlayerOINBT(entity);
        if(nbt==null)
            return 0;
        return nbt.getInt("ResistanceLevel");
    }

    public int getPlayerOIPoint(PlayerEntity entity){
        CompoundNBT nbt = getPlayerOINBT(entity);
        if(nbt==null)
            return 0;
        return nbt.getInt("OIPoint");
    }

    public void addPlayerOIRLevel(PlayerEntity entity,int add){
        int r = getPlayerOINBT(entity).getInt("ResistanceLevel");
        getPlayerOINBT(entity).putInt("ResistanceLevel",Math.max(0,r+add));
        setDirty();
    }

    public void addPlayerOIPoint(PlayerEntity entity,int add){
        CompoundNBT nbt = getPlayerOINBT(entity);
        int r = nbt.getInt("OIPoint");
        nbt.putInt("OIPoint",Math.max(0,r+add));
        levelRun(nbt);
        savePlayerOINBT(entity,nbt);
    }

    public void setPlayerOIPoint(PlayerEntity entity,int set){
        CompoundNBT nbt = getPlayerOINBT(entity);
        nbt.putInt("OIPoint",Math.max(0,set));
        levelRun(nbt);
        savePlayerOINBT(entity,nbt);
    }

    private void levelRun(CompoundNBT nbt){
       List<Integer> list = new OIMain.EntityOI().run2(nbt.getInt("OIPoint"));
       nbt.putInt("OILevel",list.get(0));
       nbt.putInt("OIPointOverflow",list.get(1));
       nbt.putInt("OILevelUpRequire",list.get(2));
    }

    public List<Integer> getOIDataList(PlayerEntity entity){
        List<Integer> list = new ArrayList<>();
        list.add(0,getPlayerOINBT(entity).getInt("OILevel"));
        list.add(1,getPlayerOINBT(entity).getInt("OIPointOverflow"));
        list.add(2,getPlayerOINBT(entity).getInt("OILevelUpRequire"));
        list.add(3,getPlayerOINBT(entity).getInt("OIPoint"));
        return list;
    }
}
