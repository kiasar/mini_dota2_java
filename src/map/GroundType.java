package map;

import java.io.Serializable;

public class GroundType implements Serializable {
    final GroundEnum type;
    private final int path;
    private final int lane;

    private GroundType(GroundEnum type, int path, int lane) {
        this.type = type;
        this.lane = lane;
        this.path = path;
    }

    static GroundType makeGrass() {
        return new GroundType(GroundEnum.GRASS, -1, -1);
    }

    static GroundType makePath(int path, int lane) {
        return new GroundType(GroundEnum.PATH, path, lane);
    }

    public boolean isSamePath(GroundType groundType) {
        return (path == groundType.path) || (groundType.type==GroundEnum.GRASS)||type==GroundEnum.GRASS;
    }
    public boolean isSameLane(GroundType groundType) {
        return lane == groundType.lane;
    }

    public GroundEnum getType() {
        return type;
    }

    public int getLane() {
        return lane;
    }

    public int getPath() {
        return path;
    }
}
