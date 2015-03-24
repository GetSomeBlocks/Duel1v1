package io.rubegamer.duelme.duelme.commands.admin;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class RemoveCmd extends DuelAdminCmd {

    public RemoveCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(DuelArena duelArena, CommandSender sender, String subCmd, String[] args) {

        DuelManager dm = plugin.getDuelManager();
        FileManager fm = plugin.getFileManager();
        String duelArenaName = duelArena.getName();
        FileConfiguration duelArenas = fm.getDuelArenas();

        dm.removeDuelArena(duelArena);//remove the duel arenas from the list

        if(duelArenas.isSet("duelarenas." + duelArenaName)) {//check is the arena been saved to disk
            duelArenas.set("duelarenas." + duelArenaName, null);//remove from disk
            fm.reloadDuelArenas();
            Util.sendMsg(sender,ChatColor.GREEN + "Removed Duel Arena " +
                    ChatColor.AQUA + duelArenaName + ChatColor.GREEN + " from cache and disk.");

            return;
        }

        Util.sendMsg(sender,ChatColor.GREEN + "Removed Duel Arena " +
                ChatColor.AQUA + duelArenaName + ChatColor.GREEN + " from cache.");
    }
}
