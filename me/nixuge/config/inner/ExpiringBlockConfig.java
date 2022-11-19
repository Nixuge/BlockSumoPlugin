package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

public class ExpiringBlockConfig {
    public ExpiringBlockConfig(ConfigurationSection conf) {
        breakTime = conf.getInt("tickbreaktime");
        breakStartTime = conf.getInt("tickbreakstarttime");
        breakTimeCenter = conf.getInt("centertickbreaktime");
        breakStartTimeCenter = conf.getInt("centertickbreakstarttime");
        minColorChanges = conf.getInt("mincolorchangecount");
        maxColorChanges = conf.getInt("maxcolorchangecount");
    }
    
    private final int breakTime;
    private final int breakStartTime;
    private final int breakTimeCenter;
    private final int breakStartTimeCenter;
    private final int minColorChanges;
    private final int maxColorChanges;

    public int getTickBreakTime() {
        return breakTime;
    }
    public int getTickBreakStartTime() {
        return breakStartTime;
    }

    public int getCenterTickBreakTime() {
        return breakTimeCenter;
    }
    public int getCenterTickBreakStartTime() {
        return breakStartTimeCenter;
    }

    public int getMinColorChanges() {
        return minColorChanges;
    }
    public int getMaxColorChanges() {
        return maxColorChanges;
    }
}
