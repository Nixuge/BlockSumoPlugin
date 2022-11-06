package me.nixuge.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // TODO Auto-generated method stub
        if (!(sender instanceof Player)) return false;
        return true;
    }
    
}
