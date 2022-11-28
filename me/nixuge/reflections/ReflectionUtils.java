package me.nixuge.reflections;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public abstract class ReflectionUtils {
    private static String versionBase = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",")
            .split(",")[3];
    private static String version = versionBase + ".";

    public static String getNMSVersion() {
        return versionBase;
    }

    protected static Class<?> getCraftbukkitClass(String craftbukkitClassString) {
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

    protected static Class<?> getNMSClass(String nmsClassString) {
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

    protected static Method getMethodFromName(Class<?> clazz, String methodName, int argCount) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getParameterCount() == argCount ) {
                return method;
            }
        }

        return null;
    }
}
