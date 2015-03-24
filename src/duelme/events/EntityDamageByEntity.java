package io.rubegamer.duelme.duelme.events;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.mysql.FieldName;
import io.rubegamer.duelme.duelme.mysql.MySql;
import io.rubegamer.duelme.duelme.util.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class EntityDamageByEntity implements Listener {

    private DuelMe plugin;

    public EntityDamageByEntity(DuelMe plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();

        if (!(entity instanceof Player) || !( e.getDamager() instanceof Player)) {//if the damage did not occur from a player to a player
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        DuelManager dm = plugin.getDuelManager();
        FileManager fm = plugin.getFileManager();
        MySql mySql = plugin.getMySql();
        Player player = (Player) entity;
        String playerName = player.getName();
        UUID playerUUID = player.getUniqueId();

        if (dm.isInDuel(playerUUID)) {// if the player is in a duel
            DuelArena playersArena = dm.getPlayersArenaByUUID(playerUUID);

            if(plugin.isDebugEnabled()) {
                SendConsoleMessage.debug("Player Health: " + player.getHealth());
                SendConsoleMessage.debug("Damage to player: " + e.getDamage());
                SendConsoleMessage.debug("Health - damage: " + (player.getHealth() - e.getDamage()));
            }

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

                Player killer = (Player) e.getDamager();
                String killerName = killer.getName();
                UUID killerUUID = killer.getUniqueId();
                if (fm.isMySqlEnabled()) {
                    mySql.addPlayerKillDeath(killerUUID, killerName, FieldName.KILL);
                }

                if(fm.isDeathMessagesEnabled()) {
                    Util.broadcastMessage(ChatColor.AQUA + player.getName() + ChatColor.RED + " was killed in a duel by "
                            + ChatColor.AQUA + killerName);
                    Util.sendMsg(player, ChatColor.translateAlternateColorCodes('&', "&eYou were defeated by &c" + killerName + " &ewith &c" + killer.getHealth() + "♥"));
                }
                dm.endDuel(player);
                e.setCancelled(true);
            }
        }
    }
}
