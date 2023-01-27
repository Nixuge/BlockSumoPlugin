package me.nixuge.objects;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

    private boolean isPlayerNearLocation(Location loc) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            // Logger.log(LogLevel.DEBUG, p.getLocation().distance(loc) + "");
            if (p.getLocation().distance(loc) <= 1.9)
                return true;
        }
        return false;
    }

    public Location getRandomSpawn() {
        int spawnsSize = spawnLocations.size();
        List<Integer> usedIndexes = new ArrayList<>();

        int randIndex = rand.nextInt(spawnsSize);
        Location randomLoc = spawnLocations.get(randIndex);
        usedIndexes.add(randIndex);

        // Keep trying as long as either:
        // - A player isn't nearby
        // - The num of tested spawns is more than the number of actual spawns
        //// -> to avoid getting stuck on an infinite loop if a player is always nearby
        while (isPlayerNearLocation(randomLoc) && usedIndexes.size() < spawnsSize) {
            randIndex = rand.nextInt(spawnsSize);
            if (usedIndexes.contains(randIndex))
                continue;

            // If the index is unique
            usedIndexes.add(randIndex);
            randomLoc = spawnLocations.get(randIndex);
        }

        return randomLoc;
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
