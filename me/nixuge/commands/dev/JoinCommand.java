package me.nixuge.commands.dev;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class JoinCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;

        // BlockSumo main = BlockSumo.getBlockSumo();

        // main.getGameManager().addPlayer((Player)sender);

        return true;
    }
    
}
