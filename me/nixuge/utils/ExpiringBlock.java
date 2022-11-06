package me.nixuge.utils;

import org.bukkit.Location;

public class ExpiringBlock {
    private int currentTime, expirationTime;
    private Location location;

    //TODO:
    //figure out how to simulate a block breaking
    //-> see https://www.spigotmc.org/threads/block-break-state.266966/

    public ExpiringBlock(int currentTime, int expirationTime, Location location) {
        this.currentTime = currentTime;
        this.expirationTime = expirationTime;
        this.location = location;
        //TODO: set intermediate break % times
        //ex: if 6 break states:
        //get breaktime (expirationtime - currenttime)
        //Divide that by 6
        //add those to currenttime
        //got all the values gj
    }

    public Location getLocation() {
        return location;
    }
}
