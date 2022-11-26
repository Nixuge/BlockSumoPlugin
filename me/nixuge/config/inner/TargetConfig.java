package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

public class TargetConfig {
    public TargetConfig(ConfigurationSection conf) {
        captureDelayTick = conf.getInt("capturedelaytick");
        minValuesNeeded = conf.getInt("minimumvaluesneeded");
        maxValuesStored = conf.getInt("maximumvaluesstored");
        minYAverage = conf.getInt("minyaveragefortarget");
        minimumTimeBetweenTargets = conf.getInt("minimumtimebetweentargets");
        allowSameTargetMultipleTimes = conf.getBoolean("allowsametargetmultipletimes");
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