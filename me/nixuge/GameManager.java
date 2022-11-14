package me.nixuge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.nixuge.enums.GameState;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.maths.Area;
import me.nixuge.objects.maths.XYZ;
import me.nixuge.runnables.BlockManagerRunnable;
import me.nixuge.runnables.GameRunnable;
import me.nixuge.runnables.ScoreboardRunnable;
import me.nixuge.utils.ScoreboardUtils;
import me.nixuge.utils.TextUtils;

public class GameManager {

    public GameManager() {
        // HARDCODED FOR NOW
        World world = Bukkit.getWorld("world");
        List<Location> spawns = new ArrayList<>();
        spawns.add(new Location(world, 96, 67, 63));
        spawns.add(new Location(world, 96, 67, 65));
        Location center = new Location(world, 93.5, 67, 64.5);

        map = new McMap(
            spawns, center,
            new Area(
                new XYZ(92, 66, 63),
                new XYZ(94, 69, 65)
            ), world);
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

        blockDestroyRunnable = new BlockManagerRunnable();
        blockDestroyRunnable.runTaskTimer(blockSumo, 1, 1);

        gameRunnable = new GameRunnable();
        gameRunnable.runTaskTimer(blockSumo, 20, 20);

        ScoreboardUtils.resetScoreboards();
        scoreboardRunnable = new ScoreboardRunnable();
        scoreboardRunnable.runTaskTimer(blockSumo, 0, 20);
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

        Bukkit.broadcastMessage("GAME DONE PLAYING ! WINNER(s):");
        for (BsPlayer p : winners) {
            Bukkit.broadcastMessage(p.getBukkitPlayer().getName());
        }
    }
}
