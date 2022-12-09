package me.nixuge.runnables.particle;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.reflections.packet.HandleParticleSend;
import me.nixuge.reflections.packet.ParticleEnum;

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

        new HandleParticleSend(ParticleEnum.ENCHANTMENT_TABLE,
                pLoc.getX(), pLoc.getY() + 1, pLoc.getZ(),
                0.2, 0.5, 0.2, 8, null)
                .sendPacketAllPlayers();
    }
}
