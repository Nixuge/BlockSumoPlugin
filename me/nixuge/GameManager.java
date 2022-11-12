package me.nixuge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.nixuge.enums.GameState;
import me.nixuge.runnables.BlockDestroyRunnable;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.runnables.ScoreboardRunnable;
import me.nixuge.utils.BsPlayer;
import me.nixuge.utils.ScoreboardUtils;
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
        // unregister previous ones
        Listener[] listeners = state.getListeners();
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
        // register new ones & set state
        state = gameState;
        for (Listener listener : gameState.getListeners()) {
            blockSumo.getPluginManager().registerEvents(listener, blockSumo);
        }
    }

    private McMap map;
    private PlayerManager pManager = new PlayerManager();
    private GameState state = GameState.WAITING;
    private BlockSumo blockSumo;

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

    public PlayerManager getPlayerMgr() {
        return pManager;
    }

    public void startGame() {
        startGame(false);
    }

    public void startGame(boolean bypass) {
        if (pManager.getPlayers().size() < 2 && !bypass) {
            Bukkit.broadcastMessage("Not enough players !");
            return;
        }
        if (state != GameState.WAITING && !bypass) {
            Bukkit.broadcastMessage("Wrong state to start a game !" + state);
            return;
        }
        TextUtils.broadcastGame("Starting!");

        setGameState(GameState.PLAYING);

        pManager.getPlayers().forEach((p) -> p.getBukkitPlayer().teleport(map.getRandomSpawn()));

        blockDestroyRunnable = new BlockDestroyRunnable();
        blockDestroyRunnable.runTaskTimer(blockSumo, 1, 1);

        gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(blockSumo, 20, 20);

        ScoreboardUtils.resetScoreboards();
        ScoreboardRunnable r = new ScoreboardRunnable();
        r.runTaskTimer(blockSumo, 0, 20);
    }

    public void checkGameEnd() {
        int alivePlayerCount = 0;
        for (BsPlayer p : pManager.getPlayers()) {
            if (!p.isDead())
                alivePlayerCount++;
        }
        if (alivePlayerCount < 2)
            endGame();
    }

    private void endGame() {
        List<BsPlayer> winners = new ArrayList<BsPlayer>();
        for (BsPlayer p : pManager.getPlayers()) {
            if (!p.isDead())
                winners.add(p);
        }

        setGameState(GameState.DONE);
        Bukkit.broadcastMessage("GAME DONE PLAYING !");
    }
}
