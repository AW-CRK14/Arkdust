package com.ardc.arkdust.NewPlayingMethod.OriInfection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class OIMain {
    public static class WorldOI {
        public boolean ifWorldOIRun(net.minecraft.world.World worldIn){
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getWorldOIState();
            }
            return false;
        }

        public double getWholeOIData(net.minecraft.world.World worldIn, BlockPos pos, PlayerEntity player){
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getWholeOILevel(pos, player);
            }
            return 0;
        }

        public double getWholeOIDataHere(net.minecraft.world.World worldIn, double x, double y, double z, PlayerEntity player){
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                return data.getWholeOILevel(x,y,z, player);
            }
            return 0;
        }

        public double getWholeOIDataHere(net.minecraft.world.World worldIn, PlayerEntity player){
            return getWholeOIDataHere(worldIn,player.getX(),player.getY(),player.getZ(),player);
        }

        public void tryAddWorldOIPoint(World worldIn,double addNum){
            if(!worldIn.isClientSide()){
                WorldOIData data = WorldOIData.get(worldIn);
                data.setWorldOILevelVariableForTest(worldIn,addNum);
            }
        }
    }

    public static void seedOIData(PlayerEntity player){
        net.minecraft.world.World worldIn = player.getCommandSenderWorld();
        if(!worldIn.isClientSide()){
            player.displayClientMessage(new TranslationTextComponent("pma.oi.worldOIState." + new WorldOI().ifWorldOIRun(worldIn)), false);
            player.displayClientMessage(new TranslationTextComponent("pma.oi.wholeOILevel",new WorldOI().getWholeOIDataHere(worldIn,player)), false);
        }
    }
}
