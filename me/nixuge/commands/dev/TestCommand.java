package me.nixuge.commands.dev;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.nixuge.config.Lang;
// import me.nixuge.reflections.particleUtils.ParticleUtils1_7;
// import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // if (!(sender instanceof Player)) return false;
        Bukkit.broadcastMessage(Lang.get("debug.startcommand"));

        String name = args[0];
        Float x = Float.parseFloat(args[1]);
        Float y = Float.parseFloat(args[2]);
        Float z = Float.parseFloat(args[3]);

        Bukkit.broadcastMessage("name" + name);
        Bukkit.broadcastMessage("x" + x + "y:" + y + "z:" + z);

        Color c = Color.RED;

        // PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
        //         name,
        //         x,
        //         y,
        //         z,
        //         c.getRed(), c.getGreen(), c.getBlue(),
        //         Float.parseFloat(args[4]),
        //         Integer.parseInt(args[5]));

        // ParticleUtils1_7.sendPacketAllPlayers(packet);

        return true;
    }

}