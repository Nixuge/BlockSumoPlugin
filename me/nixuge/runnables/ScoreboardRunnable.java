package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.enums.Color;
import me.nixuge.enums.GameState;
import me.nixuge.utils.IncreasingNumber;
import me.nixuge.utils.game.BsPlayer;
import me.nixuge.utils.specific.TextUtils;

public class ScoreboardRunnable extends BukkitRunnable {
    // pretty dirty class
    // but it's technically made of a bunch of string building
    // with conditions, so yeah excepted
    private final GameManager gameManager = BlockSumo.getInstance().getGameMgr();
    private final PlayerManager pManager = gameManager.getPlayerMgr();
    private List<BsPlayer> orderedPlayerList = new ArrayList<BsPlayer>();
    private boolean isEnded = false;

    private Map<Integer, String> currentSBValues;
    private IncreasingNumber incr;
    private int killsIndex;

    private void buildValuesMap() {
        currentSBValues = new HashMap<Integer, String>();
        incr = new IncreasingNumber();

        currentSBValues.put(incr.getNumber(), "§8§m--------------------"); 

        _buildFooter();
        _buildPlayerLives();
        _buildTop();

        currentSBValues.put(incr.getNumber(), "§r§8§m--------------------"); 
    }

    private void _buildFooter() {
        if (isEnded) {
            currentSBValues.put(incr.getNumber(), "§nGame ended !");
            currentSBValues.put(incr.getNumber(), "§e§l§l§o§r");
        }
        // if bonus spawning, display that
        int nextSpawnTime = gameManager.getGameRunnable().getNextSpawnTime();
        if (nextSpawnTime > -3 && !isEnded) { // -3 = 3s after the bonus spawn
            String s = nextSpawnTime > 0 ? "§fMid bonus in " + nextSpawnTime + "s" : "§fMid bonus spawned !";
            currentSBValues.put(incr.getNumber(), s);
            currentSBValues.put(incr.getNumber(), "§e§l§l§o§r");
        }
    }

    private void _buildPlayerLives() {
        for (BsPlayer player : orderedPlayerList) {
            if (player.isDead())
                continue;

            int lives = player.getLives();
            boolean isLoggedOn = player.isLoggedOn();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(isLoggedOn ? player.getColor().getChatColor() : "§7§m§o");
            stringBuilder.append(player.getBukkitPlayer().getName());
            stringBuilder.append(": ");
            stringBuilder.append(isLoggedOn ? colorFromLives(lives) : "§7§m§o");
            stringBuilder.append(lives);

            currentSBValues.put(incr.getNumber(), stringBuilder.toString());
        }
    }

    private String colorFromLives(int lives) {
        if (lives >= 6) {
            return Color.GREEN.getChatColor();
        } else if (lives >= 4) {
            return Color.LIME.getChatColor();
        } else if (lives >= 2) {
            return Color.YELLOW.getChatColor();
        }
        return Color.RED.getChatColor(); // lives <= 1
    }

    private void _buildTop() {
        currentSBValues.put(incr.getNumber(), "§r§r§r");
        killsIndex = incr.getNumber(); //"kills" line
        currentSBValues.put(incr.getNumber(), "§fTimer: " + TextUtils.secondsToMMSS(gameManager.getGameRunnable().getTime()));
    }

    @Override
    public void run() {
        // copy the arraylist to order it w/o concurrency errors
        orderedPlayerList = new ArrayList<>(pManager.getPlayers());
        orderedPlayerList.sort(Comparator.comparing(BsPlayer::getLives));

        isEnded = gameManager.getGameState().equals(GameState.DONE);

        buildValuesMap();

        for (BsPlayer bsPlayer : orderedPlayerList) {
            Player p = bsPlayer.getBukkitPlayer();
            Scoreboard scoreboard = p.getScoreboard();

            // reset the objective
            Objective tmp = scoreboard.getObjective("BlockSumo");
            if (tmp != null)
                tmp.unregister();

            //init it again
            Objective objective = scoreboard.registerNewObjective("BlockSumo", "main");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("§b§6BlockSumo");

            //build scoreboard from the map
            currentSBValues.forEach((index, string) -> 
                objective.getScore(string).setScore(index)
            );
            //build the missing kill key (different for every player)
            objective.getScore("§fKills: §c" + bsPlayer.getKills()).setScore(killsIndex);


            if (bsPlayer.isLoggedOn())
                p.setScoreboard(scoreboard);

            if (isEnded)
                cancel();
        }
    }
}
