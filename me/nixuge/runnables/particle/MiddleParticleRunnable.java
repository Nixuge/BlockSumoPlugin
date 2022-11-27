package me.nixuge.runnables.particle;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.reflections.ParticleUtils;
import me.nixuge.reflections.particleUtils.ParticleEnum;

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
        if (y > 2.1 + yOffset) {
            yOffset = rand.nextFloat() * 1;
            y = yOffset;
            particleLoc = originalParticleLoc.clone().add(0, -y, 0);
        }
        summonCurrentParticle1_8(0);
        summonCurrentParticle1_8(2f);
        y += 0.05f;

        if (tick >= maxTick) {
            cancel();
            return;
        }
        tick++;
    }

    private void summonCurrentParticle1_8(float yAdd) {
        float radius = 0.75f;
        double x = radius * Math.cos((y + yAdd) * 5);
        double z = radius * Math.sin((y + yAdd) * 5);

        ParticleUtils.sendParticlePacket(ParticleEnum.FLAME,
                particleLoc.getX() + x, particleLoc.getY() + y, particleLoc.getZ() + z,
                0, 0, 0, 1);
    }
}
