package com.ardc.arkdust.enums;

import net.minecraft.core.Direction;
import org.joml.Vector3i;

public enum VertexDirection {
    WDN("wdn",Direction.WEST,Direction.DOWN,Direction.NORTH,new Vector3i(-1, -1, -1)),
    WDS("wds", Direction.WEST,Direction.DOWN,Direction.SOUTH,new Vector3i(-1, -1, 1)),
    EDN("edn",Direction.EAST,Direction.DOWN,Direction.NORTH,new Vector3i(1, -1, -1)),
    EDS("eds",Direction.EAST,Direction.DOWN,Direction.SOUTH,new Vector3i(1, -1, -1)),
    WUN("wun",Direction.WEST,Direction.UP,Direction.NORTH,new Vector3i(-1, 1, -1)),
    WUS("wus",Direction.WEST,Direction.UP,Direction.SOUTH,new Vector3i(-1, 1, 1)),
    EUN("eun",Direction.EAST,Direction.UP,Direction.NORTH,new Vector3i(1, 1, -1)),
    EUS("eus",Direction.EAST,Direction.UP,Direction.SOUTH,new Vector3i(1, 1, -1));


    protected final String name;
    protected final Direction direction_ns;
    protected final Direction direction_we;
    protected final Direction direction_ud;
    protected final Vector3i v3i;
    VertexDirection(String name, Direction we, Direction ud, Direction ns, Vector3i v3i) {
        this.name = name;
        this.direction_ns = ns;
        this.direction_we = we;
        this.direction_ud = ud;
        this.v3i = v3i;
    }

    public Direction getNext2dDirection(Direction direction){
        return direction.equals(direction_ns) ? direction_we : direction_ns;
    }
}
