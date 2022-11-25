package me.nixuge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nixuge.commands.KitCommand;
import me.nixuge.commands.dev.EndCommand;
import me.nixuge.commands.dev.JoinCommand;
import me.nixuge.commands.dev.StartCommand;
import me.nixuge.commands.dev.TestCommand;
import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.listeners.ItemSpawnDropListener;
import me.nixuge.listeners.RandomChangeListener;
import me.nixuge.runnables.LobbyRunnable;
import me.nixuge.utils.ScoreboardUtils;

public class BlockSumo extends JavaPlugin {

    // TODO: add in config:
    // blocks to be destroyed & to not be destroyed

    // TODO: add sounds
    // TODO: target system (w config)
    // if a player is a lot on top, add a bounty
    // that make the players that kills him gain a life

    // TODO: proper game end (fireworks & other)
    // TODO: add javadocs (uh oh)
    // TODO: proper logging
    // TODO: only select spawn if no player around

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
        // set vars & config (order is important)
        main = this;

        saveDefaultConfig();

        Config.init(getConfig());

        Lang.setLanguage(Config.general.getLanguage());

        pluginManager = getServer().getPluginManager();
        gameManager = new GameManager();

        // start runtime here
        lobbyRunnable = new LobbyRunnable();
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

        Bukkit.broadcastMessage(Lang.get("general.pluginenabled"));

        getCommand("join_blocksumo").setExecutor(new JoinCommand());
        getCommand("end_blocksumo").setExecutor(new EndCommand());
        getCommand("start_blocksumo").setExecutor(new StartCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("testcommand").setExecutor(new TestCommand());

        // those remaining here because they're always needed
        pluginManager.registerEvents(new RandomChangeListener(), this);
        pluginManager.registerEvents(new ItemSpawnDropListener(), this);
    }
}