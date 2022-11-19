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

public class BlockExplodeListener implements Listener {
    // could be good to convert normal arrays[] to Sets<>
    // private Set<Material> protectedMaterials = new HashSet<Material>(){{
    // add(Material.GOLD_BLOCK);
    // add(Material.QUARTZ_BLOCK);
    // }};
    // or not? idk will see

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
            if (block.getType().equals(Material.WOOL)) {
                gameMgr.getBlockDestroyRunnable().removeBlock(block.getLocation());
            } else {
                blocks.remove(block); // avoid destroying the block if not wool
            }
        }
    }
}
