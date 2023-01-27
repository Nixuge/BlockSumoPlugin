package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.config.ConfigPart;

public class ExpiringBlockConfig extends ConfigPart {
    public ExpiringBlockConfig(ConfigurationSection conf) {
        breakTime = getInt(conf, "defaulttickbreaktime", 1200);
        breakStartTime = getInt(conf, "defaulttickbreakstarttime", 900);
        minColorChanges = getInt(conf, "mincolorchangecount", 1);
        maxColorChanges = getInt(conf, "maxcolorchangecount", 6);
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
