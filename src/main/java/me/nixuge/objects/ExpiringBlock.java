package me.nixuge.objects;

import java.util.Random;

import org.bukkit.Location;

import me.nixuge.config.Config;
import me.nixuge.enums.Color;

public class ExpiringBlock {
    private final Random rand = new Random();
    private final Location location;

    private final int[] states;
    private final int breakerId;

    private final int[] colorChanges;
    private final Color lastColor;

    public ExpiringBlock(int currentTime, Location location, Color color) {
        // breakTime: 1200t; breakStartTime: 900t.
        this(currentTime, location, color, Config.expiringBlock.getTickBreakTime(),
                Config.expiringBlock.getTickBreakStartTime());
    }

    public ExpiringBlock(int currentTime, Location location, Color color, int breakTime, int breakStartTime) {
        // use center of block instead of edge
        this.location = location;
        if (breakTime != 0) // 2DO: ensure setting to 0 actually works
            this.states = getStatesAfterTime(currentTime, breakTime, breakStartTime);
        else
            this.states = new int[0];
        
        this.breakerId = rand.nextInt(Integer.MAX_VALUE);

        this.colorChanges = getRandomColorChanges(currentTime);
        this.lastColor = color;
    }

    private int[] getStatesLinear(int currentTime, int breakTime) {
        int[] tempStates = new int[11];
        double stateTime = breakTime / 11.0; // more accurate for divisions
        for (int i = 0; i < 11; i++) {
            tempStates[i] = (int) (currentTime + (stateTime * i));
        }
        // 10 states (0->9)
        // +1 for last index, when the block has to break
        return tempStates;
    }

    private int[] getStatesAfterTime(int currentTime, int breakTime, int breakStartTime) {
        int newBreakTime = breakTime - breakStartTime; // if 60 and 40, will be 20s
        int newCurrentTime = currentTime + breakStartTime; // if 145 and 40, will be 185s
        return getStatesLinear(newCurrentTime, newBreakTime);
    }

    private int[] getRandomColorChanges(int currentTime) {
        int minC = Config.expiringBlock.getMinColorChanges();
        int maxC = Config.expiringBlock.getMaxColorChanges() + 1; // +1 bc nextint non inclusive for higher&
        int changesCount = rand.nextInt(maxC - minC) + minC;

        int[] tempChanges = new int[changesCount];

        for (int i = 0; i < changesCount; i++) {
            tempChanges[i] = currentTime + ((i + 1) * 5); // i+1 bc starts at 0
        }
        return tempChanges;
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

    public int[] getColorChanges() {
        return colorChanges;
    }

    public Color getLastColor() {
        return lastColor;
    }
}
