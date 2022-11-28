package me.nixuge.reflections;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class ParticleUtils extends ReflectionUtils {

    public static void sendParticlePacket(ParticleEnum particle, double x, double y, double z,
            me.nixuge.enums.Color color) {
        sendPacketAllPlayers(
                getParticlePacket(particle.toString(), x, y, z, color));
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
        if (color == null)
            color = me.nixuge.enums.Color.AQUA;

        Color bukkitColor = color.getDyeColor().getColor();

        Object packet = new HandlePacketPlayOutWorldParticles(
                particle, (float) x, (float) y, (float) z,
                bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue()).getPacket();

        return packet;
    }

    public static void sendPacketAllPlayers(Object packet) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            Object handle = HandleUtils.getHandle(((CraftPlayer)online).getHandle());
            Bukkit.broadcastMessage(handle.getClass() + "");
            HandleSendPacket.send(handle, packet);
            // .playerConnection.sendPacket(packet);
            // ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
