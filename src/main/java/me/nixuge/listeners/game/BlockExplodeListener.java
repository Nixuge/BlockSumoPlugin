package me.nixuge.listeners.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.config.Config;

public class BlockExplodeListener implements Listener {
    GameManager gameMgr;

    public BlockExplodeListener() {
        gameMgr = BlockSumo.getInstance().getGameMgr();
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        manageBlockExplode(event.blockList());
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        manageBlockExplode(event.blockList());
    }

    private void manageBlockExplode(List<Block> blocks) {
        for (Block block : new ArrayList<>(blocks)) { // ConcurrentModificationException
            Material type = block.getType();
            if (type.equals(Material.WOOL)) {
                gameMgr.getBlockDestroyRunnable().removeBlock(block.getLocation());
            } else if (!Config.map.isMaterialDestroyable(type)) {
                blocks.remove(block); // remove to be called if block isn't destroyable
            }
        }
    }
}
