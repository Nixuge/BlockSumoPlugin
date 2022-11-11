package me.nixuge.runnables.particle;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import me.nixuge.BlockSumo;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class MiddleParticleRunnable extends BukkitRunnable {

    private final Random rand = new Random();
    private final Location originalParticleLoc = BlockSumo.getInstance().getGameManager()
            .getMcMap().getCenter();

    private float y = 3;
    private float yOffset = 0;
    private Location particleLoc;
    private int tick = 0;
    private int maxTick = 0;

    public MiddleParticleRunnable(int maxTick) {
        this.maxTick = maxTick;
    }


    @Override
    public void run() {
        if (y > 2.1 + yOffset) {
            yOffset = rand.nextFloat() * 1;
            y = yOffset;
            particleLoc = originalParticleLoc.clone().add(0, -y, 0);
        }
        summonCurrentParticle(0);
        summonCurrentParticle(2f);
        y += 0.05f;

        if (tick >= maxTick) {
            cancel();
            return;
        }
        tick++;
    }

    private void summonCurrentParticle(float yAdd) {
        float radius = 0.75f;
        double x = radius * Math.cos((y + yAdd) * 5);
        double z = radius * Math.sin((y + yAdd) * 5);

        // PacketPlayOutWorldParticles:
        // particle, bool, x, y, z, x+-, y+-, z+-, idk, idk
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true,
                (float) (particleLoc.getX() + x), (float) (particleLoc.getY() + y), (float) (particleLoc.getZ() + z), 0,
                0, 0, 0, 1);

        sendPacketAllPlayers(packet);
    }

    private void sendPacketAllPlayers(PacketPlayOutWorldParticles packet) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
        }
    }

}
