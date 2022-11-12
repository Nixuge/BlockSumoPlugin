package me.nixuge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.nixuge.enums.PlayerState;
import me.nixuge.utils.BsPlayer;

public class PlayerManager {
    private List<BsPlayer> players = new ArrayList<BsPlayer>();

    public List<BsPlayer> getPlayers() {
        return players;
    }

    public boolean isPlayerInGameList(BsPlayer bsPlayer) {
        return players.contains(bsPlayer);
    }
    public boolean isPlayerInGameList(Player player) {
        return getExistingBsPlayerFromBukkit(player) != null;
        // if non null; player present.
    }

    public BsPlayer getExistingBsPlayerFromBukkit(Player player) {
        String playerName = player.getName();
        for (BsPlayer bsPlayer : players) {
            if (bsPlayer.getBukkitPlayer().getName().equals(playerName)) {
                return bsPlayer;
            }
        }
        return null;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(Player player) {
        if (!isPlayerInGameList(player)) {
            players.add(new BsPlayer(player));
        }
    }

    public void removePlayer(Player player) {
        BsPlayer bsPlayer = getExistingBsPlayerFromBukkit(player);
        if (bsPlayer == null)
            return;
        players.remove(bsPlayer);
    }

    public void setPlayerLogin(Player player, PlayerState pstate) {
        BsPlayer bsPlayer = getExistingBsPlayerFromBukkit(player);
        if (bsPlayer == null)
            return;

        bsPlayer.setState(pstate);
        bsPlayer.setBukkitPlayer(player);
    }
}
