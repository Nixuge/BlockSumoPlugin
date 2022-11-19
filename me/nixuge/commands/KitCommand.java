package me.nixuge.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nixuge.BlockSumo;
import me.nixuge.config.Lang;
import me.nixuge.enums.GameState;
import me.nixuge.utils.KitEdit;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;
        if (BlockSumo.getInstance().getGameMgr().getGameState() != GameState.WAITING) {
            p.sendMessage(Lang.get("kit.editUnavailable"));
            return true;
        }
        
        new KitEdit(p).spawnInventory();
        
        return true;
    }
}
