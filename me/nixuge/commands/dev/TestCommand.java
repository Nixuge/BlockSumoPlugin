package me.nixuge.commands.dev;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import org.bukkit.Effect;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command comamnd, String arg2, String[] args) {

        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;
        // int x = 106, y = 69, z = 62;
        // int time = Integer.parseInt(args[0]);
        for (int i = 0; i < 50; i++) {
            p.getWorld().playEffect(p.getLocation(), Effect.TILE_BREAK, 152);
        }
        

        return true;
    }
}
