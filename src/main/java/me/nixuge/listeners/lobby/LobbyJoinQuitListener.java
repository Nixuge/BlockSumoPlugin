package me.nixuge.listeners.lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.BlockSumo;
import me.nixuge.PlayerManager;
import me.nixuge.config.Lang;
import me.nixuge.utils.KitEdit;
import me.nixuge.utils.ScoreboardUtils;

public class LobbyJoinQuitListener implements Listener {
    PlayerManager playerMgr;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (playerMgr == null)
            playerMgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();

        event.setJoinMessage(Lang.get("joinquit.lobby.joined", event.getPlayer().getName()));

        Player p = event.getPlayer();
        ScoreboardUtils.resetScoreboard(p);
        playerMgr.addPlayer(p);
        p.getInventory().clear();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (playerMgr == null)
            playerMgr = BlockSumo.getInstance().getGameMgr().getPlayerMgr();

        event.setQuitMessage(Lang.get("joinquit.lobby.left", event.getPlayer().getName()));

        Player p = event.getPlayer();
        playerMgr.removePlayer(p);

        KitEdit kitEdit = KitEdit.getFromPlayer(p);
        if (kitEdit != null)
            kitEdit.removeKitEdit();
    }
}
