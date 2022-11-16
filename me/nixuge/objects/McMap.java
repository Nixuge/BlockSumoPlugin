package me.nixuge.objects;

import java.util.List;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

import me.nixuge.objects.maths.Area;

public class McMap {

    private final List<Location> spawnLocations;
    private final Location center;
    private final Area centerArea;
    private final Random rand = new Random();
    private final World world;
    
    public McMap(List<Location> spawnLocations, Location center, Area centerArea, World world) {
        this.spawnLocations = spawnLocations;
        this.center = center;
        this.centerArea = centerArea;
        this.world = world;
    }

    public Location getRandomSpawn() {
        return spawnLocations.get(rand.nextInt(spawnLocations.size()));
        //todo: get one with no players nearby
    }
    public Location getCenter() {
        return center;
    }
    public Area getCenterArea() {
        return centerArea;
    }
    public World getWorld() {
        return world;
    }
}
