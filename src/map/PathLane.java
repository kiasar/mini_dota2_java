package map;

import items.Item;

public class PathLane extends Item {
    public GroundType groundType;
    public Point first=null;
    public Point last=null;
    protected PathLane(int path,int lane) {
        super(null, 0);
        groundType=GroundType.makePath(path,lane);
    }

    @Override
    public void ruin() {

    }
}
