package me.nixuge.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class XYZ {
    //TODO: convert more things to this format
    private final int x, y, z;
    private final World world;

    public XYZ(int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Location asLocation() {
        return new Location(world, x, y, z);
    }
}
