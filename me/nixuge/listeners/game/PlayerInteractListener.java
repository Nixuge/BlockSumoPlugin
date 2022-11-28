package me.nixuge.listeners.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.nixuge.BlockSumo;
import me.nixuge.GameManager;
import me.nixuge.PlayerManager;
import me.nixuge.objects.BsPlayer;
import me.nixuge.objects.bonuses.global.BounceFeather;
import me.nixuge.objects.bonuses.global.MovingFireball;
import me.nixuge.objects.bonuses.middle.BonusLife;
import me.nixuge.objects.bonuses.middle.ExplosionGun;
import me.nixuge.objects.bonuses.middle.TntRain;

public class PlayerInteractListener implements Listener {

    BlockSumo plugin;
    GameManager gameMgr;
    PlayerManager pManager;

    public PlayerInteractListener() {
        plugin = BlockSumo.getInstance();
        gameMgr = plugin.getGameMgr();
        pManager = gameMgr.getPlayerMgr();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        // Action action = event.getAction();
        Player p = event.getPlayer();
        BsPlayer bsPlayer = pManager.getBsPlayer(p);
        ItemStack item = event.getItem();
        if (item == null)
            return;
        Material material = item.getType();

        switch (material) {
            // middle items
            case NETHER_STAR:
                BonusLife.run(bsPlayer);
                break;
            case GOLD_HOE:
                ExplosionGun.run(gameMgr, bsPlayer, item);
                event.setCancelled(true); // avoid hoeing dirt/grass
                break;
            case BLAZE_POWDER:
                TntRain.run(bsPlayer, pManager.getPlayers());
                break;

            // global items
            // TNT in "BlockPlaceListener"
            case FIREBALL:
                MovingFireball.run(p);
                event.setCancelled(true); // avoid placing fire
                break;
            case FEATHER:
                BounceFeather.run(p);
                break;
            default:
                break;
        }
    }
}
