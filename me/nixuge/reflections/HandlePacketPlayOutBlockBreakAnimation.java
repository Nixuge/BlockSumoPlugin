package me.nixuge.reflections;

import java.lang.reflect.Constructor;

import me.nixuge.BlockSumo;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandlePacketPlayOutBlockBreakAnimation {
    public static boolean is1_7 = BlockSumo.getInstance().is1_7();

    public static Object getNew(int breakerId, int x, int y, int z, int stage) {
        Class<?> clazz = ReflectionUtils.getNMSClass("PacketPlayOutBlockBreakAnimation");
        if (is1_7) {
            return getNew1_7(clazz, breakerId, x, y, z, stage);
        } else {
            return getNew1_8(clazz, breakerId, x, y, z, stage);
        }
    }

    private static Object getNew1_7(Class<?> clazz, int breakerId, int x, int y, int z, int stage) {
        // 1.7 fields:
        // breakerId, x, y, z, stage
        try {
            Constructor<?> constructor = clazz.getConstructor(int.class, int.class, int.class, int.class,
                    int.class);
            return constructor.newInstance(breakerId, x, y, z, stage);
        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to create new PacketPlayOutBlockBreakAnimation for 1.7");
            e.printStackTrace();
        }
        return null;
    }

    private static Object getNew1_8(Class<?> clazz, int breakerId, int x, int y, int z, int stage) {
        // 1.8+ fields:
        // breakerId, BlockPosition(x, y, z), stage
        try {
            Class<?> bpClass = ReflectionUtils.getNMSClass("BlockPosition");
            Constructor<?> bpConstructor = bpClass.getConstructor(int.class, int.class, int.class);
            Object blockPosition = bpConstructor.newInstance(x, y, z);

            Constructor<?> constructor = clazz.getConstructor(int.class, bpClass, int.class);
            return constructor.newInstance(breakerId, blockPosition, stage);
        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to create new PacketPlayOutBlockBreakAnimation for 1.8+");
            e.printStackTrace();
        }
        return null;
    }
}
