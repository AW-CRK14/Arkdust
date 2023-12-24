package com.ardc.arkdust.worldgen.placement;

@Deprecated
public class RandomOffsetPlacement{
    //在forge1.20中，placement被迁移至PlacementModifier
//public class RandomOffsetPlacement extends SimplePlacement<RandomPosOffsetConfig> {
//    public RandomOffsetPlacement(Codec<RandomPosOffsetConfig> configCodec) {
//        super(configCodec);
//    }
//
//    @Override
//    protected Stream<BlockPos> place(RandomSource random, RandomPosOffsetConfig config, BlockPos pos) {
//        return Stream.of(pos.offset(random.nextInt(2*config.r)-config.r,0,random.nextInt(2*config.r)-config.r));
//    }
}
