package me.nixuge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.nixuge.enums.GameState;
import me.nixuge.enums.PlayerState;
import me.nixuge.runnables.BlockDestroyRunnable;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.utils.BsPlayer;
import me.nixuge.utils.TextUtils;

public class GameManager {

    public GameManager() {
        // HARDCODED FOR NOW
        List<Location> spawns = new ArrayList<>();
        spawns.add(new Location(Bukkit.getWorld("world"), 96, 67, 63));
        spawns.add(new Location(Bukkit.getWorld("world"), 96, 67, 65));

        map = new McMap(spawns, new Location(Bukkit.getWorld("world"), 93.5, 67, 64.5), Bukkit.getWorld("world"));
        blockSumo = BlockSumo.getInstance();
        setGameState(GameState.WAITING);
    }

    private void setGameState(GameState gameState) {
        Bukkit.broadcastMessage("changing gamestate!!");
        Listener[] listeners = state.getListeners();
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }

        state = gameState;
        for (Listener listener : gameState.getListeners()) {
            Bukkit.broadcastMessage(String.valueOf(listener));
            Bukkit.broadcastMessage(String.valueOf(blockSumo));
            // Bukkit.broadcastMessage(String.valueOf(blockSumo));

            blockSumo.getPluginManager().registerEvents(listener, blockSumo);
        }
    }

    // HARDCODED FOR NOW
    private McMap map;
    private List<BsPlayer> players = new ArrayList<BsPlayer>();
    private GameState state = GameState.WAITING;
    private BlockSumo blockSumo;

    // runnables
    private GameRunnable gameRunnable;
    private BlockDestroyRunnable blockDestroyRunnable;

    public GameRunnable getGameRunnable() {
        return gameRunnable;
    }

    public BlockDestroyRunnable getBlockDestroyRunnable() {
        return blockDestroyRunnable;
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
        String playerName = player.getName();
        for (BsPlayer bsPlayer : players) {
            // if (bsPlayer.getBukkitPlayer().equals(player)) {
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
            Bukkit.broadcastMessage("Player " + player.getName() + " joined.");
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
        String bc = pstate.equals(PlayerState.LOGGED_ON) ? "Player " + player.getName() + "logged back on"
                : "Player " + player.getName() + "logged off";
        TextUtils.broadcastGame(bc);
    }

    public void startGame() {
        startGame(false);
    }

    public void startGame(boolean bypass) {
        if (players.size() < 2 && !bypass) {
            Bukkit.broadcastMessage("Not enough players !");
            return;
        }
        if (state != GameState.WAITING && !bypass) {
            Bukkit.broadcastMessage("Wrong state to start a game !" + state);
            return;
        }
        TextUtils.broadcastGame("Starting!");

        setGameState(GameState.PLAYING);

        players.forEach((p) -> p.getBukkitPlayer().teleport(map.getRandomSpawn()));

        blockDestroyRunnable = new BlockDestroyRunnable();
        blockDestroyRunnable.runTaskTimer(blockSumo, 1, 1);

        gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(blockSumo, 20, 20);
    }
}
