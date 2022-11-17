package me.nixuge.config;

import org.bukkit.configuration.ConfigurationSection;

public class GameConfig {
    public GameConfig(ConfigurationSection conf) {
        minPlayerCount = conf.getInt("minPlayerCount");
        maxPlayerCount = conf.getInt("maxPlayerCount");
    }

    private final int minPlayerCount;
    private final int maxPlayerCount;

    public int getMinPlayers() {
        return minPlayerCount;
    }
    public int getMaxPlayers() {
        return maxPlayerCount;
    }
}
