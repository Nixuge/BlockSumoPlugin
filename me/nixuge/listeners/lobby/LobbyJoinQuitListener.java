package me.nixuge.listeners.lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.PlayerManager;
import me.nixuge.config.Lang;
import me.nixuge.utils.ScoreboardUtils;

public class LobbyJoinQuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(Lang.get("joinQuit.lobby.joined", event.getPlayer().getName()));
        
        ScoreboardUtils.resetScoreboard(event.getPlayer());

        PlayerManager mgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();
        mgr.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(Lang.get("joinQuit.lobby.left", event.getPlayer().getName()));

        PlayerManager mgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();
        mgr.removePlayer(event.getPlayer());
    }
}
