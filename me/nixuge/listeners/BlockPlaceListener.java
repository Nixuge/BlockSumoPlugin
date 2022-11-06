package me.nixuge.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    //TODO:
    //PLACE BLOCKS IN ARRAYLIST OR SMTH
    //W TIME OF PLACING
    //AND 
    //->CHANGE COLORS A BIT AFTER PLACING
    //->DELETE AFTER A WHILE IF CLOSE TO MID

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        Player player = blockPlaceEvent.getPlayer();
        Block block = blockPlaceEvent.getBlockPlaced();
    }
}
