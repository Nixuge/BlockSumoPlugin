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
        time++;
    }
}
