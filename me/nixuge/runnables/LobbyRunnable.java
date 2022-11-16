package me.nixuge.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.config.GameConfig;

public class LobbyRunnable extends BukkitRunnable {

    private final int minPlayerCount;
    private final int maxPlayerCount;
    private int remainingTime = 0;

    public LobbyRunnable() {
        this.minPlayerCount = GameConfig.getMinPlayers();
        this.maxPlayerCount = GameConfig.getMaxPlayers();
    }

    @Override
    public void run() {
        //TODO: lobby lol
    }
    
    public int getRemainingTime() {
        return remainingTime;
    }
}
