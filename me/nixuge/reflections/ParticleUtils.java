package me.nixuge.reflections;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;

import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PlayerConnection;

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
            // Bukkit.broadcastMessage(handle.getClass() + "");
            // HandleSendPacket.send(handle, packet);
            // .playerConnection.sendPacket(packet);
            ((PlayerConnection)handle).sendPacket((Packet)packet);
        }
    }
}
