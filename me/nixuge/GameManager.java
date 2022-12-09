package me.nixuge;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.enums.GameState;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.McMap;
import me.nixuge.runnables.BlockManagerRunnable;
import me.nixuge.runnables.FireworkRunnable;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.runnables.ScoreboardRunnable;
import me.nixuge.runnables.TargetterRunnable;
import me.nixuge.utils.InventoryUtils;
import me.nixuge.utils.ScoreboardUtils;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class GameManager {

    public GameManager() {
        map = new McMap(
                Config.map.getSpawns(),
                Config.map.getCenterBlock(),
                Config.map.getWorld(),
                Config.map.getInnerAreas());

        blockSumo = BlockSumo.getInstance();
        setGameState(GameState.WAITING);
    }

    private GameRunnable gameRunnable;
    private BlockManagerRunnable blockDestroyRunnable;
    private ScoreboardRunnable scoreboardRunnable;
    private TargetterRunnable targetterRunnable;

    public GameRunnable getGameRunnable() {
        return gameRunnable;
    }

    public BlockManagerRunnable getBlockDestroyRunnable() {
        return blockDestroyRunnable;
    }

    public ScoreboardRunnable getScoreboardRunnable() {
        return scoreboardRunnable;
    }

    public TargetterRunnable getTargetterRunnable() {
        return targetterRunnable;
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
        Logger.log(LogLevel.DEBUG, "Setting gamestate to " + gameState.toString());
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
                Logger.log(LogLevel.DEBUG, "Registered listener: " + c.getSimpleName());
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            // ignore all errors because why should we look at those
            Logger.log(LogLevel.ERROR, "Exception happened while registering listener !");
            e.printStackTrace();
        }
        Logger.log(LogLevel.DEBUG, "Done setting gamestate");
    }

    public void startGame() {
        startGame(false);
    }

    public void startGame(boolean bypass) {
        if (bypass) {
            Logger.log(LogLevel.WARNING, "Bypassing the startgame checks !");
        }
        // checks
        if (pManager.getPlayers().size() < Config.game.getMinPlayers() && !bypass) {
            Logger.logBC(LogLevel.WARNING, Lang.get("game.starting.notenoughplayers"));
            return;
        }
        if (state != GameState.WAITING && !bypass) {
            Logger.logBC(LogLevel.WARNING, Lang.get("game.starting.wrongstate", state));
            return;
        }
        Logger.logIG(Lang.get("game.starting.starting"));

        // clear all zombies from the map
        for (Entity mob : map.getWorld().getEntities()) {
            if (mob.getType().equals(EntityType.ZOMBIE)) {
                mob.remove();
            }
        }

        // tp players & init their inventory
        pManager.getPlayers().forEach((p) -> p.getBukkitPlayer().teleport(map.getRandomSpawn()));
        InventoryUtils.setupInventories(pManager.getPlayers());
        Logger.log(LogLevel.DEBUG, "Set player invs");

        // set runnables
        blockDestroyRunnable = new BlockManagerRunnable();
        blockDestroyRunnable.runTaskTimer(blockSumo, 1, 1);
        Logger.log(LogLevel.DEBUG, "Ran blockDestroyRunnable");

        gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(blockSumo, 20, 20);
        Logger.log(LogLevel.DEBUG, "Ran gameRunnable");

        ScoreboardUtils.resetScoreboards();
        scoreboardRunnable = new ScoreboardRunnable();
        scoreboardRunnable.runTaskTimer(blockSumo, 0, 20);
        Logger.log(LogLevel.DEBUG, "Ran scoreboardRunnable");

        int captureDelayTick = Config.target.getCaptureDelayTick();
        targetterRunnable = new TargetterRunnable();
        targetterRunnable.runTaskTimer(blockSumo, captureDelayTick, captureDelayTick);
        Logger.log(LogLevel.DEBUG, "Ran targetterRunnable");

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
            endGameDelayed();
    }

    public void endGameDelayed() { // made to avoid having a "x died" after the game end message
        new BukkitRunnable() {
            @Override
            public void run() {
                endGame();
            }
        }.runTaskLater(blockSumo, 1);
    }

    public void endGame() {
        List<BsPlayer> winners = new ArrayList<BsPlayer>();
        for (BsPlayer p : pManager.getPlayers()) {
            if (!p.isDead())
                winners.add(p);
        }

        setGameState(GameState.DONE);
        gameRunnable.cancel();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(GameMode.CREATIVE);
        }

        Bukkit.broadcastMessage(
                winners.size() > 1 ? Lang.get("game.ending.gamedonewinners") : Lang.get("game.ending.gamedonewinner"));

        for (BsPlayer p : winners) {
            Bukkit.broadcastMessage("- " + p.getColoredName());
        }
        new FireworkRunnable(winners, Config.game.getFireworkMaxTickTime()).runTaskTimer(blockSumo, 1, 1);
    }
}
