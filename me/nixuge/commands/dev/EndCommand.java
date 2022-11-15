package me.nixuge.commands.dev;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;

public class EndCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Bukkit.broadcastMessage("Calling init; DEBUG COMMAND ONLY ! CAUSES ISSUES WITH PARTICLE TIMINGS AND MAYBE OTHER, USE WITH CAUTION!");
        BlockSumo.getInstance().init();

        return true;
    }
    
}
