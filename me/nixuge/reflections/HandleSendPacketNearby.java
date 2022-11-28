package me.nixuge.reflections;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandleSendPacketNearby {
    public static boolean is1_7 = BlockSumo.getInstance().is1_7();

    public static Object send(Object handle, Player p, int x, int y, int z, int radius, int dimension, Object packet) {
        try {
            if (is1_7) {
                Method method = ReflectionUtils.getMethodFromName(handle.getClass(), "sendPacketNearby", 6);
                method.invoke(handle, x, y, z, radius, dimension, packet);
            } else {
                Method method = ReflectionUtils.getMethodFromName(handle.getClass(), "sendPacketNearby", 7);
                method.invoke(handle, null, x, y, z, radius, dimension, packet);
            }

        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to send SendPacketNearby");
            e.printStackTrace();
        }

        return null;
    }
}
