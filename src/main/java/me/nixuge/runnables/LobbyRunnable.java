package me.nixuge.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.config.Config;

public class LobbyRunnable extends BukkitRunnable {

    @SuppressWarnings("unused")
    private final int minPlayerCount;
    @SuppressWarnings("unused")
    private final int maxPlayerCount;
    private int remainingTime = 0;

    public LobbyRunnable() {
        this.minPlayerCount = Config.game.getMinPlayers();
        this.maxPlayerCount = Config.game.getMaxPlayers();
    }

    @Override
    public void run() {
        //TODO: lobby lol
    }
    
    public int getRemainingTime() {
        return remainingTime;
    }
}
