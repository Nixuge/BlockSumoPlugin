package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.objects.BsPlayer;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class FireworkRunnable extends BukkitRunnable {
    private final Random rand = new Random();

    private final List<Player> playerList;
    private final int maxTick;
    private int currentTick = 0;

    public FireworkRunnable(List<Player> playerList, int maxTick) {
        this.playerList = playerList;
        this.maxTick = maxTick;
    }

    // Can't use 2 constructors w the same args except in the List type otherwise
    // java isnt happy
    public FireworkRunnable(List<BsPlayer> playerList, Integer maxTick) {
        this.playerList = new ArrayList<>();
        for (BsPlayer bsPlayer : playerList) {
            this.playerList.add(bsPlayer.getBukkitPlayer());
        }
        this.maxTick = maxTick;
    }

    @Override
    public void run() {
        if (currentTick >= maxTick) {
            Logger.log(LogLevel.DEBUG, "Finished running the firework runnable.");
            cancel();
            return;
        }

        for (Player p : playerList) {
            int chance = rand.nextInt(100);
            // 2% chance (or 1/50)
            // since this runs every tick (20 times/s), it should NOT match
            // (so spawn a firework) every ~2.5s per player
            if (chance > 2)
                continue;

            Location loc = p.getLocation();
            final Firework f = p.getWorld().spawn(loc, Firework.class);
            FireworkMeta fm = f.getFireworkMeta();
            fm.setPower(4); //flight duration? not sure
            fm.addEffect(FireworkEffect.builder()
                    .flicker(true)
                    .trail(true)
                    .with(FireworkEffect.Type.STAR)
                    .with(FireworkEffect.Type.BALL)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .withColor(Color.AQUA)
                    .withColor(Color.YELLOW)
                    .withColor(Color.RED)
                    .withColor(Color.WHITE)
                    .build());

            fm.setPower(0);
            f.setFireworkMeta(fm);
        }
        currentTick++;
    }
}