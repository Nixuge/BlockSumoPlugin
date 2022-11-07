package me.nixuge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.nixuge.enums.GameState;
import me.nixuge.enums.PlayerState;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.utils.BsPlayer;
import me.nixuge.utils.TextUtils;

public class GameManager {

    public GameManager() {
        // HARDCODED FOR NOW
        List<Location> spawns = new ArrayList<>();
        spawns.add(new Location(Bukkit.getWorld("world"), 96, 67, 63));
        spawns.add(new Location(Bukkit.getWorld("world"), 96, 67, 65));

        map = new McMap(spawns, new Location(Bukkit.getWorld("world"), 93, 66, 64), Bukkit.getWorld("world"));
    }

    // HARDCODED FOR NOW
    private McMap map;
    private List<BsPlayer> players = new ArrayList<BsPlayer>();
    private GameState state = GameState.WAITING;
    private BlockSumo blockSumo = BlockSumo.getInstance();

    // runnables
    private GameRunnable gameRunnable;

    public GameRunnable getGameRunnable() {
        return gameRunnable;
    }

    public GameState getGameState() {
        return state;
    }

    public McMap getMcMap() {
        return map;
    }

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
        String bc = pstate.equals(PlayerState.LOGGED_ON) ?
            "Player " + player.getName() + "logged back on":
            "Player " + player.getName() + "logged off";
        TextUtils.broadcastGame(bc);
    }

    public void startGame() {
        if (players.size() < 2) {
            Bukkit.broadcastMessage("Not enough players !");
        }
        if (state != GameState.WAITING) {
            Bukkit.broadcastMessage("Wrong state to start a game !" + state);
        }
        TextUtils.broadcastGame("Starting!");

        players.forEach((p) -> p.getBukkitPlayer().teleport(map.getRandomSpawn()));
        gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(blockSumo, 20, 20);
    }
}
