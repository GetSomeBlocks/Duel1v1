package io.rubegamer.duelme.duelme.commands.admin;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.mysql.FieldName;
import io.rubegamer.duelme.duelme.mysql.MySql;
import io.rubegamer.duelme.duelme.util.DuelArena;
import io.rubegamer.duelme.duelme.util.FileManager;
import io.rubegamer.duelme.duelme.util.SendConsoleMessage;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AddKillCmd extends DuelAdminCmd {
    public AddKillCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(DuelArena duelArena, CommandSender sender, String subCmd, String[] args) {
        MySql mySql = plugin.getMySql();
        FileManager fm = plugin.getFileManager();

        if(args.length < 1 ) {
            Util.sendMsg(sender, "Usage /dueladmin addkill <playername>");
            return;
        }

        if(args.length == 1 ) {
            if(!fm.isMySqlEnabled()) {
                Util.sendMsg(sender, ChatColor.RED + "MySql is NOT enabled you cannot use this command.");
                return;
            }
            String playerNameIn = args[0];
            SendConsoleMessage.debug(playerNameIn);
            Player player = plugin.getServer().getPlayerExact(playerNameIn);
            UUID playerUUID = player.getUniqueId();

            if(player != null) {
                String playerName = player.getName();
                Util.sendMsg(sender, "Adding kill for player: " + ChatColor.AQUA + playerName);
                mySql.addPlayerKillDeath(playerUUID, playerName, FieldName.KILL);
            } else {
                Util.sendMsg(sender, ChatColor.RED + "Player " + playerNameIn + " is not online, did you type the name correctly?");
            }
        }
    }
}