package me.nixuge.runnables;

import org.bukkit.scheduler.BukkitRunnable;

public class LobbyRunnable extends BukkitRunnable {

    private int remainingTime = 0;
    private int maxPlayerCount = 8;
    private int minPlayerCount = 2;
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
    
    public int getRemainingTime() {
        return remainingTime;
    }
}
