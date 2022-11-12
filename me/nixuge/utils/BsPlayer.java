package me.nixuge.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
import me.nixuge.enums.Color;
import me.nixuge.runnables.BlockDestroyRunnable;

public class BsPlayer {
    private final Color color;
    private Player player;
    private boolean isLoggedOn;
    private int lives = 5;

    public BsPlayer(Player player, Color color) {
        this.player = player;
        this.isLoggedOn = true;
        this.color = color;
    }

    public BsPlayer(Player player) {
        this.player = player;
        this.isLoggedOn = true;
        this.color = Color.values()[new Random().nextInt(Color.values().length)];
    }

    public void setBukkitPlayer(Player player) {
        // Bukkit player object gets changed on relog, so need this
        this.player = player;
    }
    public Player getBukkitPlayer() {
        return player;
    }

    public void setIsLoggedOn(boolean isLoggedOn) {
        this.isLoggedOn = isLoggedOn;
    }
    public boolean isLoggedOn() {
        return isLoggedOn;
    }

    public void addLive() {
        this.lives++;
    }
    public void removeLive() {
        this.lives--;
    }

    public int getLives() {
        return lives;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDead() {
        return lives < 1;
    }


    public void addBlock(Block block) {
        BlockDestroyRunnable bdr = BlockSumo.getInstance().getGameMgr().getBlockDestroyRunnable();
        if (bdr == null) {
            Bukkit.broadcastMessage("This shouldn't happen! avoakn");
            return;
        }

        bdr.addBlock(new ExpiringBlock(bdr.getTickTime(), block.getLocation()));
    }
}
