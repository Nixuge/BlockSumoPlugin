package me.nixuge.reflections;

import java.lang.reflect.Constructor;

import me.nixuge.BlockSumo;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandlePacketPlayOutBlockBreakAnimation {
    public static boolean is1_7 = BlockSumo.getInstance().is1_7();
    private static Class<?> packetClass = ReflectionUtils.getNMSClass("PacketPlayOutBlockBreakAnimation");
    private static Class<?> bpClass = is1_7 ? null : ReflectionUtils.getNMSClass("BlockPosition");

    private static Constructor<?> bpConstructor = getBpConstructor();
    private static Constructor<?> packetConstructor = getPacketConstructor();

    private static Constructor<?> getBpConstructor() {
        try {
            return is1_7 ? null : bpClass.getConstructor(int.class, int.class, int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Constructor<?> getPacketConstructor() {
        try {
            return (is1_7) ? packetClass.getConstructor(int.class, int.class, int.class, int.class, int.class)
                    : packetClass.getConstructor(int.class, bpClass, int.class);
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
            if (is1_7) {
                // 1.7 fields:
                // breakerId, x, y, z, stage
                return packetConstructor.newInstance(breakerId, x, y, z, stage);
            } else {
                // 1.8+ fields:
                // breakerId, BlockPosition(x, y, z), stage
                Object blockPosition = bpConstructor.newInstance(x, y, z);
                return packetConstructor.newInstance(breakerId, blockPosition, stage);
            }
        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to create new PacketPlayOutBlockBreakAnimation");
            e.printStackTrace();
        }
        return null;
    }
}
