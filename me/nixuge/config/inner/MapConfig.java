package me.nixuge.config.inner;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.config.Lang;
import me.nixuge.objects.ExpiringArea;
import me.nixuge.objects.maths.Area;
import me.nixuge.objects.maths.XYZ;

public class MapConfig {
    public MapConfig(ConfigurationSection conf) {
        world = Bukkit.getWorld(conf.getString("world"));

        centerBlock = getXYZfromString(conf.getString("centerblock")).asLocation(getWorld()).add(.5, 1, .5);

        
        spawns = new ArrayList<>();
        for (String str : conf.getStringList("spawns")) {
            spawns.add(getLocationFromString(str, world).add(.5, 1, .5));
        }

        // inner areas
        ConfigurationSection areaConf = conf.getConfigurationSection("areas");
        innerAreas = new ArrayList<>();

        for (String str : areaConf.getKeys(false)) {
            ConfigurationSection innerAreaConf = areaConf.getConfigurationSection(str);

            innerAreas.add(new ExpiringArea(
                    new Area(getXYZfromString(innerAreaConf.getString("corner1")),
                            getXYZfromString(innerAreaConf.getString("corner2"))),
                    innerAreaConf.getInt("tickbreaktime"),
                    innerAreaConf.getInt("tickbreakstarttime")));
        }
    }

    private final List<ExpiringArea> innerAreas;
    private final Location centerBlock;
    private final World world;
    private final List<Location> spawns;

    private static Location getLocationFromString(String str, World world) {
        String[] xyz_yp = str.split(", ");
        if (xyz_yp.length != 2) {
            Bukkit.broadcastMessage(Lang.get("errors.mapconfig.wrongparselocation1", xyz_yp.length, str));
            return new Location(world, 0, 0, 0);
        } 
        XYZ coords = getXYZfromString(xyz_yp[0]);


        //raw_yp[0] = yaw, raw_yp[1] = pitch
        String[] raw_yp = xyz_yp[1].split(" ");

        int[] yp = new int[2];

        for (int i = 0; i < 2; i++) {
            String part = raw_yp[i];
            try {
                yp[i] = Integer.parseInt(part);
            } catch (NumberFormatException e) {
                Bukkit.broadcastMessage(Lang.get("errors.mapconfig.wrongparselocation2", part, str));
                yp[i] = 0;
            }
        }

        Location finalLoc = coords.asLocation(world);
        finalLoc.setYaw(yp[0]);
        finalLoc.setPitch(yp[1]);
        return finalLoc;
    }

    private static XYZ getXYZfromString(String str) {
        String[] parts = str.split(" ");
        if (parts.length < 3) {
            Bukkit.broadcastMessage(Lang.get("errors.mapconfig.wrongparseXYZ1", parts.length, str));
            return new XYZ(0, 0, 0);
        }

        int[] xyz = new int[3];

        for (int i = 0; i < 3; i++) {
            String part = parts[i];
            try {
                xyz[i] = Integer.parseInt(part);
            } catch (NumberFormatException e) {
                Bukkit.broadcastMessage(Lang.get("errors.mapconfig.wrongparseXYZ2", part, str));
                xyz[i] = 0;
            }
        }

        return new XYZ(xyz[0], xyz[1], xyz[2]);
    }

    public World getWorld() {
        return world;
    }

    public Location getCenterBlock() {
        return centerBlock;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public List<ExpiringArea> getInnerAreas() {
        return innerAreas;
    }
}
