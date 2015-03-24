package io.rubegamer.duelme.duelme.commands.duel;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelManager;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCmd extends DuelCmd {

    public AcceptCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(CommandSender sender, String subCmd, String[] args) {
        if (!(sender instanceof Player)) {
            Util.sendMsg(sender, NO_CONSOLE);
            return;
        }

        if(args.length < 1){
            Util.sendMsg(sender, ChatColor.GREEN + "Usage: /duel accept <player>");
            return;
        }

        Player accepter = (Player) sender;
        String senderName = getValue(args, 0, "");

        DuelManager dm = plugin.getDuelManager();
        dm.acceptRequest(accepter , senderName);
    }
}
