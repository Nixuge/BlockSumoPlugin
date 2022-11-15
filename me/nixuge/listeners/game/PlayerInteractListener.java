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
import me.nixuge.utils.bonuses.global.BounceFeather;
import me.nixuge.utils.bonuses.global.MovingFireball;
import me.nixuge.utils.bonuses.middle.BonusLife;
import me.nixuge.utils.bonuses.middle.ExplosionGun;
import me.nixuge.utils.bonuses.middle.TntRain;

public class PlayerInteractListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        BlockSumo plugin = BlockSumo.getInstance();
        GameManager gameMgr = plugin.getGameMgr();
        PlayerManager pManager = gameMgr.getPlayerMgr();
        // Action action = event.getAction();
        Player p = event.getPlayer();
        BsPlayer bsPlayer = pManager.getExistingBsPlayerFromBukkit(p);
        ItemStack item = event.getItem();
        if (item == null) return;
        Material material = item.getType();
        
        switch (material) {
            //middle items
            case NETHER_STAR:
                BonusLife.run(bsPlayer);
                break;
            case GOLD_HOE:
                ExplosionGun.run(gameMgr, bsPlayer, item);
                break;
            case BLAZE_POWDER:
                TntRain.run(bsPlayer, pManager.getPlayers());
                break;

            //global items
            //TNT in "BlockPlaceListener"
            case FIREBALL:
                MovingFireball.run(p);
                break;
            case FEATHER:
                BounceFeather.run(p);
                break;
            default:
                break;
        }
    }
}
