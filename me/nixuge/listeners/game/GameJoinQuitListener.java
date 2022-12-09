package me.nixuge.listeners.game;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.PlayerManager;
import me.nixuge.config.Lang;

public class GameJoinQuitListener implements Listener {

    PlayerManager playerMgr;

    public GameJoinQuitListener() {
        playerMgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (!playerMgr.isPlayerInGameList(p)) {
            event.setJoinMessage(null);
            p.sendMessage(Lang.get("joinquit.game.alreadystarted"));
            p.setGameMode(GameMode.SPECTATOR);
            return;
        }
        event.setJoinMessage(Lang.get("joinquit.game.rejoined", event.getPlayer().getName()));
        playerMgr.setPlayerLogin(event.getPlayer(), true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(Lang.get("joinquit.game.quit", event.getPlayer().getName()));
        playerMgr.setPlayerLogin(event.getPlayer(), false);
    }
}
