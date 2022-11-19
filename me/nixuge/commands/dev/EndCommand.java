package me.nixuge.commands.dev;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
import me.nixuge.config.Lang;

public class EndCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        Bukkit.broadcastMessage(Lang.get("debug.endcommand"));
        BlockSumo.getInstance().init();

        return true;
    }
    
}
