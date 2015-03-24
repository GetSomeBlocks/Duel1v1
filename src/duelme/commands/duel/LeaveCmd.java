package io.rubegamer.duelme.duelme.commands.duel;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LeaveCmd extends DuelCmd {
    public LeaveCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(CommandSender sender, String subCmd, String[] args) {
        if(!(sender instanceof Player)){
            Util.sendMsg(sender, NO_CONSOLE);
            return;
        }

        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        DuelManager dm = plugin.getDuelManager();

        if(dm.isInDuel(playerUUID)){
            dm.endDuel(player);
        } else {
            Util.sendMsg(sender, ChatColor.RED + "You cannot leave duel if you are not in one!");
        }
    }
}