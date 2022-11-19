package me.nixuge.commands.dev;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
// import org.bukkit.entity.Player;

import me.nixuge.config.Config;
import me.nixuge.config.Lang;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command comamnd, String arg2, String[] args) {

        // if (!(sender instanceof Player)) return false;
        // Player p = (Player)sender;

        // Config.test();
        Lang.setLanguage(args[0]);

        return true;
    }
}
