package me.nixuge.reflections;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandleSendPacketNearby {
    public static Class<?> serverHandleClass = ReflectionUtils.getNMSClass("DedicatedPlayerList");
    public static Method method = ReflectionUtils.getMethodFromNameArgcount(serverHandleClass, "sendPacketNearby", 7);

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
