package me.nixuge.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class MapConfig {
    private static final ConfigurationSection conf = Config.getFileConfigBlock("map");

    public static Location getCenter() {
        conf.getList("centerFirstCorner");

        return new Location(Bukkit.getWorld("world"), getMaxPlayers(), getMinPlayers(), getMaxPlayers());
    }

    public static int getMinPlayers() {
        return conf.getInt("minimumPlayerCount");
    }
    public static int getMaxPlayers() {
        return conf.getInt("maxPlayerCount");
    }
}
