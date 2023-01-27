package me.nixuge.objects;

import org.bukkit.entity.Player;

public class Hit {
    private final int time;
    private final Player hitter;
    
    public Hit(int time, Player hitter) {
        this.time = time;
        this.hitter = hitter;
    }
    
    public int getHitTime() {
        return time;
    }
    public Player getHitter() {
        return hitter;
    }
}
