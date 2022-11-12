package me.nixuge.runnables;

import java.util.Random;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.BlockSumo;
import me.nixuge.runnables.particle.MiddleParticleRunnable;
import me.nixuge.utils.TextUtils;

public class GameRunnable extends BukkitRunnable {

    private final BlockSumo plugin = BlockSumo.getInstance();
    private final Random rand = new Random();

    private int time = 1;
    private int previousLastBonusSpawn = 59;
    private int lastBonusSpawn = 60;

    @Override
    public void run() {
        manageBonus();

        time++;
        previousLastBonusSpawn = lastBonusSpawn;
        lastBonusSpawn++;
    }

    private void manageBonus() {
        if (lastBonusSpawn < 0 && (lastBonusSpawn == -10 || lastBonusSpawn >= -3)) {
            TextUtils.broadcastGame("Bonus spawning in " + -lastBonusSpawn + "s.");

        } else if (lastBonusSpawn == 0) {
            TextUtils.broadcastGame("Bonus spawning now !");
            spawnBonus();

        } else if (lastBonusSpawn > 0 && willBonusSpawn()) {
            lastBonusSpawn = -15;
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

    public int getNextSpawnTime() {
        return -previousLastBonusSpawn;
    }
    
    public int getTime() {
        return time;
    }
}
