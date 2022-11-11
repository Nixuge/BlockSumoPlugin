package me.nixuge.listeners.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;

public class LobbyJoinQuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(event.getPlayer().getName() + " joined the lobby !");

        GameManager mgr = BlockSumo.getInstance().getGameManager();
        mgr.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(event.getPlayer().getName() + " left the lobby !");

        GameManager mgr = BlockSumo.getInstance().getGameManager();
        mgr.removePlayer(event.getPlayer());
    }
}
