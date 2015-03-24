package io.rubegamer.duelme.duelme.commands.admin;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.mysql.FieldName;
import io.rubegamer.duelme.duelme.mysql.MySql;
import io.rubegamer.duelme.duelme.util.DuelArena;
import io.rubegamer.duelme.duelme.util.FileManager;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AddDeathCmd extends DuelAdminCmd {
    public AddDeathCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(DuelArena duelArena, CommandSender sender, String subCmd, String[] args) {
        MySql mySql = plugin.getMySql();
        FileManager fm = plugin.getFileManager();

        if (args.length < 1) {
            Util.sendMsg(sender, "Usage /dueladmin addDeath <playername>");
            return;
        }

        if (args.length == 1) {
            if (!fm.isMySqlEnabled()) {
                Util.sendMsg(sender, ChatColor.RED + "MySql is NOT enabled you cannot use this command.");
                return;
            }
            String playerNameIn = args[0];
            Player player = plugin.getServer().getPlayer(playerNameIn);
            UUID playerUUID = player.getUniqueId();

            if (player != null) {
                String playerName = player.getName();
                Util.sendMsg(sender, "Adding death for player: " + ChatColor.AQUA + playerName);
                mySql.addPlayerKillDeath(playerUUID, playerName, FieldName.DEATH);
            } else {
                Util.sendMsg(sender, ChatColor.RED + "Player " + playerNameIn + " is not online, did you type the name correctly?");
            }
        }
    }
}