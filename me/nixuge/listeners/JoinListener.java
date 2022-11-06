package me.nixuge.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.enums.GameState;
import me.nixuge.enums.PlayerState;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.broadcastMessage("cc");
        GameManager mgr = BlockSumo.getInstance().getGameManager();
        if ((!mgr.isPlayerInGameList(event.getPlayer())) && (mgr.getGameState() != GameState.WAITING)) {
            event.getPlayer().kickPlayer("Game already started !");
            return;
        }
        mgr.setPlayerLogin(event.getPlayer(), PlayerState.LOGGED_ON);
        mgr.addPlayer(event.getPlayer());
    }
}
