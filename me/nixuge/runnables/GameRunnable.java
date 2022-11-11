package me.nixuge.runnables;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.runnables.particle.MiddleParticleRunnable;
import me.nixuge.runnables.particle.PlayerRespawnParticle;

public class GameRunnable extends BukkitRunnable {

    // TODO: refactor this class

    private int time = 0;
    private int last_bonus_spawn = 0;

    private BlockSumo plugin = BlockSumo.getInstance();

    @Override
    public void run() {
        // TODO:
        time++;

        MiddleParticleRunnable run = new MiddleParticleRunnable(60);
        run.runTaskTimer(plugin, 1, 1);

        PlayerRespawnParticle r = new PlayerRespawnParticle(20, Bukkit.getPlayer("Nixuge"));
        r.runTaskTimer(plugin, 1, 1);
    }
}
