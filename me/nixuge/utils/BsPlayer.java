package me.nixuge.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import me.nixuge.BlockSumo;
import me.nixuge.enums.Color;
import me.nixuge.enums.PlayerState;
import me.nixuge.runnables.BlockDestroyRunnable;

public class BsPlayer {
    private final Color color;
    private Scoreboard scoreboard;
    private Player player;
    private PlayerState state;
    private int lives = 5;

    public BsPlayer(Player player, Color color) {
        this.player = player;
        this.state = PlayerState.LOGGED_ON;
        this.color = color;
    }

    public BsPlayer(Player player) {
        this.player = player;
        this.state = PlayerState.LOGGED_ON;
        this.color = Color.values()[new Random().nextInt(Color.values().length)];
    }

    public void setBukkitPlayer(Player player) {
        // Bukkit player object gets changed on relog, so need this
        this.player = player;
    }
    public Player getBukkitPlayer() {
        return player;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
    public PlayerState getState() {
        return state;
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

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public void setPlayerScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
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
