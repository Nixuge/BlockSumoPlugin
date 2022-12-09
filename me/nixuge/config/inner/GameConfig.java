package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

public class GameConfig {
    public GameConfig(ConfigurationSection conf) {
        minPlayerCount = conf.getInt("minplayercount");
        maxPlayerCount = conf.getInt("maxplayercount");
        countAsKillDelay = conf.getInt("countaskilldelay");
        fireworkMaxTickTime = conf.getInt("fireworkmaxtimetick");
    }

    private final int minPlayerCount;
    private final int maxPlayerCount;
    private final int countAsKillDelay;
    private final int fireworkMaxTickTime;

    public int getMinPlayers() {
        return minPlayerCount;
    }
    public int getMaxPlayers() {
        return maxPlayerCount;
    }
    public int getCountAsKillDelay() {
        return countAsKillDelay;
    }
    public int getFireworkMaxTickTime() {
        return fireworkMaxTickTime;
    }
}
