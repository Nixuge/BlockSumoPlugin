package me.nixuge.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public abstract class ReflectionInterface {
    private static String versionBase = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    private static String version = versionBase + ".";
    public static String getNMSVersion() {
        return versionBase;
    }

    private Class<?> getCraftbukkitClass(String craftbukkitClassString) {
        String name = "org.bukkit.craftbukkit." + version + craftbukkitClassString;
        Class<?> craftbukkitClass;
        try {
            craftbukkitClass = Class.forName(name);
            return craftbukkitClass;
        } catch (ClassNotFoundException e) {
            Logger.log(LogLevel.ERROR, "Error getting craftbukkit class using reflections!");
            e.printStackTrace();
        }
        return null;
    }

    private Class<?> getNMSClass(String nmsClassString) {
        String name = "net.minecraft.server." + version + nmsClassString;
        Class<?> nmsClass;
        try {
            nmsClass = Class.forName(name);
            return nmsClass;
        } catch (ClassNotFoundException e) {
            Logger.log(LogLevel.ERROR, "Error getting NMS class using reflections!");
            e.printStackTrace();
        }
        return null;
    }

    public static int getPing(Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            Object nmsPlayer = getHandle.invoke(player);
            Field conField = nmsPlayer.getClass().getField("ping");
            int con = (int)conField.get(nmsPlayer);
            return con;
        } catch (InvocationTargetException | NoSuchMethodException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }
    // public int getPing(Player player) {
    //     getPing(player);
    // }
}
