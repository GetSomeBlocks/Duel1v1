package io.rubegamer.duelme.duelme.events;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.mysql.FieldName;
import io.rubegamer.duelme.duelme.mysql.MySql;
import io.rubegamer.duelme.duelme.util.*;

public class EntityDamage implements Listener {

    private DuelMe plugin;

    public EntityDamage(DuelMe plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();

        if (!(entity instanceof Player) ) {//if the damage did not occur from a player to a player
            return;
        }

        DuelManager dm = plugin.getDuelManager();
        FileManager fm = plugin.getFileManager();
        MySql mySql = plugin.getMySql();
        Player player = (Player) entity;
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        if (dm.isInDuel(playerUUID)) {// if the player is in a duel
            if(plugin.isDebugEnabled()) {
                SendConsoleMessage.debug("Player Health: " + player.getHealth());
                SendConsoleMessage.debug("Damage to player: " + e.getDamage());
                SendConsoleMessage.debug("Health - damage: " + (player.getHealth() - e.getDamage()));
            }
            DuelArena playersArena = dm.getPlayersArenaByUUID(playerUUID);
            if (playersArena.getDuelState() == DuelState.STARTING) {//if the duel state is starting
                e.setCancelled(true); //cancel the event
                return;
            } else if (playersArena.getDuelState() == DuelState.STARTED
                    && (player.getHealth() - e.getDamage()) < 1) {

                if(plugin.isDebugEnabled()) {
                    SendConsoleMessage.debug("player killed!");
                }
                if (fm.isMySqlEnabled()) {
                    mySql.addPlayerKillDeath(playerUUID, playerName, FieldName.DEATH);
                }
                Util.broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + " was killed in a duel!");
                dm.endDuel(player);
                e.setCancelled(true);
            }
        }
    }
}