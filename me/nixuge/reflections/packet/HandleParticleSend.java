package me.nixuge.reflections.packet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.nixuge.reflections.HandleUtils;
import me.nixuge.reflections.ReflectionUtilsAbstract;
import me.nixuge.reflections.send.HandleSendPacket;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class HandleParticleSend extends ReflectionUtilsAbstract {
    private static Class<?> packetClass = getNMSClass("PacketPlayOutWorldParticles");
    private static Class<?> enumClass = getNMSClass("EnumParticle");

    private static Constructor<?> packetConstructor = getPacketConstructor();
    private static Method enumValueOf = getEnumValueOfMethod();

    private static Constructor<?> getPacketConstructor() {
        try {
            return packetClass.getConstructor(enumClass, boolean.class, float.class, float.class, float.class,
                    float.class, float.class, float.class, float.class, int.class, int[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Method getEnumValueOfMethod() {
        try {
            return enumClass.getMethod("valueOf", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private ParticleEnum particle;
    private float x, y, z;
    private float xPlus, yPlus, zPlus;
    private int speed = 0; // not needed (as of now)
    private int count;
    private int[] data;

    public HandleParticleSend(ParticleEnum particle,
            double x, double y, double z,
            double xPlus, double yPlus, double zPlus,
            int count, int[] data) {
        this.particle = particle;
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        this.xPlus = (float) xPlus;
        this.yPlus = (float) yPlus;
        this.zPlus = (float) zPlus;
        this.count = count;
        this.data = data;
    }

    // enum_particle, true,
    // (float) x, (float) y, (float) z,
    // (float) xPlus, (float) yPlus, (float) zPlus,
    // 0, count, data);

    public Object getPacket() {
        try {
            Object enumParticle = enumValueOf.invoke(enumClass, particle.toString());
            Object packet = packetConstructor.newInstance(
                    enumParticle, true,
                    x, y, z, xPlus, yPlus, zPlus,
                    speed, count, data);
            return packet;
        } catch (Exception e) {
            Logger.log(LogLevel.ERROR, "Failed to create new PacketPlayOutWorldParticles");
            e.printStackTrace();
        }
        return null;
    }

    public void sendPacketAllPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendPacketPlayer(p);
        }
    }
    
    public void sendPacketPlayer(Player p) {
        Object playerHandle = HandleUtils.getHandleField(p, "playerConnection");
        
        HandleSendPacket.send(playerHandle, getPacket());
    }
}

