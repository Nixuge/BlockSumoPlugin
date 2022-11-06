package me.nixuge;

import java.util.List;

import java.util.Random;

import org.bukkit.Location;

public class McMap {

    //TODO: set to final
    private List<Location> spawnLocations;
    private final Location center;
    private final Random rand = new Random();
    
    public McMap(List<Location> spawnLocations, Location center) {
        this.spawnLocations = spawnLocations;
        this.center = center;
    }

    public Location getRandomSpawn() {
        return spawnLocations.get(rand.nextInt(spawnLocations.size()));
        //todo: get one with no players nearby
    }
    public Location getCenter() {
        return center;
    }
}
