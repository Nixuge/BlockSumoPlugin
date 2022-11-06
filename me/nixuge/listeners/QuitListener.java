package me.nixuge.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.enums.PlayerState;

public class QuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        GameManager mgr = BlockSumo.getInstance().getGameManager();
        mgr.setPlayerLogin(event.getPlayer(), PlayerState.LOGGED_OFF);
    }
}