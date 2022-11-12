package me.nixuge.listeners.game;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.enums.GameState;
import me.nixuge.utils.BsPlayer;

public class BlockPlaceDestroyListener implements Listener {
    //TODO:
    //->CHANGE COLORS A BIT AFTER PLACING
    //->DELETE AFTER A SHORTER WHILE IF CLOSE TO MID

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        if (block.getType().equals(Material.TNT)) {
            block.setType(Material.AIR);
            TNTPrimed tnt = (TNTPrimed) block.getWorld().spawnEntity(block.getLocation().add(.5, 0, .5) , EntityType.PRIMED_TNT);
            tnt.setYield(3f); //reduce tnt size
            tnt.setFuseTicks(60);
            return;
        }


        BsPlayer bsPlayer = BlockSumo.getInstance().getGameMgr().getPlayerMgr().getExistingBsPlayerFromBukkit(player);
        if (bsPlayer == null) return;

        bsPlayer.addBlock(block);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        GameManager gameMgr = BlockSumo.getInstance().getGameMgr();
        if (gameMgr.getGameState() != GameState.PLAYING) return;
        
        gameMgr.getBlockDestroyRunnable().removeBlock(event.getBlock().getLocation());
    }
}
