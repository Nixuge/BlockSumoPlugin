package me.nixuge.config;

import org.bukkit.configuration.ConfigurationSection;

public class GameConfig {
    private static final ConfigurationSection conf = Config.getFileConfigBlock("game");

    public static int getMinPlayers() {
        return conf.getInt("minimumPlayerCount");
    }
    public static int getMaxPlayers() {
        return conf.getInt("maxPlayerCount");
    }
}
