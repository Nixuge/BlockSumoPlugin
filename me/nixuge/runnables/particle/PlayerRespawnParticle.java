package me.nixuge.runnables.particle;

import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawnParticle extends BukkitRunnable {

    private int tick = 0;
    private int maxTick = 0;

    public PlayerRespawnParticle(int maxTick) {
        this.maxTick = maxTick;
    }


    @Override
    public void run() {
        //TODO: logic here

        if (tick >= maxTick) {
            cancel();
            return;
        }
        tick++;
    }
    
}
