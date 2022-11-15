package me.nixuge.commands.dev;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nixuge.objects.Kit;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command comamnd, String arg2, String[] args) {

        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;

        Kit playerKit = Kit.loadKit(p.getName());
        if (playerKit == null) {
            p.sendMessage("§cYou don't have a kit saved or it failed to load !");
            return true;
        }
        playerKit.useKit(p);

        return true;
    }
}
