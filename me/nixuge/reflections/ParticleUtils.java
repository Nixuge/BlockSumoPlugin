package me.nixuge.reflections;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

public class ParticleUtils extends ReflectionUtils {

    public static void sendParticlePacket(ParticleEnum particle, double x, double y, double z,
            me.nixuge.enums.Color color) {
        sendPacketAllPlayers(
                getParticlePacket(particle.getParticleName(), x, y, z, color));
    }

    public static Object getParticlePacket(String particle, double x, double y, double z,
            me.nixuge.enums.Color color) {
        // format:
        // particlename
        // x, y, z
        // R, G, B
        // speed, count?
        // TODO (maybe): use the count and not hardcode 0
        // TODO: make block break particles work like in 1.8
        Color bukkitColor = (color == null) ?
                Color.fromRGB(0, 0, 0) :
                color.getDyeColor().getColor();

        Object packet = new HandlePacketPlayOutWorldParticles(
                particle, (float) x, (float) y, (float) z,
                bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue()).getPacket();

        return packet;
    }

    public static void sendPacketAllPlayers(Object packet) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            Object handle = HandleUtils.getHandleField(online, "playerConnection");
            HandleSendPacket.send(handle, packet);
        }
    }
}
