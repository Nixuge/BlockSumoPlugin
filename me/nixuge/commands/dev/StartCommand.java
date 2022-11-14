package me.nixuge.commands.dev;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;

public class StartCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Bukkit.broadcastMessage("IMPORTANT: dev command, testing only NEEDS TO BE REMOVED IN THE FINAL BUILD.");
        
        BlockSumo plugin = BlockSumo.getInstance();
        plugin.init();
        plugin.getGameMgr().startGame(true);

        return true;
    }
    
}
