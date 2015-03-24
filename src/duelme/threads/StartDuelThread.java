package io.rubegamer.duelme.duelme.threads;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class StartDuelThread extends BukkitRunnable {

    private DuelMe plugin;
    private Player sender;
    private Player target;
    private DuelArena duelArena;
    private int countDown;

    public StartDuelThread(DuelMe plugin, Player sender, Player target, DuelArena duelArena) {
        this.plugin = plugin;
        this.sender = sender;
        this.target = target;
        this.countDown = plugin.getFileManager().getDuelCountdownTime();
        this.duelArena = duelArena;
    }

    @Override
    public void run() {
        DuelManager dm = plugin.getDuelManager();
        FileManager fm = plugin.getFileManager();
        int duelTime = fm.getDuelTime();
        String senderName = sender.getName();
        String targetName = target.getName();
        UUID senderUUID = sender.getUniqueId();
        UUID targetUUID = target.getUniqueId();
        int duelSize = duelArena.getPlayers().size();

        if (plugin.isDebugEnabled()) {
            SendConsoleMessage.debug("Duel size: " + duelSize);
        }

        if (duelSize == 0) {
            dm.endDuel(duelArena);
            this.cancel();
        }


        if (this.countDown > 0 && duelSize == 2) {
            Util.setTime(sender, target, this.countDown);
            this.countDown--;
        } else {
            if(duelSize == 2) {
                Util.setTime(sender, target, this.countDown);
                Util.sendMsg(sender, target, ChatColor.YELLOW + "Duel!");
                duelArena.setDuelState(DuelState.STARTED);
            }

            dm.removeFrozenPlayer(senderUUID);
            dm.removeFrozenPlayer(targetUUID);
            dm.updateDuelStatusSign(duelArena);

            if (plugin.isDebugEnabled()) {
                SendConsoleMessage.debug("Stopping duel start thread.");
            }
            this.cancel();

            if (duelTime != 0 && duelSize == 2) {
                if (plugin.isDebugEnabled()) {
                    SendConsoleMessage.debug("Duel time limit is set, starting countdown task.");
                }
                new DuelTimeThread(plugin, sender, target, duelArena, duelTime).runTaskTimer(plugin, 20L, 20L);
            }
        }
    }
}