package me.nixuge.utils.game;

import java.util.Random;

import org.bukkit.Location;

public class ExpiringBlock {
    private final Location location;
    private final int[] states;
    private final int breakerId;

    public ExpiringBlock(int currentTime, Location location) {
        // IMPORTANT NOTE: FUNCTION NOW RUNNING EVERY 
        // GAME TICK, SO MULTIPLY VALUES BY 20
        // TO GET THE TIME FROM SECONDS
        int breakTime = 1200; //default 60s
        // this.location = location;
        this.location = location.clone().add(0.5, 0, 0.5); //use center of block instead of edge

        this.states = getStatesAfterTime(currentTime, breakTime, 900); //default 45s
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
