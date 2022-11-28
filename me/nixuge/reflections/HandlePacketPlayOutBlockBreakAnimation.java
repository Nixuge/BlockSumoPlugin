package me.nixuge.reflections;

import java.lang.reflect.Constructor;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandlePacketPlayOutBlockBreakAnimation {
    private static Class<?> packetClass = ReflectionUtils.getNMSClass("PacketPlayOutBlockBreakAnimation");

    private static Constructor<?> packetConstructor = getPacketConstructor();

    private static Constructor<?> getPacketConstructor() {
        try {
            return packetClass.getConstructor(int.class, int.class, int.class, int.class, int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int breakerId;
    private int x, y, z;
    private int stage;

    public HandlePacketPlayOutBlockBreakAnimation(int breakerId, int x, int y, int z, int stage) {
        this.breakerId = breakerId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.stage = stage;
    }

    public Object getPacket() {
        try {
            // 1.7 fields:
            // breakerId, x, y, z, stage
            return packetConstructor.newInstance(breakerId, x, y, z, stage);
        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to create new PacketPlayOutBlockBreakAnimation");
            e.printStackTrace();
        }
        return null;
    }
}
