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
    private double WorldOILevel;//�����Ⱦ����
    private boolean debugMode = false;

    public WorldOIData() {
        super(NAME);
    }

    //WARN С�ı���null��ɵ�δ��ʼ�������������Ҫ����savePlayerOINBT���򲻻ᱻ����

    //ǰ������
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

    //�����Ⱦ״̬���
    public void worldOIBegin(World world) {
        if (!WorldOIState) {//����OI״̬Ϊ��ʱִ��
            WorldOIState = true;//��������OI״̬
            setDirty();
            if (!world.isClientSide()) {//������֪ȫ���������OI������
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
            System.out.println("����Դʯ��Ⱦ�����á���ǰ״̬��" + WorldOIState);
            return;
        }
        System.out.println("DebugMode��δ���á�");
    }
    public boolean getWorldOIState() {
        return WorldOIState;
    }

    //�����Ⱦֵ��� TODO Ⱥϵ��Ⱦ�������Ⱦ�䶯
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
