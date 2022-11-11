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
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class GameRunnable extends BukkitRunnable {

    // TODO: refactor this class

    private int time = 0;
    private int last_bonus_spawn = 0;

    private McMap map = BlockSumo.getInstance().getGameManager().getMcMap();
    private Location loc = map.getCenter();
    private Location particleLoc = loc.clone().add(0, +5, 0);
    private World world = map.getWorld();
    private Random rand = new Random();

    @Override
    public void run() {
        // TODO:
        time++;
        summonParticles();
    }

    private void summonParticles() {
        // for (int i = 0; i < 20; i++) {
        // //proper API only on 1.9+, to rework
        // world.playEffect(loc, Effect.FLAME, 2, 1);
        // }
        float rdm = 0 + rand.nextFloat() * .47f; // perimeter of circle w .75 radius
        float radius = 0.75f;
        for (double y = rdm; y <= 3 + rdm; y += 0.025) {
            double x = radius * Math.cos(y * 10);
            double z = radius * Math.sin(y * 10);
            //PacketPlayOutWorldParticles:
            //particle, bool, x, y, z, x+-, y+-, z+-, idk, idk
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true,
                    (float) (particleLoc.getX() + x), (float) (particleLoc.getY() + y), (float) (particleLoc.getZ() + z), 0, 0, 0, 0, 0);
            for (Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            }
        }

    }
}
