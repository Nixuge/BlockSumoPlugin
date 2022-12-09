package me.nixuge.commands.dev;

import org.bukkit.Bukkit;
// import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nixuge.config.Lang;
// import me.nixuge.reflections.particleUtils.ParticleUtils1_7;
// import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Bukkit.broadcastMessage(Lang.get("debug.startcommand"));


        return true;
    }

}