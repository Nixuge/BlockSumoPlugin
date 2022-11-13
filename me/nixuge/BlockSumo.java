package me.nixuge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nixuge.commands.JoinCommand;
import me.nixuge.commands.QuitCommand;
import me.nixuge.commands.StartCommand;
import me.nixuge.commands.TestCommand;
import me.nixuge.listeners.RandomChangeListener;
import me.nixuge.runnables.LobbyRunnable;
import me.nixuge.utils.specific.ScoreboardUtils;

public class BlockSumo extends JavaPlugin {

    // TODO: add sounds
    // TODO: target system
    // if a player is a lot on top, add a bounty
    // that make the players that kills him gain a life

    private static BlockSumo main;

    public static BlockSumo getInstance() {
        return main;
    }

    private GameManager gameManager;

    public GameManager getGameMgr() {
        return gameManager;
    }

    private PluginManager pluginManager;

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    private LobbyRunnable lobbyRunnable;

    public void init() {
        //set vars (order is important)
        main = this;
        pluginManager = getServer().getPluginManager();
        gameManager = new GameManager();

        // start runtime here
        lobbyRunnable = new LobbyRunnable(2, 8);
        lobbyRunnable.runTaskTimer(this, 20, 20);

        // add players instead of kicking
        for (Player player : Bukkit.getOnlinePlayers()) {
            gameManager.getPlayerMgr().addPlayer(player);
        }

        // reset scoreboard
        ScoreboardUtils.resetScoreboards();
    }

    @Override
    public void onEnable() {
        init();

        Bukkit.broadcastMessage("enabled");
        getCommand("join_blocksumo").setExecutor(new JoinCommand());
        getCommand("quit_blocksumo").setExecutor(new QuitCommand());
        getCommand("start_blocksumo").setExecutor(new StartCommand());
        getCommand("testcommand").setExecutor(new TestCommand());

        // this one staying here since it's always on
        pluginManager.registerEvents(new RandomChangeListener(), this);
    }
}