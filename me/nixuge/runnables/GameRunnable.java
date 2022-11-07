package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;
import me.nixuge.BlockSumo;
import me.nixuge.enums.PlayerState;
import me.nixuge.utils.BsPlayer;
import me.nixuge.utils.ExpiringBlock;

public class GameRunnable extends BukkitRunnable {

    private int time = 0;
    private int last_bonus_spawn = 0;
    private List<ExpiringBlock> blocks = new ArrayList<>();

    @Override
    public void run() {
        // TODO:
        changeExpiringBlocks(time);
        time++;
        // if (time > 10) {
        // time = 0;
        // }
    }

    private void changeExpiringBlocks(int time) {
        for (ExpiringBlock block : blocks) {
            int[] states = block.getStates();
            for (int i = 0; i < states.length; i++) {
                if (states[i] == time) {
                    Bukkit.broadcastMessage("changed blockstate, set to: " + i);
                    sendBreakBlockPacket(block.asLocation(), i);
                    break;
                }
            }
        }
    }

    private void breakBlockParticles(Location loc) {
        //todo
    }

    private void sendBreakBlockPacket(Location loc, int stage) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        // int = id of player who is hitting
        // set it to a random one, everytime the same tho so that it doesn't look weird
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
                16127, new BlockPosition(x, y, z), stage);

        int dimension;

        List<BsPlayer> onlinePlayers = BlockSumo.getInstance().getGameManager().getPlayers();

        for (BsPlayer bsPlayer : onlinePlayers) {
            if (bsPlayer.getPlayerState().equals(PlayerState.LOGGED_OFF))
                return;
            Player player = bsPlayer.getBukkitPlayer();

            dimension = ((CraftWorld) player.getWorld()).getHandle().dimension;

            ((CraftServer) player.getServer()).getHandle().sendPacketNearby(
                    x, y, z, 120, dimension, packet);
        }
        // int dimension = ((CraftWorld) p.getWorld()).getHandle().dimension;
        // TODO: remove block or change break % here
        // see ExpiringBlock.java
    }

    private void spawnBonus() {
        // make it so that
        // this function is harder to call right after a bonus
        // eg, 0.1% chance 5s after a bonus, 10% chance 23s after a bonus
    }

    public int getTime() {
        return time;
    }

    public void addBlock(ExpiringBlock block) {
        blocks.add(block);
    }

    public void removeBlock(ExpiringBlock block) {
        blocks.remove(block);
    }

    public void removeBlock(Location location) {
        blocks.forEach((b) -> {
            if (location.equals(b.asLocation()))
                blocks.remove(b);
        });
    }
}
