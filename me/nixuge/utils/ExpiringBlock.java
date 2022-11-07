package me.nixuge.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ExpiringBlock {
    private final int currentTime, expirationTime;
    private final Location location;
    private final int[] states;
    private final int breakerId;

    //TODO:
    //figure out how to simulate a block breaking
    //-> see https://www.spigotmc.org/threads/block-break-state.266966/

    public ExpiringBlock(int currentTime, Location location, int breakTime) {
        this.currentTime = currentTime;
        this.expirationTime = currentTime + breakTime;
        this.location = location;
        this.states = getStatesAfterTime(currentTime, breakTime, 45);
        this.breakerId = new Random().nextInt(Integer.MAX_VALUE);
    }
    
    public ExpiringBlock(int currentTime, Location location) {
        int breakTime = 60; //default
        this.currentTime = currentTime;
        this.expirationTime = currentTime + breakTime;
        this.location = location;
        this.states = getStatesAfterTime(currentTime, breakTime, 45);
        this.breakerId = new Random().nextInt(Integer.MAX_VALUE);
    }
    
    private int[] getStatesLinear(int currentTime, int breakTime) {
        int[] tempStates = new int[11];
        double stateTime = breakTime / 11.0; //more accurate for divisions
        for(int i=0; i < 11; i++) {
            tempStates[i] = (int)(currentTime + (stateTime * i));
        }
        //10 states (0->9)
        //+1 for last index, when the block has to break
        return tempStates;
    }
    private int[] getStatesAfterTime(int currentTime, int breakTime, int breakStartTime) {
        int newBreakTime = breakTime - breakStartTime; //if 60 and 40, will be 20s
        int newCurrentTime = currentTime + breakStartTime; //if 145 and 40, will be 185s
        return getStatesLinear(newCurrentTime, newBreakTime);
    }

    public Location asLocation() {
        return location;
    }
    public int[] getStates() {
        return states;
    }
    public int getBreakerId() {
        return breakerId;
    }
}
