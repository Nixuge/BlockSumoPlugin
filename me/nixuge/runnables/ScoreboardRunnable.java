package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
import me.nixuge.utils.BsPlayer;
import me.nixuge.utils.TextUtils;

public class ScoreboardRunnable extends BukkitRunnable {

    // pretty dirty class
    // but it's technically made of a bunch of string building
    // with conditions, so yeah excepted
    // also TODO: find if you can clone the scoreboard content to another one
    // since everything is the same across all players (except kills) and yet
    // the logic is repeating
    private final GameManager gameManager = BlockSumo.getInstance().getGameMgr();
    private final PlayerManager pManager = gameManager.getPlayerMgr();
    private List<BsPlayer> orderedPlayerList = new ArrayList<BsPlayer>();
    private boolean isEnded = false;

    private Objective getBaseObjective(Scoreboard scoreboard) {
        Objective objective = scoreboard.registerNewObjective("BlockSumo", "main");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§b§6BlockSumo");
        objective.getScore("§8§m--------------------").setScore(1);
        return objective;
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

    private int setPlayerScoreboard(Objective objective, int currentIndex) {
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

            objective.getScore(stringBuilder.toString()).setScore(currentIndex);
            currentIndex++;
        }

        return currentIndex;
    }

    private Scoreboard buildScoreboard(Scoreboard scoreboard, BsPlayer bsPlayer) {
        Objective objective = getBaseObjective(scoreboard);
        int currentIndex = 2;

        if (isEnded) {
            objective.getScore("§nGame ended !").setScore(currentIndex);
            currentIndex++;
            objective.getScore("§e§l§l§o§r").setScore(currentIndex);
            currentIndex++;
        }
        // if bonus spawning, display that
        int nextSpawnTime = gameManager.getGameRunnable().getNextSpawnTime();
        if (nextSpawnTime > -3 && !isEnded) { // -3 = 3s after the bonus spawn
            if (nextSpawnTime > 0) {
                objective.getScore("§fMid bonus in " + nextSpawnTime + "s").setScore(currentIndex);
            } else {
                objective.getScore("§fMid bonus spawned !").setScore(currentIndex);
            }
            currentIndex++;
            objective.getScore("§e§l§l§o§r").setScore(currentIndex);
            currentIndex++;
        }

        // display score for every player
        currentIndex = setPlayerScoreboard(objective, currentIndex);

        // game timer
        objective.getScore("§r§r§r").setScore(currentIndex);
        currentIndex++;
        objective.getScore("§fKills: §c" + bsPlayer.getKills()).setScore(currentIndex);
        currentIndex++;
        objective.getScore(
                "§fTimer: " + TextUtils.secondsToMMSS(gameManager.getGameRunnable().getTime()))
                .setScore(currentIndex);
        currentIndex++;

        objective.getScore("§r§8§m--------------------").setScore(currentIndex);
        return scoreboard;
    }

    @Override
    public void run() {
        // copy the arraylist to order it w/o concurrency errors
        orderedPlayerList = new ArrayList<>(pManager.getPlayers());
        orderedPlayerList.sort(Comparator.comparing(BsPlayer::getLives));

        isEnded = gameManager.getGameState().equals(GameState.DONE);

        for (BsPlayer bsPlayer : orderedPlayerList) {
            Player p = bsPlayer.getBukkitPlayer();

            // reset the objective
            Objective tmp = p.getScoreboard().getObjective("BlockSumo");
            if (tmp != null)
                tmp.unregister();

            Scoreboard bScoreboard = buildScoreboard(p.getScoreboard(), bsPlayer);

            if (bsPlayer.isLoggedOn())
                p.setScoreboard(bScoreboard);

            if (isEnded)
                cancel();
        }
    }
}
