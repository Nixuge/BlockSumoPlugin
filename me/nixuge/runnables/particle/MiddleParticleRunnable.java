package me.nixuge.runnables.particle;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class MiddleParticleRunnable extends BukkitRunnable {

    private final Random rand = new Random();
    private final Location originalParticleLoc = BlockSumo.getInstance().getGameMgr()
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
        if (y > 2.2 + yOffset) {
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

        PacketPlayOutWorldParticles packet = PacketUtils.getParticlePacket(EnumParticle.FLAME,
                particleLoc.getX() + x, particleLoc.getY() + y, particleLoc.getZ() + z);
            
        PacketUtils.sendPacketAllPlayers(packet);
    }
}
