package me.nixuge.runnables;

import org.bukkit.scheduler.BukkitRunnable;

public class LobbyRunnable extends BukkitRunnable {

    private final int minPlayerCount;
    private final int maxPlayerCount;
    private int remainingTime = 0;

    public LobbyRunnable(int minPlayerCount, int maxPlayerCount) {
        this.minPlayerCount = minPlayerCount;
        this.maxPlayerCount = maxPlayerCount;
    }

    @Override
    public void run() {
        //TODO: lobby lol
    }
    
    public int getRemainingTime() {
        return remainingTime;
    }
}
