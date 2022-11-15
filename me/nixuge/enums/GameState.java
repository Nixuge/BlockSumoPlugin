package me.nixuge.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

import me.nixuge.listeners.game.BlockPlaceDestroyListener;
import me.nixuge.listeners.game.GameInventoryChangeListener;
import me.nixuge.listeners.game.GameInventoryListener;
import me.nixuge.listeners.game.GameJoinQuitListener;
import me.nixuge.listeners.game.PlayerInteractListener;
import me.nixuge.listeners.game.PlayerRespawnListener;
import me.nixuge.listeners.lobby.LobbyInventoryListener;
import me.nixuge.listeners.lobby.LobbyJoinQuitListener;

public enum GameState {
    WAITING(new Class<?>[] {
        LobbyJoinQuitListener.class,
        LobbyInventoryListener.class
    }),
    PLAYING(new Class<?>[] {
        GameJoinQuitListener.class,
        BlockPlaceDestroyListener.class,
        GameInventoryChangeListener.class,
        PlayerRespawnListener.class,
        PlayerInteractListener.class,
        GameInventoryListener.class
    }),
    DONE(new Class<?>[] {
        GameJoinQuitListener.class,
    });

    private final Class<?>[] classes;
    private List<Listener> instances;

    private GameState(Class<?>[] classes) {
        this.classes = classes;
        this.instances = new ArrayList<>();
    }

    public Class<?>[] getListeners() {
        return classes;
    }
    public List<Listener> getInstances() {
        return instances;
    }
    public void addInstance(Listener listener) {
        instances.add(listener);
    }
    public void clearInstances() {
        this.instances = new ArrayList<>();
    }
}
