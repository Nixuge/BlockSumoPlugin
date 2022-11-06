package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.utils.ExpiringBlock;

public class GameRunnable extends BukkitRunnable {

    private int time = 0;
    private int last_bonus_spawn = 0;
    private List<ExpiringBlock> blocks = new ArrayList<>();

    @Override
    public void run() {
        //TODO:



        time++;
    }
    
    private void changeExpiringBlocks() {
        for(ExpiringBlock block : blocks) {
            //TODO: remove block or change break % here
            //see ExpiringBlock.java
        }
    }

    private void spawnBonus() {
        //make it so that
        //this function is harder to call right after a bonus
        //eg, 0.1% chance 5s after a bonus, 10% chance 23s after a bonus
    }



    public int getTime() {
        return time;
    }
    public void addBlock(ExpiringBlock block) {
        blocks.add(block);
    }
    public void removeBlock(ExpiringBlock block) {
        blocks.remove(block);
    }

    public void removeBlock(Location location) {
        blocks.forEach((b) -> {
            if (location.equals(b.getLocation())) blocks.remove(b);
        });
    }
}
