package io.rubegamer.duelme.duelme.commands.duel;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelManager;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendCmd extends DuelCmd {

    public SendCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(CommandSender sender, String subCmd, String[] args) {
        if (!(sender instanceof Player)) {
            Util.sendMsg(sender, NO_CONSOLE);
            return;
        }

        if(args.length < 1){
            Util.sendMsg(sender, ChatColor.GREEN + "Usage: /duel send <player>");
            Util.sendMsg(sender, ChatColor.GREEN + "Or");
            Util.sendMsg(sender, ChatColor.GREEN + "Usage: /duel send <player> <amount>");
            return;
        }

        Player duelSender = (Player) sender;
        String duelTarget = getValue(args, 0, "");
        DuelManager dm = plugin.getDuelManager();

        if(args.length == 1) {
            dm.sendNormalDuelRequest(duelSender, duelTarget);
        } else if( args.length == 2) {
            if(!sender.hasPermission("duelme.player.sendbet")) {
                Util.sendMsg(sender, NO_PERM);
                return;
            }
            String betAmountIn = getValue(args, 1, "");
            try {
                double betAmount = Double.parseDouble(betAmountIn);
                dm.sendBetDuelRequest(duelSender, duelTarget, betAmount);
            } catch (NumberFormatException e) {
                Util.sendMsg(sender, ChatColor.RED + "The bet amount must be a number!");
                return;
            }
        }
    }
}