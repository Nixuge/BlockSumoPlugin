package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

public class ExpiringBlockConfig {
    public ExpiringBlockConfig(ConfigurationSection conf) {
        breakTime = conf.getInt("defaulttickbreaktime");
        breakStartTime = conf.getInt("defaulttickbreakstarttime");
        minColorChanges = conf.getInt("mincolorchangecount");
        maxColorChanges = conf.getInt("maxcolorchangecount");
    }
    
    private final int breakTime;
    private final int breakStartTime;
    private final int minColorChanges;
    private final int maxColorChanges;

    public int getTickBreakTime() {
        return breakTime;
    }
    public int getTickBreakStartTime() {
        return breakStartTime;
    }

    public int getMinColorChanges() {
        return minColorChanges;
    }
    public int getMaxColorChanges() {
        return maxColorChanges;
    }
}
