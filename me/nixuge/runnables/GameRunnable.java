package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
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

    // TODO: refactor this class

    private int time = 0;
    private int last_bonus_spawn = 0;
    private List<ExpiringBlock> blocks = new ArrayList<>();

    @Override
    public void run() {
        // TODO:
        changeExpiringBlocks(time);
        time++;
    }

    private void changeExpiringBlocks(int time) {
        List<ExpiringBlock> toRemove = new ArrayList<>();

        for (ExpiringBlock block : blocks) {

            int[] states = block.getStates();
            for (int i = 0; i < states.length; i++) {
                if (states[i] == time) {
                    if (i < 10) {
                        sendBreakBlockPacket(block.asLocation(), i, block.getBreakerId());
                    } else {
                        breakBlockParticles(block.asLocation());
                        sendBreakBlockPacket(block.asLocation(), i, block.getBreakerId()); //reset state
                        toRemove.add(block);
                    }
                    break;
                }
            }
        }
        for (ExpiringBlock block : toRemove) {
            blocks.remove(block); //remove after to avoid causing issues in the loop
        }
    }

    private void breakBlockParticles(Location loc) {
        World world = loc.getWorld();
        Block block = loc.getBlock();
        int typeId = block.getTypeId();

        block.setType(Material.AIR);

        for (int i = 0; i < 50; i++) {
            //proper API only on 1.9+, to rework
            world.playEffect(loc, Effect.TILE_BREAK, typeId, 500);
        }
    }

    private void sendBreakBlockPacket(Location loc, int stage, int breakerId) {
        //-> see https://www.spigotmc.org/threads/block-break-state.266966/
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        // int = id of player who is hitting
        // set it to a random one, everytime the same tho so that it doesn't look weird
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
                breakerId, new BlockPosition(x, y, z), stage);

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

    public void removeBlock(Location location) {
        for (ExpiringBlock b : blocks) {
            if (location.equals(b.asLocation())) {
                sendBreakBlockPacket(b.asLocation(), 10, b.getBreakerId()); //reset state
                blocks.remove(b);
                break; // should only ever be 1 at the same place so breaking is fine
            }
        }
    }
}
