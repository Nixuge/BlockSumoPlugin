package me.nixuge.reflections.send;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import me.nixuge.reflections.ReflectionUtilsAbstract;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandleSendPacketNearby extends ReflectionUtilsAbstract {
    public static Class<?> serverHandleClass = getNMSClass("DedicatedPlayerList");
    public static Method method = getMethodFromNameArgs(serverHandleClass, "sendPacketNearby", null, double.class, double.class, double.class, double.class, int.class, null);

    public static Object send(Object handle, Player p, int x, int y, int z, int radius, int dimension, Object packet) {
        try {
            method.invoke(handle, null, x, y, z, radius, dimension, packet);
        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to send SendPacketNearby");
            e.printStackTrace();
        }

        return null;
    }
}
