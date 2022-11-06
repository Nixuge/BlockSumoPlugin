package me.nixuge.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
import me.nixuge.enums.PlayerState;
import me.nixuge.runnables.GameRunnable;

public class BsPlayer {
    private final Player player;
    private PlayerState state;
    private List<Block> placedBlocks = new ArrayList<>();

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
        int current_time = gr.getTime();
        int expiration_time = current_time + 60; // for now hardcoded 60, need diff vals for mid and other

        gr.addBlock

    }
}
