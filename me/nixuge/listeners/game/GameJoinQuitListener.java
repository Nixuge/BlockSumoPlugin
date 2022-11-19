package me.nixuge.listeners.game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.PlayerManager;
import me.nixuge.config.Lang;

public class GameJoinQuitListener implements Listener {
    
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        PlayerManager mgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();
        if (!mgr.isPlayerInGameList(event.getPlayer())) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Lang.get("joinQuit.game.alreadyStarted"));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(Lang.get("joinQuit.game.rejoined", event.getPlayer().getName()));

        PlayerManager mgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();
        mgr.setPlayerLogin(event.getPlayer(), true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(Lang.get("joinQuit.game.quit", event.getPlayer().getName()));

        PlayerManager mgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();
        mgr.setPlayerLogin(event.getPlayer(), false);
    }
}
