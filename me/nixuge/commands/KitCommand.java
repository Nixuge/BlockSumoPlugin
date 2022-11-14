package me.nixuge.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;
        
        Inventory inv = Bukkit.createInventory(p, 0, "Please organize your inventory"); 
        p.openInventory(inv);

        p.getInventory();
        
        return true;
    }
    
}
