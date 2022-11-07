package me.nixuge.utils;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
import me.nixuge.enums.PlayerState;
import me.nixuge.runnables.GameRunnable;

public class BsPlayer {
    private final Player player;
    private PlayerState state;

    public BsPlayer(Player player, PlayerState pState) {
        this.player = player;
        this.state = pState;
    }

    public BsPlayer(Player player) {
        this.player = player;
        this.state = PlayerState.LOGGED_ON;
    }

    public Player getBukkitPlayer() {
        return player;
    }

    public PlayerState getPlayerState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public void addBlock(Block block) {
        GameRunnable gr = BlockSumo.getInstance().getGameManager().getGameRunnable();
        if (gr == null) {
            Bukkit.broadcastMessage("This shouldn't happen! avoakn");
            return;
        }

        gr.addBlock(new ExpiringBlock(gr.getTime(), block.getLocation()));
    }
}
