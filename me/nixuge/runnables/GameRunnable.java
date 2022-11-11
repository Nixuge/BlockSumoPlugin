package me.nixuge.runnables;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import me.nixuge.BlockSumo;
import me.nixuge.McMap;
import me.nixuge.runnables.particle.MiddleParticleRunnable;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class GameRunnable extends BukkitRunnable {

    // TODO: refactor this class

    private int time = 0;
    private int last_bonus_spawn = 0;

    private BlockSumo plugin = BlockSumo.getInstance();
    private McMap map = plugin.getGameManager().getMcMap();
    private Location loc = map.getCenter();
    private World world = map.getWorld();
    private Random rand = new Random();

    @Override
    public void run() {
        // TODO:
        time++;
        
        MiddleParticleRunnable run = new MiddleParticleRunnable(60);
        run.runTaskTimer(plugin, 1, 1);
        cancel();

    }

    private void summonParticles() {

    }
}
