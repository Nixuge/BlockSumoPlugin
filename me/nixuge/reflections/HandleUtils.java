package me.nixuge.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.Bukkit;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandleUtils {
    public static Object getHandle(Object object) {
        try {
            Method getHandle = object.getClass().getMethod("getHandle");
            return getHandle.invoke(object);

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            Logger.log(LogLevel.ERROR, "Failed to get handle!");
            e.printStackTrace();
        }
        return null;
    }

    public static Object getHandleField(Object object, String field) {
        try {
            Object handle = getHandle(object);

            Field conField = handle.getClass().getField(field);
            return conField.get(handle);

        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
            Logger.log(LogLevel.ERROR, "Failed to get handle field!");
            e.printStackTrace();
        }
        return null;
    }

    public static Object callMethodFromHandle(Object handle, String methodName, Object... parameters) {
        try {
            Method method = handle.getClass().getMethod(methodName);
            Object result = method.invoke(method, parameters);
            return result;

        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException
                | NoSuchMethodException e) {
            Logger.log(LogLevel.ERROR, "Failed to call handle method");
            e.printStackTrace();
        }

        return null;
    }

    // Predefined methods
    public static Object sendPacketNearby(Object handle, Object... parameters) {
        try {
            //TODO:
            //remove the hardcoded types
            //Figure out how to actually invoke the method
            Method method = handle.getClass().getMethod("sendPacketNearby", double.class, double.class, double.class,
                    double.class, int.class, net.minecraft.server.v1_8_R3.Packet.class);
            Object result = method.invoke(handle, parameters);
            return result;

        } catch (IllegalArgumentException | SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            Logger.log(LogLevel.ERROR, "Failed to call handle method");
            e.printStackTrace();
        }

        return null;
    }
}
