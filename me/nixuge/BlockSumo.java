package me.nixuge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nixuge.commands.JoinCommand;
import me.nixuge.commands.QuitCommand;
import me.nixuge.commands.StartCommand;
import me.nixuge.commands.TestCommand;
import me.nixuge.listeners.BlockPlaceListener;
import me.nixuge.listeners.InventoryChangeListener;
import me.nixuge.listeners.JoinListener;
import me.nixuge.listeners.QuitListener;
import me.nixuge.listeners.RandomChangeListener;
import me.nixuge.runnables.LobbyRunnable;

public class BlockSumo extends JavaPlugin {

    private static BlockSumo main;
    public static BlockSumo getInstance() {
        return main;
    }

    private GameManager gameManager;
    public GameManager getGameManager() {
        return gameManager;
    }

    private LobbyRunnable lobbyRunnable;
    //TODO
    
    @Override
    public void onEnable() {
        main = this;
        gameManager = new GameManager();

        Bukkit.broadcastMessage("enabled");
        getCommand("join_blocksumo").setExecutor(new JoinCommand());
        getCommand("quit_blocksumo").setExecutor(new QuitCommand());
        getCommand("start_blocksumo").setExecutor(new StartCommand());
        getCommand("testcommand").setExecutor(new TestCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new RandomChangeListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new InventoryChangeListener(), this);
        // start runtime here
        lobbyRunnable = new LobbyRunnable();
        lobbyRunnable.runTaskTimer(this, 20, 20);
    }
}