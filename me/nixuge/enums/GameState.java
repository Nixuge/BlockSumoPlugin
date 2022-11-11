package me.nixuge.enums;

import org.bukkit.event.Listener;

import me.nixuge.listeners.game.BlockPlaceDestroyListener;
import me.nixuge.listeners.game.GameInventoryChangeListener;
import me.nixuge.listeners.game.GameJoinQuitListener;
import me.nixuge.listeners.game.PlayerRespawnListener;
import me.nixuge.listeners.lobby.LobbyJoinQuitListener;

public enum GameState {
    WAITING(new Listener[] {
        new LobbyJoinQuitListener(),
    }),
    PLAYING(new Listener[] {
        new GameJoinQuitListener(),
        new BlockPlaceDestroyListener(),
        new GameInventoryChangeListener(),
        new PlayerRespawnListener()
    }),
    DONE(new Listener[] {
        new GameJoinQuitListener(),
    });

    private final Listener[] listeners;

    private GameState(Listener[] listeners) {
        this.listeners = listeners;
    }

    public Listener[] getListeners() {
        return listeners;
    }
}
