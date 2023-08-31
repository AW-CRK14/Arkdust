package com.ardc.arkdust.playmethod.oi.ori_infection;

import net.minecraft.nbt.CompoundNBT;
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

public class WorldOIData extends WorldSavedData {
    private static final String NAME = "ArdWorldOI";
    private boolean WorldOIState = false;
    private double WorldOILevel;//世界感染常量
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
        WorldOILevel = nbt.getDouble("WorldOILevelConstant");
    }
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putBoolean("WorldOIOn", WorldOIState);
        nbt.putDouble("WorldOILevelConstant", WorldOILevel);
        return nbt;
    }
//    public CompoundNBT getPlayerOINBT(PlayerEntity entity) {
//        if (entity != null) {
//            UUID playerUUID = entity.getUUID();
//            if(PlayerOIDataNbt == null)
//                PlayerOIDataNbt = new CompoundNBT();
//            if (PlayerOIDataNbt.contains(playerUUID.toString()))
//                return PlayerOIDataNbt.getCompound(playerUUID.toString());
//            setDirty();
//            CompoundNBT newNBT = new CompoundNBT();
//            newNBT.putInt("OIPoint", 0);
//            newNBT.putInt("ResistanceLevel", 0);
//            newNBT.putInt("OILevel", 0);
//            newNBT.putInt("OIPointOverflow", 0);
//            newNBT.putInt("OILevelUpRequire", new OIMain.EntityOI().run1(1));
//            return newNBT;
//        }
//        return null;
//    }

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
    public double getEnvironmentOILevel(double x, double y, double z) {
        return getWorldOILevel() + getBiomeOILevel(x, y, z);
    }
    public double getWorldOILevel() {
        return WorldOILevel;
    }
    public double getBiomeOILevel(double x, double y, double z) {//TODO
        return 0;
    }
    public void addWorldOILevelConstant(double add) {
        WorldOILevel += add;
        setDirty();
    }
}
