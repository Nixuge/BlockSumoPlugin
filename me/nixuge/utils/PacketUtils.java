package me.nixuge.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class PacketUtils {
    public static PacketPlayOutWorldParticles getParticlePacket(EnumParticle particle, double x, double y, double z,
            double xPlus, double yPlus, double zPlus, int number) {
        // PacketPlayOutWorldParticles:
        // particle, boolidk, x, y, z, x+-, y+-, z+-, speed, number of particles
        //optional last argument: int[] data (see 
        //spigotmc.org/threads/specify-block-data-on-effect-step_sound.196477)
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                particle, true,
                (float) x, (float) y, (float) z,
                (float) xPlus, (float) yPlus, (float) zPlus,
                0, number);

        return packet;
    }

    public static PacketPlayOutWorldParticles getParticlePacket(EnumParticle particle, Location loc,
            double xPlus, double yPlus, double zPlus, int number) {
        return getParticlePacket(particle, loc.getX(), loc.getY(), loc.getZ(), xPlus, yPlus, zPlus, number);
    }

    public static PacketPlayOutWorldParticles getParticlePacket(EnumParticle particle, double x, double y, double z) {
        return getParticlePacket(particle, x, y, z, 0, 0, 0, 1);
    }

    public static PacketPlayOutWorldParticles getParticlePacket(EnumParticle particle, Location loc) {
        return getParticlePacket(particle, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, 1);
    }

    public static void sendPacketAllPlayers(PacketPlayOutWorldParticles packet) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
