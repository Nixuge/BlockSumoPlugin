package me.nixuge.config.inner;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import me.nixuge.config.ConfigPart;
import me.nixuge.config.Lang;
import me.nixuge.objects.ExpiringArea;
import me.nixuge.objects.maths.Area;
import me.nixuge.objects.maths.XYZ;

public class MapConfig extends ConfigPart {
    public MapConfig(ConfigurationSection conf) {

        //making default arraylistes
        List<String> defaultDestroyableBlocks = new ArrayList<String>();
        defaultDestroyableBlocks.add("WOOL");
        List<String> defaultSpawns = new ArrayList<String>();
        defaultSpawns.add("0 1 0, 90 6");

        world = Bukkit.getWorld(getString(conf, "world", "world"));

        centerBlock = getXYZfromString(getString(conf, "centerblock", "2 1 2")).asLocation(getWorld()).add(.5, 1, .5);

        destroyableBlocks = new ArrayList<>();
        List<String> destroyableBlocksStr = getStringList(conf, "destroyableblocks", defaultDestroyableBlocks);
        for (String str : destroyableBlocksStr) {
            destroyableBlocks.add(Material.getMaterial(str));
        }
        
        spawns = new ArrayList<>();
        for (String str : getStringList(conf, "spawns", defaultSpawns)) {
            spawns.add(getLocationFromString(str, world).add(.5, 1, .5));
        }

        // inner areas
        ConfigurationSection areaConf = conf.getConfigurationSection("areas");
        innerAreas = new ArrayList<>();

        for (String str : areaConf.getKeys(false)) {
            ConfigurationSection innerAreaConf = areaConf.getConfigurationSection(str);

            innerAreas.add(new ExpiringArea(
                    new Area(getXYZfromString(getString(innerAreaConf, "corner1", "0 0 0")),
                            getXYZfromString(getString(innerAreaConf, "corner2", "0 0 0"))),
                    getInt(innerAreaConf, "tickbreaktime", 0),
                    getInt(innerAreaConf, "tickbreakstarttime", 0)));
        }
    }

    private final World world;
    private final Location centerBlock;
    private final List<Material> destroyableBlocks;
    private final List<Location> spawns;
    private final List<ExpiringArea> innerAreas;

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

    public List<Material> getDestroyableMaterials() {
        return destroyableBlocks;
    }
    public boolean isMaterialDestroyable(Material material) {
        return destroyableBlocks.contains(material);
    }
}
