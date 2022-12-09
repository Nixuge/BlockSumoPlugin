package me.nixuge.listeners.game;

import java.util.List;

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
import me.nixuge.config.Config;
import me.nixuge.config.Lang;
import me.nixuge.enums.Color;
import me.nixuge.enums.GameState;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.ExpiringArea;
import me.nixuge.objects.ExpiringBlock;
import me.nixuge.objects.bonuses.global.NormalTnt;
import me.nixuge.runnables.BlockManagerRunnable;
import me.nixuge.utils.PlayerUtils;

public class BlockPlaceDestroyListener implements Listener {

    GameManager gameMgr;
    BlockManagerRunnable bdr;
    List<ExpiringArea> innerAreas;

    public BlockPlaceDestroyListener() {
        gameMgr = BlockSumo.getInstance().getGameMgr();
        bdr = gameMgr.getBlockDestroyRunnable();
        innerAreas = gameMgr.getMcMap().getInnerAreas();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player p = event.getPlayer();

        if (PlayerUtils.isHidden(p)) {
            event.setCancelled(true);
            return;
        }

        if (block.getType().equals(Material.TNT)) {
            NormalTnt.run(block);
            return;
        }

        BsPlayer bsPlayer = gameMgr.getPlayerMgr().getBsPlayer(p);
        Color color = bsPlayer.getColor();

        // check for inner areas
        boolean isInArea = false;
        for (ExpiringArea area : innerAreas) {
            if (area.getArea().containsBlock(block.getLocation())) {
                isInArea = true;
                int breakTime = area.getBreakTime();
                int breakStartTime = area.getBreakStartTime();
                bdr.addBlock(new ExpiringBlock(bdr.getTickTime(), block.getLocation(),
                        color, breakTime, breakStartTime));

                break; // avoid adding to multiple areas
            }
        }
        if (!isInArea) {
            bdr.addBlock(new ExpiringBlock(bdr.getTickTime(), block.getLocation(), color));
        }

        // re-give the wool
        if (block.getType().equals(Material.WOOL)) {
            ItemStack item = p.getItemInHand();
            item.setAmount(64);
            p.setItemInHand(item);
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        if (PlayerUtils.isHidden(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        if (gameMgr.getGameState() != GameState.PLAYING)
            return;

        if (!Config.map.isMaterialDestroyable(event.getBlock().getType())) {
            event.getPlayer().sendMessage(Lang.get("general.cantdestroyblock"));
            event.setCancelled(true);
            event.getPlayer().updateInventory(); // in case used w a kb sword in hand
            return;
        }

        gameMgr.getBlockDestroyRunnable().removeBlock(event.getBlock().getLocation());
    }
}
