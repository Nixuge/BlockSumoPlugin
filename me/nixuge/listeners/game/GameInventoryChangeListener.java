package me.nixuge.listeners.game;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryEvent;

public class GameInventoryChangeListener implements Listener {
    //TODO:
    //prevent inventory changes
    //IF POSSIBLE custom kits while in the lobby
    //TEMPORARILY: with command /setslot <item> <slot_id>
    //only 2 items (64 wool & shears), apart from that can move freely

    public void onInventoryChange(InventoryEvent inventoryEvent) {
        //TODO: will do later
        //unneccessary and hard to do.
        //Apart from refilling wool, this won't do much at first
    }
}
