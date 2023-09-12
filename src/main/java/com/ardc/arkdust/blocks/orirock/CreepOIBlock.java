package com.ardc.arkdust.blocks.orirock;

import com.ardc.arkdust.playmethod.oi.OIItem.PreOIBlock;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.resourcelocation.Tag;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class CreepOIBlock extends PreOIBlock {
    private final boolean toOriD;
    public CreepOIBlock(Properties properties, float touchTickDamage,int tickPlayerOILevelAdd,int needOIRLevel,boolean toOriD) {
        super(properties,touchTickDamage,tickPlayerOILevelAdd,needOIRLevel);
        this.toOriD = toOriD;
    }

    private boolean randomBoolean(int get){
        Random r = new Random();
        return get >= r.nextInt(32) + 1;
    }

    private void autoSetBlock(World world, BlockPos pos, int randomInt){
        BlockPos[] posList = new BlockPos[]{pos.above(),pos.below(),pos.north(),pos.east(),pos.south(),pos.west()};
        Random r = new Random();
        int count = r.nextInt(2);
        for(BlockPos posIn : posList) {
            if (Tag.Blocks.ALLOW_ORIROCK_SPREAD.contains(world.getBlockState(posIn).getBlock())) {
                count +=1;
                if(randomBoolean(randomInt))
                    world.setBlock(posIn, this.defaultBlockState(), 3);
            }
        }
        if(toOriD && count==0) world.setBlock(pos, BlockRegistry.d_originium_block.get().defaultBlockState(),3);
    }

    public void stepOn(World world,BlockPos pos,Entity entity){
        super.stepOn(world,pos,entity);
        if(!world.isClientSide)
            autoSetBlock(world,pos,4);
    }

//    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos2, boolean b){
//        super.neighborChanged(state,world,pos,block,pos,b);
//        autoSetBlock(world,pos,4);
//    }

    public void onRemove(BlockState state1, World world, BlockPos pos, BlockState state2, boolean b) {
        super.onRemove(state1,world,pos,state2,b);
        if(!world.isClientSide()){
            if(world.getNearestPlayer(pos.getX(),pos.getY(),pos.getZ(),16,false) != null)
                autoSetBlock(world,pos,4);
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state,world,pos,random);
        if(random.nextInt() <= 4) autoSetBlock(world,pos,1);
    }

}
