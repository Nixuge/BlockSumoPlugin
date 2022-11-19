package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

public class GameConfig {
    public GameConfig(ConfigurationSection conf) {
        minPlayerCount = conf.getInt("minplayercount");
        maxPlayerCount = conf.getInt("maxplayercount");
        countAsKillDelay = conf.getInt("countaskilldelay");
    }

    private final int minPlayerCount;
    private final int maxPlayerCount;
    private final int countAsKillDelay;

    public int getMinPlayers() {
        return minPlayerCount;
    }
    public int getMaxPlayers() {
        return maxPlayerCount;
    }
    public int getCountAsKillDelay() {
        return countAsKillDelay;
    }
}
