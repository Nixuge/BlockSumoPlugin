package me.nixuge.config.inner;

import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.config.ConfigPart;

public class GameConfig extends ConfigPart {
    public GameConfig(ConfigurationSection conf) {
        minPlayerCount = getInt(conf, "minplayercount", 2);
        maxPlayerCount = getInt(conf, "maxplayercount", 8);
        countAsKillDelay = getInt(conf, "countaskilldelay", 2);
        fireworkMaxTickTime = getInt(conf, "fireworkmaxtimetick", 300);
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
