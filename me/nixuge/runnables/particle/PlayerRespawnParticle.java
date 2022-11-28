package me.nixuge.runnables.particle;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.reflections.ParticleEnum;
import me.nixuge.reflections.ParticleUtils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerRespawnParticle extends BukkitRunnable {

    private final int maxTick;
    private final Player player;

    private int tick = 0;

    public PlayerRespawnParticle(int maxTick, Player player) {
        this.maxTick = maxTick;
        this.player = player;
    }

    @Override
    public void run() {
        summonParticle();

        if (tick >= maxTick) {
            cancel();
            return;
        }
        tick++;
    }

    private void summonParticle() {
        Location pLoc = player.getLocation();


        ParticleUtils.sendParticlePacket(
                ParticleEnum.ENCHANTMENT_TABLE, pLoc.getX(), pLoc.getY() + 1, pLoc.getZ(),
                null);
    }
}
