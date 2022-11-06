package me.nixuge;

import org.bukkit.entity.Player;

import me.nixuge.enums.PlayerState;

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
}
