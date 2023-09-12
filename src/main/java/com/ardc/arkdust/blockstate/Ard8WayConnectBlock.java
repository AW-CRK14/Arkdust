package com.ardc.arkdust.blockstate;

import com.ardc.arkdust.enums.VertexDirection;
import com.ardc.arkdust.helper.DirectionAndRotationHelper;
import com.ardc.arkdust.obj_property.ExtraBlockStateProperty;
import com.ardc.arkdust.blocks.infac.IConnectRenderInfo;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import java.util.HashMap;
import java.util.Map;

public abstract class Ard8WayConnectBlock extends DropSelfBlock implements IConnectRenderInfo, IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
        p_203421_0_.put(Direction.NORTH, NORTH);
        p_203421_0_.put(Direction.EAST, EAST);
        p_203421_0_.put(Direction.SOUTH, SOUTH);
        p_203421_0_.put(Direction.WEST, WEST);
    });
    public static final BooleanProperty WN = ExtraBlockStateProperty.WDN;
    public static final BooleanProperty EN = ExtraBlockStateProperty.EDN;
    public static final BooleanProperty WS = ExtraBlockStateProperty.WDS;
    public static final BooleanProperty ES = ExtraBlockStateProperty.EDS;
    public static final Map<Direction, BooleanProperty[]> VERTEX_PROPERTY_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
        p_203421_0_.put(Direction.NORTH, new BooleanProperty[]{WN,EN});
        p_203421_0_.put(Direction.EAST, new BooleanProperty[]{ES,EN});
        p_203421_0_.put(Direction.SOUTH, new BooleanProperty[]{ES,WS});
        p_203421_0_.put(Direction.WEST, new BooleanProperty[]{WN,WS});
    });
    public static final Map<BooleanProperty, VertexDirection> VERTEX_DIRECTION_BY_PROPERTY;
    static {
        HashMap<BooleanProperty, VertexDirection> map = Maps.newHashMap();
        map.put(WN,VertexDirection.WUN);
        map.put(WS,VertexDirection.WUS);
        map.put(EN,VertexDirection.EUN);
        map.put(ES,VertexDirection.EUS);
        VERTEX_DIRECTION_BY_PROPERTY = map;
    }

    public Ard8WayConnectBlock(Properties properties) {
        super(properties);
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = this.defaultBlockState().setValue(WATERLOGGED,fluid.getType() == Fluids.WATER);
        for(Direction d : DirectionAndRotationHelper.direcList.HORIZON_DIRECTION.getList()) {
            state = checkDirection(context.getLevel(),context.getClickedPos(),state,d);
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos){
        if(state.getValue(WATERLOGGED)){
            world.getLiquidTicks().scheduleTick(pos,Fluids.WATER,Fluids.WATER.getTickDelay(world));
        }
        state = checkDirection(world,pos,state,facing);
        return super.updateShape(state,facing,facingState,world,pos,facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

//    @Override
//    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
//        checkDirection(world,pos,state,state.getBedDirection(world,neighbor));
//    }

    public BlockState checkDirection(IWorldReader world, BlockPos pos, BlockState state, Direction d1) {
        BlockState toState = world.getBlockState(pos.relative(d1));
        if (d1 != Direction.DOWN && d1 != Direction.UP) {
            boolean b = toState.getBlock() instanceof IConnectRenderInfo && ((IConnectRenderInfo) toState.getBlock()).getInfo().hashCode() == this.getInfo().hashCode();
            state = state.setValue(PROPERTY_BY_DIRECTION.get(d1), !b);

            if (b) {
                for (BooleanProperty property : VERTEX_PROPERTY_BY_DIRECTION.get(d1)) {
                    Direction d2 = VERTEX_DIRECTION_BY_PROPERTY.get(property).getNext2dDirection(d1);
                    BlockState s1 = world.getBlockState(pos.relative(d1));
                    BlockState s2 = world.getBlockState(pos.relative(d2));

                    state = state.setValue(property, !state.getValue(PROPERTY_BY_DIRECTION.get(d2))
                            && s1.getBlock() instanceof IConnectRenderInfo
                            && s2.getBlock() instanceof IConnectRenderInfo
                            && s1.getValue(PROPERTY_BY_DIRECTION.get(d2))
                            && s2.getValue(PROPERTY_BY_DIRECTION.get(d1))
                    );
                }
            }
        }
        return state;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED,WN,WS,EN,ES);
        super.createBlockStateDefinition(p_206840_1_);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST))
                        .setValue(WN,state.getValue(ES)).setValue(WS,state.getValue(EN)).setValue(EN,state.getValue(WS)).setValue(ES,state.getValue(WN));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH))
                        .setValue(WN,state.getValue(EN)).setValue(WS,state.getValue(WN)).setValue(ES,state.getValue(WS)).setValue(EN,state.getValue(ES));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH))
                        .setValue(WN,state.getValue(WS)).setValue(WS,state.getValue(ES)).setValue(ES,state.getValue(EN)).setValue(EN,state.getValue(WN));
            default:
                return super.rotate(state,rotation);
        }
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        switch(mirror) {
            case LEFT_RIGHT:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH))
                        .setValue(ES,state.getValue(EN)).setValue(EN,state.getValue(ES))
                        .setValue(WS,state.getValue(WN)).setValue(WN,state.getValue(WS));
            case FRONT_BACK:
                return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST))
                        .setValue(ES,state.getValue(WS)).setValue(EN,state.getValue(WN))
                        .setValue(EN,state.getValue(WN)).setValue(ES,state.getValue(WS));
            default:
                return super.mirror(state, mirror);
        }
    }

//    @Override
//    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
//        if(!world.isClientSide)
//            player.displayClientMessage(new StringTextComponent(state.toString()),false);
//        return super.use(state, world, pos, player, hand, result);
//    }
}
