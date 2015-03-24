package io.rubegamer.duelme.duelme.commands.admin;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelArena;
import io.rubegamer.duelme.duelme.util.DuelManager;
import io.rubegamer.duelme.duelme.util.FileManager;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCmd extends DuelAdminCmd {
    public ReloadCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(DuelArena duelArena, CommandSender sender, String subCmd, String[] args) {
        DuelManager dm = plugin.getDuelManager();
        FileManager fm = plugin.getFileManager();
        dm.getDuelArenas().clear();
        fm.reloadDuelArenas();
        Util.sendMsg(sender, ChatColor.YELLOW + "Reloaded duel arenas.");
        plugin.reloadConfig();
        Util.sendMsg(sender, ChatColor.YELLOW + "Reloaded config.yml!");
        Util.sendMsg(sender, ChatColor.YELLOW + "Saving Duel arenas!");
        fm.saveDuelArenas();
        Util.sendMsg(sender, ChatColor.YELLOW + "Loading Duel arenas!");
        fm.loadDuelArenas();
        Util.sendMsg(sender, ChatColor.GREEN + "Complete!");
    }
}