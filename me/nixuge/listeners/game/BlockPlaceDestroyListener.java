package me.nixuge.listeners.game;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.enums.Color;
import me.nixuge.enums.GameState;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.ExpiringBlock;
import me.nixuge.objects.maths.Area;
import me.nixuge.runnables.BlockManagerRunnable;
import me.nixuge.utils.bonuses.global.NormalTnt;

public class BlockPlaceDestroyListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player p = event.getPlayer();
        
        if (block.getType().equals(Material.TNT)) {
            NormalTnt.run(block);
            return;
        }

        GameManager gameMgr = BlockSumo.getInstance().getGameMgr();
        BlockManagerRunnable bdr = gameMgr.getBlockDestroyRunnable();
        Area centerArea = gameMgr.getMcMap().getCenterArea();

        BsPlayer bsPlayer = gameMgr.getPlayerMgr().getExistingBsPlayerFromBukkit(p);
        Color color = bsPlayer.getColor();

        if ( centerArea.containsBlock(block.getLocation()) ) {
            bdr.addBlock(new ExpiringBlock(bdr.getTickTime(), block.getLocation(), color, 60, 0));
        } else {
            bdr.addBlock(new ExpiringBlock(bdr.getTickTime(), block.getLocation(), color));
        }

        //re-give the wool
        if (block.getType().equals(Material.WOOL)) {
            ItemStack item = p.getItemInHand();
            item.setAmount(64);
            p.setItemInHand(item);
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        GameManager gameMgr = BlockSumo.getInstance().getGameMgr();
        if (gameMgr.getGameState() != GameState.PLAYING) return;

        if (event.getBlock().getType() != Material.WOOL) {
            event.getPlayer().sendMessage("Can't destroy that block !");
            event.setCancelled(true);
            event.getPlayer().updateInventory(); //in case used w a kb sword in hand
            return;
        }
        
        gameMgr.getBlockDestroyRunnable().removeBlock(event.getBlock().getLocation());
    }
}
