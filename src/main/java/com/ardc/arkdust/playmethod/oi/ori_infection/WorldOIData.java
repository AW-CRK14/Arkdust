package com.ardc.arkdust.playmethod.oi.ori_infection;
//
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.util.Util;
//import net.minecraft.util.text.ChatType;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraft.world.World;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.saveddata.SavedData;
//import net.minecraft.world.server.ServerWorld;
//import net.minecraft.world.storage.DimensionSavedDataManager;
//import net.minecraft.world.storage.WorldSavedData;
//import net.minecraftforge.fml.server.ServerLifecycleHooks;
//
//public class WorldOIData extends SavedData {
//    private static final String NAME = "ArdWorldOI";
//    private boolean worldOIState = false;
//    private double WorldOILevel;//世界感染常量
//    private boolean debugMode = false;
//
//    public WorldOIData() {}
//    public WorldOIData(CompoundTag tag) {load(tag);}
//
//    //WARN 小心变量null造成的未初始化报错，玩家数据要调用savePlayerOINBT否则不会被保存
//
//    //前置内容
//    public static WorldOIData get(ServerLevel worldIn) {
//            return worldIn.getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(WorldOIData::new,WorldOIData::new, NAME);
//    }
//    public void load(CompoundTag nbt) {
//        worldOIState = nbt.getBoolean("WorldOIOn");
//        WorldOILevel = nbt.getDouble("WorldOILevelConstant");
//    }
//    public CompoundTag save(CompoundTag nbt) {
//        nbt.putBoolean("WorldOIOn", worldOIState);
//        nbt.putDouble("WorldOILevelConstant", WorldOILevel);
//        return nbt;
//    }
////    public CompoundNBT getPlayerOINBT(PlayerEntity entity) {
////        if (entity != null) {
////            UUID playerUUID = entity.getUUID();
////            if(PlayerOIDataNbt == null)
////                PlayerOIDataNbt = new CompoundNBT();
////            if (PlayerOIDataNbt.contains(playerUUID.toString()))
////                return PlayerOIDataNbt.getCompound(playerUUID.toString());
////            setDirty();
////            CompoundNBT newNBT = new CompoundNBT();
////            newNBT.putInt("OIPoint", 0);
////            newNBT.putInt("ResistanceLevel", 0);
////            newNBT.putInt("OILevel", 0);
////            newNBT.putInt("OIPointOverflow", 0);
////            newNBT.putInt("OILevelUpRequire", new OIMain.EntityOI().run1(1));
////            return newNBT;
////        }
////        return null;
////    }
//
//    //世界感染状态相关
//    public void worldOIBegin() {
//        this.worldOIState = true;
//    }
//    public void worldOIReset() {
//        if (debugMode) worldOIState = false;
//    }
//    public boolean getWorldOIState() {
//        return worldOIState;
//    }
//
//    //世界感染值相关 TODO 群系感染，世界感染变动
//    public double getEnvironmentOILevel(double x, double y, double z) {
//        return getWorldOILevel() + getBiomeOILevel(x, y, z);
//    }
//    public double getWorldOILevel() {
//        return WorldOILevel;
//    }
//    public double getBiomeOILevel(double x, double y, double z) {//TODO
//        return 0;
//    }
//    public void addWorldOILevelConstant(double add) {
//        WorldOILevel += add;
//        setDirty();
//    }
//}
