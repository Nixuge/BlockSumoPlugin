package me.nixuge.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_8_R3.World;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command comamnd, String arg2, String[] args) {

        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;
        int x = 106, y = 69, z = 62;
        int time = Integer.parseInt(args[0]);
        BlockPosition bpos = new BlockPosition(x, y, z);
        World mcworld = ((CraftWorld) p.getWorld()).getHandle();
        
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
                0, bpos, time);

        Player player = Bukkit.getPlayer("Nixuge");
        int dimension = ((CraftWorld) player.getWorld()).getHandle().dimension;
        
        ((CraftServer) player.getServer()).getHandle().sendPacketNearby(
                x, y, z, 120, dimension, packet);


        return true;
    }
}
