package me.nixuge;

import java.util.List;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

public class McMap {

    //TODO: set to final
    private List<Location> spawnLocations;
    private final Location center;
    private final Random rand = new Random();
    private final World world;
    
    public McMap(List<Location> spawnLocations, Location center, World world) {
        this.spawnLocations = spawnLocations;
        this.center = center;
        this.world = world;
    }

    public Location getRandomSpawn() {
        return spawnLocations.get(rand.nextInt(spawnLocations.size()));
        //todo: get one with no players nearby
    }
    public Location getCenter() {
        return center;
    }
    public World getWorld() {
        return world;
    }
}
