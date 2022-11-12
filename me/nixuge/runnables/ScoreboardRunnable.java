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
import me.nixuge.enums.PlayerState;
import me.nixuge.utils.BsPlayer;

public class ScoreboardRunnable extends BukkitRunnable {

    private GameManager gameManager = BlockSumo.getInstance().getGameMgr();
    private PlayerManager pManager = gameManager.getPlayerMgr();
    private List<BsPlayer> orderedPlayerList = new ArrayList<BsPlayer>();

    private Objective getBaseObjective(Scoreboard scoreboard) {
        Objective objective = scoreboard.registerNewObjective("BlockSumo", "main");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§b§6BlockSumo");
        objective.getScore("§8§m--------------------").setScore(1);
        return objective;
    }

    private String coloredStringFromLives(int lives) {
        if (lives >= 6) {
            return Color.GREEN.getChatColor() + lives;
        } else if (lives >= 4) {
            return Color.LIME.getChatColor() + lives;
        } else if (lives >= 2) {
            return Color.YELLOW.getChatColor() + lives;
        }
        return Color.RED.getChatColor() + lives; // lives <= 1
    }

    private int setPlayerScoreboard(Objective objective, int currentIndex) {
        for (BsPlayer player : orderedPlayerList) {
            if (player.isDead()) continue;
            String str = player.getColor().getChatColor() + player.getBukkitPlayer().getName() +
                    ": §r" + coloredStringFromLives(player.getLives());
            objective.getScore(str).setScore(currentIndex);
            currentIndex++;
        }

        return currentIndex;
    }

    private Scoreboard buildScoreboard(Scoreboard scoreboard) {
        Objective objective = getBaseObjective(scoreboard);
        int currentIndex = 2;

        // if bonus spawning, display that
        int nextSpawnTime = gameManager.getGameRunnable().getNextSpawnTime();
        if (nextSpawnTime > -3) { //-3 = 3s after the bonus spawn
            if (nextSpawnTime > 0) {
                objective.getScore("§fBonus in " + nextSpawnTime + "s").setScore(currentIndex);
            } else {
                objective.getScore("§fBonus spawned !").setScore(currentIndex);
            }
            currentIndex++;
            objective.getScore("§e§l§l§o§r").setScore(currentIndex);
            currentIndex++;
        }

        // display score for every player
        currentIndex = setPlayerScoreboard(objective, currentIndex);

        objective.getScore("§r§8§m--------------------").setScore(currentIndex);
        return scoreboard;
    }

    @Override
    public void run() {
        //copy the arraylist to order it w/o concurrency errors
        orderedPlayerList = new ArrayList<>(pManager.getPlayers());
        orderedPlayerList.sort(Comparator.comparing(BsPlayer::getLives));

        for (BsPlayer bsPlayer : orderedPlayerList) {
            Player p = bsPlayer.getBukkitPlayer();

            Objective tmp = p.getScoreboard().getObjective("BlockSumo");
            if (tmp != null)
                tmp.unregister();

            Scoreboard bScoreboard = buildScoreboard(p.getScoreboard());

            if (bsPlayer.getState() == PlayerState.LOGGED_ON)
                p.setScoreboard(bScoreboard);
        }
    }
}
