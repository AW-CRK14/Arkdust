package com.ardc.arkdust.NewPlayingMethod.OriInfection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
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
    private boolean WorldOIState;
    private double WorldOILevelConstant;
    private double WorldOILevelVariable;
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
    }
    
    public CompoundNBT save(CompoundNBT nbt){
        nbt.putBoolean("WorldOIOn", WorldOIState);
        nbt.putDouble("WorldOILevelConstant",WorldOILevelConstant);
        nbt.putDouble("WorldOILevelVariable",WorldOILevelVariable);
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

    public double getWholeOILevel(BlockPos pos, PlayerEntity player){
        if(pos == null)
            return getWorldOILevel() + getBiomeOILevel(0,0,0) + getPlayerOILevel(player);
        return getWorldOILevel() + getBiomeOILevel(pos.getX(),pos.getY(),pos.getZ()) + getPlayerOILevel(player);
    }
    public double getWholeOILevel(double x, double z, PlayerEntity player){
        return getWorldOILevel() + getBiomeOILevel(x,63,z) + getPlayerOILevel(player);
    }

    public double getWholeOILevel(double x,double y,double z, PlayerEntity player){
        return getWorldOILevel() + getBiomeOILevel(x,y,z) + getPlayerOILevel(player);
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

    public double getPlayerOILevel(PlayerEntity player){//TODO
        if(player == null)
            return 0;
        return 0;
    }
}
