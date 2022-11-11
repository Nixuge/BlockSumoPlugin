package me.nixuge.runnables;

import java.util.Random;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.runnables.particle.MiddleParticleRunnable;
import me.nixuge.runnables.particle.PlayerRespawnParticle;
import me.nixuge.utils.TextUtils;

public class GameRunnable extends BukkitRunnable {

    private final BlockSumo plugin = BlockSumo.getInstance();
    private final Random rand = new Random();

    private int time = 1;
    private int lastBonusSpawn = 50;

    @Override
    public void run() {


        // PlayerRespawnParticle r = new PlayerRespawnParticle(20, Bukkit.getPlayer("Nixuge"));
        // r.runTaskTimer(plugin, 1, 1);
        // MiddleParticleRunnable run = new MiddleParticleRunnable(60);
        // run.runTaskTimer(plugin, 1, 1);
        manageBonus();

        time++;
        lastBonusSpawn++;
    }

    private void manageBonus() {
        if (lastBonusSpawn < 0 && (lastBonusSpawn == -10 || lastBonusSpawn >= -5)) {
            TextUtils.broadcastGame("Bonus spawning in " + -lastBonusSpawn + "s.");

        } else if (lastBonusSpawn == 0) {
            TextUtils.broadcastGame("Bonus spawning now !");
            spawnBonus();

        } else if (lastBonusSpawn > 0 && willBonusSpawn()) {
            lastBonusSpawn = -11;
            MiddleParticleRunnable run = new MiddleParticleRunnable(220);
            run.runTaskTimer(plugin, 1, 1);
        }
    }

    private boolean willBonusSpawn() {
        int randomPercent = rand.nextInt(100);
        //totally random formula
        double neededPercent = ((.0025 * (lastBonusSpawn * lastBonusSpawn)) - .2);
        // Bukkit.broadcastMessage("needed: " + neededPercent + " | random: " + randomPercent + " | time: " + time + " | lbs:" + last_bonus_spawn);
        return neededPercent  > randomPercent;
    }

    private void spawnBonus() {
        //TODO
    }

}
