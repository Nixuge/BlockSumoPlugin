package me.nixuge;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.enums.GameState;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.McMap;
import me.nixuge.runnables.BlockManagerRunnable;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.runnables.ScoreboardRunnable;
import me.nixuge.utils.InventoryUtils;
import me.nixuge.utils.ScoreboardUtils;
import me.nixuge.utils.TextUtils;

public class GameManager {

    public GameManager() {
        map = new McMap(
                Config.map.getSpawns(),
                Config.map.getCenterBlock(),
                Config.map.getCenterArea(),
                Config.map.getWorld());

        blockSumo = BlockSumo.getInstance();
        setGameState(GameState.WAITING);
    }

    private GameRunnable gameRunnable;
    private BlockManagerRunnable blockDestroyRunnable;
    private ScoreboardRunnable scoreboardRunnable;

    public GameRunnable getGameRunnable() {
        return gameRunnable;
    }

    public BlockManagerRunnable getBlockDestroyRunnable() {
        return blockDestroyRunnable;
    }

    public ScoreboardRunnable getScoreboardRunnable() {
        return scoreboardRunnable;
    }

    private McMap map;
    private PlayerManager pManager = new PlayerManager();
    private GameState state = GameState.WAITING;
    private BlockSumo blockSumo;

    public McMap getMcMap() {
        return map;
    }

    public GameState getGameState() {
        return state;
    }

    public PlayerManager getPlayerMgr() {
        return pManager;
    }

    private void setGameState(GameState gameState) {
        // unregister previous ones
        for (Listener listener : state.getInstances()) {
            HandlerList.unregisterAll(listener);
        }
        state.clearInstances();

        // set new state var
        state = gameState;

        // make new instances for the new ones from the classes
        Class<?>[] classes = gameState.getListeners();
        try {
            for (Class<?> c : classes) {
                Constructor<?> cons = c.getConstructor();
                Listener listener = (Listener) cons.newInstance();
                state.addInstance(listener);
                blockSumo.getPluginManager().registerEvents(listener, blockSumo);
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            // ignore all errors because why should we look at those
            e.printStackTrace();
        }
    }

    public void startGame() {
        startGame(false);
    }

    public void startGame(boolean bypass) {
        // checks
        if (pManager.getPlayers().size() < Config.game.getMinPlayers() && !bypass) {
            Bukkit.broadcastMessage(Lang.get("game.starting.notenoughplayers"));
            return;
        }
        if (state != GameState.WAITING && !bypass) {
            Bukkit.broadcastMessage(Lang.get("game.starting.wrongstate", state));
            return;
        }
        TextUtils.broadcastGame(Lang.get("game.starting.starting"));

        // tp players & init their inventory
        pManager.getPlayers().forEach((p) -> p.getBukkitPlayer().teleport(map.getRandomSpawn()));
        InventoryUtils.setupInventories(pManager.getPlayers());

        // set runnables
        blockDestroyRunnable = new BlockManagerRunnable();
        blockDestroyRunnable.runTaskTimer(blockSumo, 1, 1);

        gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(blockSumo, 20, 20);

        ScoreboardUtils.resetScoreboards();
        scoreboardRunnable = new ScoreboardRunnable();
        scoreboardRunnable.runTaskTimer(blockSumo, 0, 20);

        // Only once everything is set so the listeners constructors can do their job
        setGameState(GameState.PLAYING);
    }

    public void checkGameEnd() {
        int alivePlayerCount = 0;
        for (BsPlayer p : pManager.getPlayers()) {
            if (!p.isDead())
                alivePlayerCount++;
        }
        if (alivePlayerCount < 2)
            forceEndGame();
    }

    public void forceEndGame() {
        List<BsPlayer> winners = new ArrayList<BsPlayer>();
        for (BsPlayer p : pManager.getPlayers()) {
            if (!p.isDead())
                winners.add(p);
        }

        setGameState(GameState.DONE);
        gameRunnable.cancel();

        // TODO HERE: better end bc this sucks
        // TODO: gamedonewinner insteaed of gamedonewinners when single winner
        Bukkit.broadcastMessage(Lang.get("game.ending.gamedonewinners"));
        for (BsPlayer p : winners) {
            Bukkit.broadcastMessage(p.getName());
        }
    }
}
