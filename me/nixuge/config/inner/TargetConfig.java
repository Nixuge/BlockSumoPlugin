package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

public class TargetConfig {
    public TargetConfig(ConfigurationSection conf) {
        captureDelayTick = conf.getInt("capturedelaytick");
        minValuesNeeded = conf.getInt("minimumvaluesneeded");
        maxValuesPossible = conf.getInt("maximumvaluespossible");
        minYAverage = conf.getInt("minyaveragefortarget");
    }

    private final int captureDelayTick;
    private final int minValuesNeeded;
    private final int maxValuesPossible;
    // private final int maxTargets;
    private final int minYAverage;

    public int getCaptureDelayTick() {
        return captureDelayTick;
    }
    public int getMinValuesNeeded() {
        return minValuesNeeded;
    }
    public int getMaxValuesPossible() {
        return maxValuesPossible;
    }
    public int getMinYAverage() {
        return minYAverage;
    }
}