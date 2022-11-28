package me.nixuge.reflections;

import java.lang.reflect.Constructor;
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

    protected static Object instanciateObject(Class<?> clazz, Object... args) {
        try {
            return getConstructor(clazz, args).newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Constructor<?> getConstructor(Class<?> clazz, Object... args) {
        Class<?>[] classes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            classes[i] = args[i].getClass();
        }
        try {
            return clazz.getConstructor(classes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    protected static Method getMethodFromNameArgcount(Class<?> clazz, String methodName, int argCount) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getParameterCount() == argCount) {
                return method;
            }
        }

        return null;
    }

    protected static Method getMethodFromNameArgs(Class<?> clazz, String methodName, Class<?>... args) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (!method.getName().equals(methodName))
                continue;

            Class<?>[] paramTypes = method.getParameterTypes();
            int argLength = paramTypes.length;

            if (argLength != args.length)
                continue;

            boolean isMatching = true;

            for (int i = 0; i < argLength; i++) {
                Class<?> currentArg = args[i];
                Class<?> currentParam = paramTypes[i];

                if (args[i] == null) // Just skip if null
                    continue;

                if (currentArg != currentParam) {
                    isMatching = false;
                    break;
                }
            }
            if (isMatching)
                return method;
        }

        return null;
    }
}
