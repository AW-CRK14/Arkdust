package com.ardc.arkdust.blocks.orirock;

import com.ardc.arkdust.playmethod.oi.OIItem.PreOIBlock;
import com.ardc.arkdust.registry.BlockRegistry;
import com.ardc.arkdust.resource.Tag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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

    private void autoSetBlock(Level world, BlockPos pos, int randomInt){
        BlockPos[] posList = new BlockPos[]{pos.above(),pos.below(),pos.north(),pos.east(),pos.south(),pos.west()};
        Random r = new Random();
        int count = r.nextInt(2);
        for(BlockPos posIn : posList) {
            if (world.getBlockState(posIn).is(Tag.Blocks.ALLOW_ORIROCK_SPREAD)) {
                count +=1;
                if(randomBoolean(randomInt))
                    world.setBlock(posIn, this.defaultBlockState(), 3);
            }
        }
        if(toOriD && count==0) world.setBlock(pos, BlockRegistry.d_originium_block.get().defaultBlockState(),3);
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity){
        super.stepOn(world,pos ,state,entity);
        if(!world.isClientSide)
            autoSetBlock(world,pos,4);
    }

//    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos pos2, boolean b){
//        super.neighborChanged(state,world,pos,block,pos,b);
//        autoSetBlock(world,pos,4);
//    }

    public void onRemove(BlockState state1, Level world, BlockPos pos, BlockState state2, boolean b) {
        super.onRemove(state1,world,pos,state2,b);
        if(!world.isClientSide()){
            if(world.getNearestPlayer(pos.getX(),pos.getY(),pos.getZ(),16,false) != null)
                autoSetBlock(world,pos,4);
        }
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state,world,pos,random);
        if(random.nextInt() <= 4) autoSetBlock(world,pos,1);
    }

}
