package me.nixuge.reflections.particleUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.nixuge.reflections.ReflectionInterface;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ParticleUtils1_8 extends ReflectionInterface {
    public static PacketPlayOutWorldParticles getParticlePacket(EnumParticle particle, double x, double y, double z,
            double xPlus, double yPlus, double zPlus, int count, int[] data) {
        // PacketPlayOutWorldParticles:
        // particle, boolidk, x, y, z, x+-, y+-, z+-, speed, number of particles
        // optional last argument: int[] data (see
        // spigotmc.org/threads/specify-block-data-on-effect-step_sound.196477)
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                particle, true,
                (float) x, (float) y, (float) z,
                (float) xPlus, (float) yPlus, (float) zPlus,
                0, count, data);

        return packet;
    }

    public static PacketPlayOutWorldParticles getParticlePacket(ParticleEnum particle, double x, double y, double z,
            double xPlus, double yPlus, double zPlus, int count, int[] data) {
        EnumParticle enumPart = EnumParticle.valueOf(particle.toString());
        return getParticlePacket(enumPart, x, y, z, xPlus, yPlus, zPlus, count, data);
    }

    public static void sendPacketAllPlayers(PacketPlayOutWorldParticles packet) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
