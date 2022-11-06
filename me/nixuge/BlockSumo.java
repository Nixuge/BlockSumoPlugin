package me.nixuge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.nixuge.commands.JoinCommand;
import me.nixuge.commands.QuitCommand;
import me.nixuge.listeners.JoinListener;
import me.nixuge.listeners.QuitListener;
import me.nixuge.listeners.RandomChangeListener;
import me.nixuge.runnables.LobbyRunnable;

public class BlockSumo extends JavaPlugin {

    private static BlockSumo main;
    public static BlockSumo getInstance() {
        return main;
    }

    private GameManager gameManager = new GameManager();
    public GameManager getGameManager() {
        return gameManager;
    }

    private LobbyRunnable lobbyRunnable;
    //TODO
    
    @Override
    public void onEnable() {
        main = this;
        Bukkit.broadcastMessage("enabled");
        getCommand("join_blocksumo").setExecutor(new JoinCommand());
        getCommand("quit_blocksumo").setExecutor(new QuitCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new RandomChangeListener(), this);
        // start runtime here
        lobbyRunnable = new LobbyRunnable();
        lobbyRunnable.runTaskTimer(this, 20, 20);
    }
}