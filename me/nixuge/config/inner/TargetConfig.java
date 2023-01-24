package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.config.ConfigPart;

public class TargetConfig extends ConfigPart {
    public TargetConfig(ConfigurationSection conf) {
        captureDelayTick = getInt(conf, "capturedelaytick", 100);
        minValuesNeeded = getInt(conf, "minimumvaluesneeded", 7);
        maxValuesStored = getInt(conf, "maximumvaluesstored", 9);
        minYAverage = getInt(conf, "minyaveragefortarget", 15);
        minimumTimeBetweenTargets = getInt(conf, "minimumtimebetweentargets", 60);
        allowSameTargetMultipleTimes = getBoolean(conf, "allowsametargetmultipletimes", true);
    }

    private final int captureDelayTick;
    private final int minValuesNeeded;
    private final int maxValuesStored;
    // private final int maxTargets;
    private final int minYAverage;
    private final int minimumTimeBetweenTargets;
    private final boolean allowSameTargetMultipleTimes; 

    public int getCaptureDelayTick() {
        return captureDelayTick;
    }
    public int getMinValuesNeeded() {
        return minValuesNeeded;
    }
    public int getMaxValuesStored() {
        return maxValuesStored;
    }
    public int getMinYAverage() {
        return minYAverage;
    }
    public int getMinTimeBetweenTargets() {
        return minimumTimeBetweenTargets;
    }
    public boolean allowSameTargetMultipleTimes() {
        return allowSameTargetMultipleTimes;
    }
}