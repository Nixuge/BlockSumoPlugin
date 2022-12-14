package me.nixuge.reflections.packet;

import java.lang.reflect.Constructor;

import me.nixuge.reflections.ReflectionUtilsAbstract;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandleBlockBreakAnimation extends ReflectionUtilsAbstract {
    private static Class<?> packetClass = getNMSClass("PacketPlayOutBlockBreakAnimation");
    private static Class<?> bpClass = getNMSClass("BlockPosition");

    private static Constructor<?> bpConstructor = getBpConstructor();
    private static Constructor<?> packetConstructor = getPacketConstructor();

    private static Constructor<?> getBpConstructor() {
        try {
            return bpClass.getConstructor(int.class, int.class, int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Constructor<?> getPacketConstructor() {
        try {
            return packetClass.getConstructor(int.class, bpClass, int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int breakerId;
    private int x, y, z;
    private int stage;

    public HandleBlockBreakAnimation(int breakerId, int x, int y, int z, int stage) {
        this.breakerId = breakerId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.stage = stage;
    }

    public Object getPacket() {
        try {
            // 1.8+ fields:
            // breakerId, BlockPosition(x, y, z), stage
            Object blockPosition = bpConstructor.newInstance(x, y, z);
            return packetConstructor.newInstance(breakerId, blockPosition, stage);

        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to create new PacketPlayOutBlockBreakAnimation");
            e.printStackTrace();
        }
        return null;
    }
}
