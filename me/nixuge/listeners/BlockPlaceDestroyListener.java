package me.nixuge.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
    //PLACE BLOCKS IN ARRAYLIST OR SMTH -> DONE
    //W TIME OF PLACING -> SHOULD BE DONE TOO
    //AND 
    //->CHANGE COLORS A BIT AFTER PLACING
    //->DELETE AFTER A WHILE IF CLOSE TO MID

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        if (!BlockSumo.getInstance().getGameManager().getGameState().equals(GameState.PLAYING)) return;
        //could be cleaner and only enable the event while game playing
        //but oh well will see later
        Player player = blockPlaceEvent.getPlayer();
        Block block = blockPlaceEvent.getBlockPlaced();

        BsPlayer bsPlayer = BlockSumo.getInstance().getGameManager().getExistingBsPlayerFromBukkit(player);
        if (bsPlayer == null) return;

        bsPlayer.addBlock(block);
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        GameManager gameMgr = BlockSumo.getInstance().getGameManager();
        if (gameMgr.getGameState() != GameState.PLAYING) return;
        
        gameMgr.getGameRunnable().removeBlock(event.getBlock().getLocation());
    }
}
