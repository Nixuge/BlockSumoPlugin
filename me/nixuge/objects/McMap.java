package me.nixuge.objects;

import java.util.List;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

public class McMap {

    private final List<Location> spawnLocations;
    private final Location center;
    private final List<ExpiringArea> innerAreas;
    private final Random rand = new Random();
    private final World world;
    
    public McMap(List<Location> spawnLocations, Location center, World world, List<ExpiringArea> innerAreas) {
        this.spawnLocations = spawnLocations;
        this.center = center;
        this.world = world;
        this.innerAreas = innerAreas;
    }

    public Location getRandomSpawn() {
        return spawnLocations.get(rand.nextInt(spawnLocations.size()));
        //todo: get one with no players nearby
    }
    public Location getCenter() {
        return center;
    }
    public List<ExpiringArea> getInnerAreas() {
        return innerAreas;
    }
    public World getWorld() {
        return world;
    }
}
