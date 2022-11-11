package me.nixuge.listeners.game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.enums.PlayerState;

public class GameJoinQuitListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(event.getPlayer().getName() + " rejoined the game !");

        GameManager mgr = BlockSumo.getInstance().getGameManager();
        if (!mgr.isPlayerInGameList(event.getPlayer())) {
            event.getPlayer().kickPlayer("Game already started !");
            return;
        }
        mgr.setPlayerLogin(event.getPlayer(), PlayerState.LOGGED_ON);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(event.getPlayer().getName() + " quit the game !");

        GameManager mgr = BlockSumo.getInstance().getGameManager();
        mgr.setPlayerLogin(event.getPlayer(), PlayerState.LOGGED_OFF);
    }
}
