package me.nixuge;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nixuge.commands.KitCommand;
import me.nixuge.commands.dev.EndCommand;
import me.nixuge.commands.dev.StartCommand;
import me.nixuge.commands.dev.TestCommand;
import me.nixuge.commands.dev.TestCommand2;
import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.listeners.global.ItemSpawnDropListener;
import me.nixuge.listeners.global.RandomChangeListener;
import me.nixuge.runnables.LobbyRunnable;
import me.nixuge.utils.ScoreboardUtils;
import me.nixuge.utils.logger.LogLevel;
import me.nixuge.utils.logger.Logger;

public class BlockSumo extends JavaPlugin {

    // TODO: add sounds

    // TODO: proper game end (fireworks & other)
    // TODO: only select spawn if no player around

    // NOT WORKING IN 1.7:
    // Logging
    // "BlockExplodeEvent"
    // particles

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

    @SuppressWarnings("deprecation") // Bukkit.getOnlinePlayers()
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

        Logger.log(LogLevel.DEBUG, "Done calling init()");
    }

    @Override
    public void onEnable() {
        init();

        getCommand("end_blocksumo").setExecutor(new EndCommand());
        getCommand("start_blocksumo").setExecutor(new StartCommand());
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("testcommand").setExecutor(new TestCommand());
        getCommand("test2").setExecutor(new TestCommand2());

        // those remaining here because they're always needed
        pluginManager.registerEvents(new RandomChangeListener(), this);
        pluginManager.registerEvents(new ItemSpawnDropListener(), this);

        Logger.log("Done loading plugin !");
    }
}

// TODO for 1.5 (& 1.6) support:

// scoreboard: The method getScore(OfflinePlayer) in the type Objective is not
// applicable for the arguments (String)

// setunbreakable: The method spigot() is undefined for the type ItemMeta

// respawnlistener: The method respawn() is undefined for the type Player.Spigot

// oninventoryclick: The method getClickedInventory() is undefined for the type
// InventoryClickEvent
