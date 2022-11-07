package me.nixuge.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.nixuge.BlockSumo;
import me.nixuge.utils.BsPlayer;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
        // TODO Auto-generated method stub
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
            new Random().nextInt(5000), new BlockPosition(x, y, z), time);
        
        int dimension;
    
        List<BsPlayer> onlinePlayers = BlockSumo.getInstance().getGameManager().getPlayers();
        
        for (BsPlayer bsPlayer : onlinePlayers) {
            if (bsPlayer.getPlayerState().equals(PlayerState.LOGGED_OFF)) return;
            Player player = bsPlayer.getBukkitPlayer();
    
            dimension = ((CraftWorld) player.getWorld()).getHandle().dimension; 
    
            ((CraftServer) player.getServer()).getHandle().sendPacketNearby(
                x, y, z, 120, dimension, packet);
        }
        // int dimension = ((CraftWorld) p.getWorld()).getHandle().dimension;
        //TODO: remove block or change break % here
        //see ExpiringBlock.java
    }
    }

}
