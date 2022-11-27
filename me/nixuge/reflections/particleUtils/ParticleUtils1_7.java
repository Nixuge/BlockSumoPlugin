package me.nixuge.reflections.particleUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;


public class ParticleUtils1_7 {
    public static PacketPlayOutWorldParticles getParticlePacket(String particle, double x, double y, double z,
    me.nixuge.enums.Color color) {

        //format:
        //particlename
        //x, y, z
        //R, G, B
        //speed, count?
        // 2do (maybe): use the count and not hardcode 0
        // 2do: make block break particles work like in 1.8
        Color bukkitColor = (color == null) ?
            Color.fromRGB(0, 0, 0) :
            color.getDyeColor().getColor();

        // Bukkit.broadcastMessage(bukkitColor + " color");
        // Bukkit.broadcastMessage(particle + " particlename");

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                particle, (float) x, (float) y, (float) z,
                bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue(),
                1, 0);

        return packet;
    }

    public static PacketPlayOutWorldParticles getParticlePacket(ParticleEnum particle, double x, double y, double z,
        me.nixuge.enums.Color color, int count) {
        return getParticlePacket(particle.getParticleName(), x, y, z, color);
    }

    public static void sendPacketAllPlayers(PacketPlayOutWorldParticles packet) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
