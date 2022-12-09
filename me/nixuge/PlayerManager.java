package me.nixuge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import me.nixuge.enums.Color;
import me.nixuge.objects.BsPlayer;

public class PlayerManager {
    private final Random rand = new Random();
    private List<BsPlayer> players = new ArrayList<BsPlayer>();
    private List<Color> usedColors = new ArrayList<Color>();

    public List<BsPlayer> getPlayers() {
        return players;
    }

    public boolean isPlayerInGameList(BsPlayer bsPlayer) {
        return players.contains(bsPlayer);
    }

    public boolean isPlayerInGameList(Player player) {
        return getBsPlayer(player) != null;
        // if non null; player present.
    }

    public BsPlayer getBsPlayer(Player player) {
        return getBsPlayer(player.getName());
    }
    public BsPlayer getBsPlayer(Zombie entity) {
        return getBsPlayer(entity.getCustomName());
    }
    public BsPlayer getBsPlayer(String playerName) {
        for (BsPlayer bsPlayer : players) {
            if (bsPlayer.getName().equals(playerName)) {
                return bsPlayer;
            }
        }
        return null;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(Player player) {
        if (isPlayerInGameList(player))
            return;
        // get random color
        Color color = Color.getRandomColor();
        if (!(usedColors.size() >= Color.values().length)) {
            while (usedColors.contains(color)) {
                color = Color.values()[rand.nextInt(Color.values().length)];
            }
        }
        usedColors.add(color);
        players.add(new BsPlayer(player, color));
    }

    public void removePlayer(Player player) {
        BsPlayer bsPlayer = getBsPlayer(player);
        if (bsPlayer == null)
            return;
        players.remove(bsPlayer);
    }

    public void setPlayerLogin(Player player, boolean isLoggedOn) {
        BsPlayer bsPlayer = getBsPlayer(player);
        if (bsPlayer == null)
            return;

        bsPlayer.setBukkitPlayer(player);
        bsPlayer.setIsLoggedOn(isLoggedOn);
    }
}
