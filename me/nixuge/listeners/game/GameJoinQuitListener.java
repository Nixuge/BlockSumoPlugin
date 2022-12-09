package me.nixuge.listeners.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.config.Lang;
import me.nixuge.objects.BsPlayer;
import me.nixuge.utils.PlayerUtils;

public class GameJoinQuitListener implements Listener {

    PlayerManager playerMgr;
    GameManager gameMgr;

    public GameJoinQuitListener() {
        gameMgr = BlockSumo.getInstance().getGameMgr();
        playerMgr = gameMgr.getPlayerMgr();
    }

    // public void onPlayerLogin(PlayerLoginEvent event)
    // Now handled on onPlayerJoin for spectators.
    

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (!playerMgr.isPlayerInGameList(p)) {
            p.sendMessage(Lang.get("joinquit.game.alreadystarted"));
        }

        BsPlayer bsPlayer = playerMgr.getBsPlayer(p);
        if (bsPlayer == null || (bsPlayer != null && bsPlayer.isDead())) {
            PlayerUtils.hidePlayer(p);
            event.setJoinMessage(null);
            p.teleport(gameMgr.getMcMap().getCenter());
            return;
        }

        event.setJoinMessage(Lang.get("joinquit.game.rejoined", p.getName()));
        playerMgr.setPlayerLogin(p, true);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        if (PlayerUtils.isHidden(p)) {
            event.setQuitMessage(null);
            return;
        }

        event.setQuitMessage(Lang.get("joinquit.game.quit", p.getName()));
        playerMgr.setPlayerLogin(p, false);
    }
}
