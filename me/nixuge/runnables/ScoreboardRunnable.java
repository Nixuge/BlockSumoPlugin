package me.nixuge.runnables;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.enums.Color;
import me.nixuge.enums.PlayerState;
import me.nixuge.utils.BsPlayer;

public class ScoreboardRunnable extends BukkitRunnable {

    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private GameManager gameManager = BlockSumo.getInstance().getGameMgr();
    private PlayerManager pManager = gameManager.getPlayerMgr();
    
    public void init() {
        for (BsPlayer p : pManager.getPlayers()) {
            p.setPlayerScoreboard(manager.getNewScoreboard());
            Scoreboard playScoreboard = p.getScoreboard();
        }
    }

    private Objective getBaseObjective(Scoreboard scoreboard) {
        Objective objective = scoreboard.registerNewObjective("BlockSumo", "main");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§b§6BlockSumo");
        objective.getScore("§8§m--------------------").setScore(1);
        return objective;
    }


    private String coloredStringFromLives(int lives) {
        if (lives >= 5) {
            return Color.GREEN.getChatColor() + lives;
        } else if (lives >= 3) {
            return Color.LIME.getChatColor() + lives;
        } else if (lives == 2) {
            return Color.YELLOW.getChatColor() + lives;
        }
        return Color.RED.getChatColor() + lives; //lives <= 1
    }

    private Scoreboard buildBaseScoreboard(Scoreboard scoreboard) {
        Objective objective = getBaseObjective(scoreboard);
        
        int currentIndex = 2;
        int nextSpawnTime = gameManager.getGameRunnable().getNextSpawnTime();
        if (nextSpawnTime > 0) {
            objective.getScore("§fBonus in: " + nextSpawnTime + "s").setScore(currentIndex);
            currentIndex++;
            objective.getScore("§e§l§l§o§r").setScore(currentIndex);
            currentIndex++;
        }

        objective.getScore("§r§8§m--------------------").setScore(currentIndex);
        return scoreboard;
    }

    @Override
    public void run() {
        for (BsPlayer p : pManager.getPlayers()) {
            // Bukkit.broadcastMessage("Setting scoreboard!!" + p.getBukkitPlayer().getName()
            //  + p.getScoreboard().getObjective("BlockSumo").getScore("§8§m--------------------").getScore());
            Objective tmp = p.getScoreboard().getObjective("BlockSumo");
            if (tmp != null) tmp.unregister();
            

            Scoreboard bScoreboard = buildBaseScoreboard(p.getScoreboard());
            p.setPlayerScoreboard(bScoreboard);
            
            if (p.getState() == PlayerState.LOGGED_ON) {
                p.getBukkitPlayer().setScoreboard(bScoreboard);
            }
        }
    }
}
