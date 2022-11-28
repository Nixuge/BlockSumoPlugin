package me.nixuge.reflections;

import java.lang.reflect.Method;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandleSendPacket {
    public static Class<?> serverHandleClass = ReflectionUtils.getNMSClass("PlayerConnection");
    public static Method method = ReflectionUtils.getMethodFromNameArgcount(serverHandleClass, "sendPacket", 1);


    public static void send(Object handle, Object packet) {
        try {
            // if (is1_7)
            // method.invoke(handle, x, y, z, radius, dimension, packet);
            // else
            method.invoke(handle, packet);

        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to send SendPacketNearby");
            e.printStackTrace();
        }
    }
}
