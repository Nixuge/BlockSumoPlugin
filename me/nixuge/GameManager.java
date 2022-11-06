package me.nixuge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.nixuge.enums.GameState;
import me.nixuge.enums.PlayerState;
import me.nixuge.runnables.GeneratorRunnable;

public class GameManager {

    public GameManager() {
        // HARDCODED FOR NOW
        List<Location> spawns = new ArrayList<>();
        spawns.add(new Location(Bukkit.getWorld("world"), 96, 67, 63));
        spawns.add(new Location(Bukkit.getWorld("world"), 96, 67, 65));

        map = new McMap(spawns, new Location(Bukkit.getWorld("world"), 93, 66, 64));
    }

    // HARDCODED FOR NOW
    private McMap map;
    private List<BsPlayer> players = new ArrayList<BsPlayer>();
    private GameState state = GameState.WAITING;
    private BlockSumo blockSumo = BlockSumo.getInstance();

    // runnables
    private GeneratorRunnable genRunnable;

    public GameState getGameState() {
        return state;
    }

    public boolean isPlayerInGameList(BsPlayer bsPlayer) {
        return players.contains(bsPlayer);
    }

    public boolean isPlayerInGameList(Player player) {
        return getExistingBsPlayerFromBukkit(player) != null;
        // if non null; player present.
    }

    public BsPlayer getExistingBsPlayerFromBukkit(Player player) {
        for (BsPlayer bsPlayer : players) {
            if (bsPlayer.getBukkitPlayer().equals(player)) {
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
            Bukkit.broadcastMessage("Player " + player.getName() + " joined.");
            players.add(new BsPlayer(player));
        }
    }

    public void removePlayer(Player player) {
        BsPlayer bsPlayer = getExistingBsPlayerFromBukkit(player);
        if (bsPlayer == null) return;
        players.remove(bsPlayer);
    }

    public void setPlayerLogin(Player player, PlayerState pstate) {
        BsPlayer bsPlayer = getExistingBsPlayerFromBukkit(player);
        if (bsPlayer == null)
            return;

        bsPlayer.setState(pstate);
        if (pstate.equals(PlayerState.LOGGED_ON)) {
            Bukkit.broadcastMessage("Player " + player.getName() + "logged back on");
        } else {
            Bukkit.broadcastMessage("Player " + player.getName() + "logged off");
        }
    }

    public void startGame() {
        if (players.size() < 2) {
            Bukkit.broadcastMessage("Not enough players !");
        }
        if (state != GameState.WAITING) {
            Bukkit.broadcastMessage("Wrong time to start a game !");
        }
        Bukkit.broadcastMessage("Starting!");

        players.forEach((p) -> p.getBukkitPlayer().teleport(map.getRandomSpawn()));
        genRunnable = new GeneratorRunnable();
        genRunnable.runTaskTimer(blockSumo, 20, 20);
    }
}
