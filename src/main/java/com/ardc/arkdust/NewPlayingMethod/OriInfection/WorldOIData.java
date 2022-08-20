package com.ardc.arkdust.NewPlayingMethod.OriInfection;

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
    private CompoundNBT PlayerOIDataNbt;//存储玩家感染信息
    private CompoundNBT PlayerOIRDataNbt;//存储玩家抗性信息
    private boolean debugMode = false;

    public WorldOIData() {
        super(NAME);
    }

    //WARN 小心变量null造成的未初始化报错，玩家数据要调用savePlayerOINBT否则不会被保存

    //前置内容
    public static WorldOIData get(World worldIn) {
        if (worldIn instanceof ServerWorld) {
            ServerWorld world = worldIn.getServer().getLevel(World.OVERWORLD);
            DimensionSavedDataManager s = world.getDataStorage();
            return s.computeIfAbsent(WorldOIData::new, NAME);
        }
        throw new RuntimeException("you shouldn't use the client world");
    }
    public void load(CompoundNBT nbt) {
        WorldOIState = nbt.getBoolean("WorldOIOn");
        WorldOILevelConstant = nbt.getDouble("WorldOILevelConstant");
        WorldOILevelVariable = nbt.getDouble("WorldOILevelVariable");
        INBT nbt1 = nbt.get("PlayerOINBT");
        if (nbt1 instanceof CompoundNBT) {
            PlayerOIDataNbt = ((CompoundNBT) nbt1).copy();
        } else {
            PlayerOIDataNbt = new CompoundNBT();
        }
        INBT nbt2 = nbt.get("PlayerOIRNBT");
        if (nbt2 instanceof CompoundNBT){
            PlayerOIRDataNbt = ((CompoundNBT) nbt2).copy();
        } else {
            PlayerOIRDataNbt = new CompoundNBT();
        }
    }
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putBoolean("WorldOIOn", WorldOIState);
        nbt.putDouble("WorldOILevelConstant", WorldOILevelConstant);
        nbt.putDouble("WorldOILevelVariable", WorldOILevelVariable);
        nbt.put("PlayerOINBT", PlayerOIDataNbt);
        nbt.put("PlayerOIRNBT",PlayerOIRDataNbt);
        return nbt;
    }
    public CompoundNBT getPlayerOINBT(PlayerEntity entity) {
        if (entity != null) {
            UUID playerUUID = entity.getUUID();
            if(PlayerOIDataNbt == null)
                PlayerOIDataNbt = new CompoundNBT();
            if (PlayerOIDataNbt.contains(playerUUID.toString()))
                return PlayerOIDataNbt.getCompound(playerUUID.toString());
            setDirty();
            CompoundNBT newNBT = new CompoundNBT();
            newNBT.putInt("OIPoint", 0);
            newNBT.putInt("ResistanceLevel", 0);
            newNBT.putInt("OILevel", 0);
            newNBT.putInt("OIPointOverflow", 0);
            newNBT.putInt("OILevelUpRequire", new OIMain.EntityOI().run1(1));
            return newNBT;
        }
        return null;
    }
    public void savePlayerOINBT(PlayerEntity entity, CompoundNBT nbt) {
        if (entity == null) return;
        UUID playerUUID = entity.getUUID();
        PlayerOIDataNbt.put(playerUUID.toString(), nbt);
        setDirty();
    }

    //世界感染状态相关
    public void worldOIBegin(World world) {
        if (!WorldOIState) {//世界OI状态为否时执行
            WorldOIState = true;//设置世界OI状态
            setDirty();
            if (!world.isClientSide()) {//发包告知全体玩家世界OI已启用
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                if (server != null) {
                    server.getPlayerList().broadcastMessage(new TranslationTextComponent("pma.oi.worldOIOn").withStyle(TextFormatting.RED), ChatType.CHAT, Util.NIL_UUID);
                }
            }
        }
    }
    public void worldOIReset() {
        if (debugMode) {
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
    public boolean getWorldOIState() {
        return WorldOIState;
    }

    //世界感染值相关 TODO 群系感染，世界感染变动
    public void setWorldOILevelVariableForTest(World world, double addNum) {
        if (debugMode) {
            WorldOILevelVariable += addNum;
        }
    }
    public double getEnvironmentOILevel(double x, double y, double z) {
        return getWorldOILevel() + getBiomeOILevel(x, y, z);
    }
    public double getWorldOILevel() {
        return WorldOILevelConstant + WorldOILevelVariable;
    }
    public double getWorldOILevelConstant() {
        return WorldOILevelConstant;
    }
    public double getWorldOILevelVariable() {
        return WorldOILevelVariable;
    }
    public double getBiomeOILevel(double x, double y, double z) {//TODO
        return 0;
    }
    public void addWorldOILevelConstant(double add) {
        WorldOILevelConstant += add;
        setDirty();
    }
    public void addWorldOILevelVariable(double add) {
        WorldOILevelVariable += add;
        setDirty();
    }
    public void minusWorldOILevelVariable(double minus) {
        WorldOILevelVariable -= minus;
        setDirty();
    }

    //玩家感染值有关
    public int getPlayerOIPoint(PlayerEntity entity) {
        CompoundNBT nbt = getPlayerOINBT(entity);
        if (nbt == null)
            return 0;
        return nbt.getInt("OIPoint");
    }
    public List<Integer> getOIDataList(PlayerEntity entity) {
        List<Integer> list = new ArrayList<>();
        list.add(0, getPlayerOINBT(entity).getInt("OILevel"));
        list.add(1, getPlayerOINBT(entity).getInt("OIPointOverflow"));
        list.add(2, getPlayerOINBT(entity).getInt("OILevelUpRequire"));
        list.add(3, getPlayerOINBT(entity).getInt("OIPoint"));
        return list;
    }
    public void addPlayerOIPoint(PlayerEntity entity, int add) {
        CompoundNBT nbt = getPlayerOINBT(entity);
        int r = nbt.getInt("OIPoint");
        nbt.putInt("OIPoint", Math.max(0, r + add));
        levelRun(nbt);
        savePlayerOINBT(entity, nbt);
    }
    public void setPlayerOIPoint(PlayerEntity entity, int set) {
        CompoundNBT nbt = getPlayerOINBT(entity);
        nbt.putInt("OIPoint", Math.max(0, set));
        levelRun(nbt);
        savePlayerOINBT(entity, nbt);
    }

    //玩家感染抗性有关
    public void loadPlayerOIRNBT(PlayerEntity entity){
        if(PlayerOIRDataNbt == null)
            PlayerOIRDataNbt = new CompoundNBT();
        if(!PlayerOIRDataNbt.contains(entity.getUUID().toString())){
            PlayerOIRDataNbt.putInt(entity.getUUID().toString(),0);
        }
    }
    public int getPlayerOIRLevel(PlayerEntity entity) {
        loadPlayerOIRNBT(entity);
        return PlayerOIRDataNbt.getInt(entity.getUUID().toString());
    }
    public void addPlayerOIRLevel(PlayerEntity entity, int add) {
        loadPlayerOIRNBT(entity);
        int r = PlayerOIRDataNbt.getInt(entity.getUUID().toString());
        PlayerOIRDataNbt.putInt(entity.getUUID().toString(),Math.max(0, r + add));
        setDirty();
    }
    public void setPlayerOIRLevel(PlayerEntity entity, int level) {
        loadPlayerOIRNBT(entity);
        PlayerOIRDataNbt.putInt(entity.getUUID().toString(),Math.max(0, level));
        setDirty();
        entity.displayClientMessage(new TranslationTextComponent("pma.oi.OIRSet", level), false);
    }

    private void levelRun(CompoundNBT nbt) {
        List<Integer> list = new OIMain.EntityOI().run2(nbt.getInt("OIPoint"));
        nbt.putInt("OILevel", list.get(0));
        nbt.putInt("OIPointOverflow", list.get(1));
        nbt.putInt("OILevelUpRequire", list.get(2));
    }
}
