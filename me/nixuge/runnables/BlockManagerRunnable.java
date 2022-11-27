package me.nixuge.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.enums.Color;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.ExpiringBlock;
import me.nixuge.reflections.HandleUtils;
import me.nixuge.reflections.ParticleUtils;
import me.nixuge.reflections.particleUtils.ParticleEnum;

public class BlockManagerRunnable extends BukkitRunnable {
    private int tick_time = 0;
    private List<ExpiringBlock> blocks = new ArrayList<>();
    private List<ExpiringBlock> toRemove;

    private BlockSumo plugin;
    private GameManager gameMgr;
    private boolean is1_7;

    private int dimension;
    private Object serverHandle;

    public BlockManagerRunnable() {
        this.plugin = BlockSumo.getInstance();
        this.gameMgr = plugin.getGameMgr();
        this.is1_7 = plugin.is1_7();

        World world = gameMgr.getMcMap().getWorld();
        dimension = (int) HandleUtils.getHandleField(world, "dimension");
        serverHandle = HandleUtils.getHandle(Bukkit.getServer());
    }

    @Override
    public void run() {
        tick_time++;
        toRemove = new ArrayList<>();

        for (ExpiringBlock block : blocks) {
            loopBlockStates(block);
            loopBlockColors(block);
        }
        for (ExpiringBlock block : toRemove) {
            blocks.remove(block); // remove after to avoid causing issues in the loop
        }
    }

    @SuppressWarnings("deprecation")
    private void loopBlockColors(ExpiringBlock block) {
        int[] colors = block.getColorChanges();
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            if (colors[i] == tick_time) {
                Location loc = block.asLocation();
                if (i + 1 == length) { // if last element
                    // NOTE: will see if I keep it like that
                    // or just remove the lastColor altogether and do it just
                    // like bwpractice rn. For now keeping it.
                    loc.getBlock().setData(block.getLastColor().getWoolByteColor());
                } else {
                    loc.getBlock().setData(Color.getRandomColor().getWoolByteColor());
                }
            }
        }
    }

    private void loopBlockStates(ExpiringBlock block) {
        int[] states = block.getStates();
        for (int i = 0; i < states.length; i++) {
            if (states[i] == tick_time) {
                if (i < 10) {
                    sendBreakBlockPacket1_8(block.asLocation(), i, block.getBreakerId());
                } else {
                    breakBlockParticles1_8(block.asLocation());
                    sendBreakBlockPacket1_8(block.asLocation(), i, block.getBreakerId()); // reset state
                    toRemove.add(block);
                }
                break;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void breakBlockParticles1_8(Location loc) {
        Block block = loc.getBlock();
        int id = block.getTypeId(); // =Material.WOOL.getId() in normal circumstances
        byte data = block.getData();

        // NOTE: to be more safe, could get the material here and if it's air
        // just not send any particles
        // but that means if i have an issue somewhere else in my code
        // with destroyed blocks not registering properly I won't see it
        // so sticking with no checks here for now
        block.setType(Material.AIR);

        // Important info:
        // As of now, this doesn't work on 1.7 (the version using colors bukkit objects)
        // Need to find a fix on here
        Color color = null;
        if (is1_7) {
            color = Color.getFromWoolData(data);
        }

        ParticleUtils.sendParticlePacket(ParticleEnum.BLOCK_CRACK,
                (float) loc.getX() + .5f,
                (float) loc.getY(),
                (float) loc.getZ() + .5f,
                0, 0, 0, 10,
                new int[] { id | (data << 12) },
                color);

    }

    private void sendBreakBlockPacket1_8(Location loc, int stage, int breakerId) {
        // -> see https://www.spigotmc.org/threads/block-break-state.266966/
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        // int = id of player who is hitting
        // set it to a random one, everytime the same tho so that it doesn't look weird
        

        //TODO: use reflections for taht
        PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
                breakerId, new BlockPosition(x, y, z), stage);

        List<BsPlayer> gamePlayers = BlockSumo.getInstance().getGameMgr()
                .getPlayerMgr().getPlayers();

        for (BsPlayer bsPlayer : gamePlayers) {
            if (!bsPlayer.isLoggedOn())
                return;

            // TODO: use the other sendPacketNearby form
            // that starts w a human
            // Uncomment the original without reflections for intellisense
            HandleUtils.sendPacketNearby(serverHandle, x, y, z, 120, dimension, packet);

            // Original without reflections:
            // ((CraftServer) player.getServer()).getHandle().sendPacketNearby(
            // x, y, z, 120, dimension, packet);
        }
    }

    public int getTickTime() {
        return tick_time;
    }

    public void addBlock(ExpiringBlock block) {
        blocks.add(block);
    }

    public void removeBlock(Location location) {
        for (ExpiringBlock b : blocks) {
            if (location.equals(b.asLocation())) {
                sendBreakBlockPacket1_8(b.asLocation(), 10, b.getBreakerId()); // reset state
                blocks.remove(b);
                break; // should only ever be 1 at the same place so breaking is fine
            }
        }
    }
}
