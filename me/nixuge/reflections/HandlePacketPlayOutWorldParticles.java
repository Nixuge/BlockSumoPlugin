package me.nixuge.reflections;

import java.lang.reflect.Constructor;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandlePacketPlayOutWorldParticles {
    private static Class<?> packetClass = ReflectionUtils.getNMSClass("PacketPlayOutWorldParticles");

    private static Constructor<?> packetConstructor = getPacketConstructor();

    private static Constructor<?> getPacketConstructor() {
        try {
            return packetClass.getConstructor(String.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String particleName;
    private float x, y, z;
    private float r, g, b;
    //private int data1, data2;

    public HandlePacketPlayOutWorldParticles(String particleName, float x, float y, float z, float r, float g, float b) {
        this.particleName = particleName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public Object getPacket() {
        try {
            // 1.7 fields:
            // breakerId, x, y, z, stage
            return packetConstructor.newInstance(particleName, x, y, z, r, g, b, 1, 0);
        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to create new HandlePacketPlayOutWorldParticles");
            e.printStackTrace();
        }
        return null;
    }
}
