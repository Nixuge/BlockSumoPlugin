package me.nixuge.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.objects.maths.Area;
import me.nixuge.objects.maths.XYZ;

public class MapConfig {
    private static final ConfigurationSection conf = Config.getFileConfigBlock("map");

    private static XYZ getXYZfromString(String str) {
        String[] parts = str.split(" ");
        if (parts.length != 3) {
            Bukkit.broadcastMessage("§l§4Wrong number of string parts to create an XYZ object (" + parts.length
                    + " instead of 3). @getXYZfromString for string \"" + str + "\".");
            return new XYZ(0, 0, 0);
        }

        int[] xyz = new int[3];

        for (int i = 0; i < 3; i++) {
            String part = parts[i];
            try {
                xyz[i] = Integer.parseInt(part);
            } catch (NumberFormatException e) {
                Bukkit.broadcastMessage(
                        "§l§4ERROR IN CONF! @getXYZfromString for value \"" + part + "\" for string \"" + str + "\".");
                xyz[i] = 0;
            }
        }

        return new XYZ(xyz[0], xyz[1], xyz[2]);
    }

    private static Location getCenterLocation(Location loc) {
        // should mutate original as well but still returning
        loc.add(.5, 0, .5);
        return loc;
    }

    public static World getWorld() {
        return Bukkit.getWorld(conf.getString("world"));
    }

    public static Area getCenterArea() {
        return new Area(
                getXYZfromString(conf.getString("centerAreaFirstCorner")),
                getXYZfromString(conf.getString("centerAreaSecondtCorner")));
    }

    public static Location getCenterBlock() {
        return getCenterLocation(getXYZfromString(conf.getString("centerBlock")).asLocation(getWorld())).add(.5, 1, .5);
    }

    public static List<Location> getSpawns() {
        List<Location> spawns = new ArrayList<>();

        for (String str : conf.getStringList("spawns")) {
            spawns.add(getXYZfromString(str).asLocation(getWorld()).add(.5, 1, .5));
        }
        Bukkit.broadcastMessage(spawns + "");
        return spawns;
    }
}
