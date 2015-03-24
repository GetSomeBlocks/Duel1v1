package io.rubegamer.duelme.duelme.events;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelArena;
import io.rubegamer.duelme.duelme.util.DuelManager;
import io.rubegamer.duelme.duelme.util.FileManager;
import io.rubegamer.duelme.duelme.util.Util;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignEdit implements Listener {

    private DuelMe plugin;

    public SignEdit(DuelMe plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onSignEdit(SignChangeEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        String line1 = e.getLine(0);
        String line2 = e.getLine(1);
        DuelManager dm = plugin.getDuelManager();
        FileManager fm = plugin.getFileManager();

        if(line1 == null) {
            return;
        }

        if(line1.equalsIgnoreCase("[duelme]")) {
            if(!player.hasPermission("duelme.admin.signs.create")) {
                Util.sendMsg(player, ChatColor.RED + "You do not have the permission duelme.admin.signs.create!");
                return;
            }
            if(line2 == null || line2.equals("")) {
                Util.sendMsg(player, ChatColor.RED + "You must provide an arena name to create a duel status sign!.");
                block.breakNaturally();
                return;
            }

            DuelArena arena = dm.getDuelArenaByName(line2);

            if(arena == null) {
                Util.sendMsg(player, ChatColor.RED + "Duel arena " + line2 + " does not exist!");
                block.breakNaturally();
                return;
            }

            e.setLine(0, ChatColor.GREEN + "[DuelMe]");
            e.setLine(1, arena.getName());
            e.setLine(2, ChatColor.AQUA + arena.getDuelState().toString());

            fm.saveArenaSign(arena.getName(), block.getWorld().getName(), block.getX(), block.getY(), block.getZ());

            Util.sendMsg(player, ChatColor.GREEN + "Successfully created a duel arena status sign!");

        }

    }

}