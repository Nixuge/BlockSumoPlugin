package me.nixuge.commands.dev;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

// import org.bukkit.craftbukkit.v1_12_R1.CraftServer;

import me.nixuge.BlockSumo;
import me.nixuge.objects.BsPlayer;
import me.nixuge.reflections.HandleUtils;
import me.nixuge.reflections.packet.HandleBlockBreakAnimation;
// import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import me.nixuge.reflections.send.HandleSendPacketNearby;

public class TestCommand2 implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //TODO: use reflections for taht
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        
        Object packet = new HandleBlockBreakAnimation(12, x, y, z, Integer.parseInt(args[3])).getPacket();

        List<BsPlayer> gamePlayers = BlockSumo.getInstance().getGameMgr()
                .getPlayerMgr().getPlayers();

        for (BsPlayer bsPlayer : gamePlayers) {
            if (!bsPlayer.isLoggedOn())
                return true;

            // that starts w a human
            // Uncomment the original without reflections for intellisense
            Object serverHandle = HandleUtils.getHandle(Bukkit.getServer());
            HandleSendPacketNearby.send(serverHandle, bsPlayer.getBukkitPlayer(), x, y, z, 120, 0, packet);

            // Original without reflections:
            // ((CraftServer) player.getServer()).getHandle().sendPacketNearby(
            // x, y, z, 120, dimension, packet);
        }
        return true;
    }
    
}
