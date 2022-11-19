package me.nixuge.config.inner;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.config.Lang;
import me.nixuge.objects.maths.Area;
import me.nixuge.objects.maths.XYZ;

public class MapConfig {
    public MapConfig(ConfigurationSection conf) {
        centerArea = new Area(
                getXYZfromString(conf.getString("centerareafirstcorner")),
                getXYZfromString(conf.getString("centerareasecondcorner")));
        world = Bukkit.getWorld(conf.getString("world"));

        centerBlock = getXYZfromString(conf.getString("centerblock")).asLocation(getWorld()).add(.5, 1, .5);

        //TODO: add orientation parsing
        spawns = new ArrayList<>();
        for (String str : conf.getStringList("spawns")) {
            spawns.add(getXYZfromString(str).asLocation(getWorld()).add(.5, 1, .5));
        }
    }

    private final Area centerArea;
    private final Location centerBlock;
    private final World world;
    private final List<Location> spawns;

    private static XYZ getXYZfromString(String str) {
        String[] parts = str.split(" ");
        if (parts.length != 3) {
            Bukkit.broadcastMessage(Lang.get("errors.mapconfig.wrongparse1", parts.length, str));
            return new XYZ(0, 0, 0);
        }

        int[] xyz = new int[3];

        for (int i = 0; i < 3; i++) {
            String part = parts[i];
            try {
                xyz[i] = Integer.parseInt(part);
            } catch (NumberFormatException e) {
                Bukkit.broadcastMessage(Lang.get("errors.mapconfig.wrongparse2", part, str));
                xyz[i] = 0;
            }
        }

        return new XYZ(xyz[0], xyz[1], xyz[2]);
    }

    public World getWorld() {
        return world;
    }

    public Area getCenterArea() {
        return centerArea;
    }

    public Location getCenterBlock() {
        return centerBlock;
    }

    public List<Location> getSpawns() {
        return spawns;
    }
}
