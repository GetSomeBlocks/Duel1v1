package io.rubegamer.duelme.duelme.commands.admin;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelArena;
import io.rubegamer.duelme.duelme.util.DuelManager;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ListCmd extends DuelAdminCmd {

    public ListCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(DuelArena duelArena, CommandSender sender, String subCmd, String[] args) {
        DuelManager dm = plugin.getDuelManager();

        Util.sendEmptyMsg(sender, Util.LINE_BREAK);
        Util.sendEmptyMsg(sender, ChatColor.GOLD + "                        DuelMe - List Duel Arenas");
        Util.sendEmptyMsg(sender, "");
        int amount = 1;
        if(dm.getDuelArenas().size() > 0) {
            for(DuelArena da: dm.getDuelArenas()){
                Util.sendEmptyMsg(sender, ChatColor.GREEN + "Name: " + ChatColor.AQUA + da.getName());
                Util.sendEmptyMsg(sender, ChatColor.GREEN + "Status: " + ChatColor.AQUA + da.getDuelState());
                Util.sendEmptyMsg(sender, ChatColor.GREEN + "Has Bet: " + ChatColor.AQUA + da.hasBet());
                if(da.hasBet()) {
                    Util.sendEmptyMsg(sender, ChatColor.GREEN + "Bet Amount: " + ChatColor.AQUA + da.getBetAmount());
                }
                Util.sendEmptyMsg(sender, ChatColor.GREEN + "Pos1:" + ChatColor.AQUA + da.getPos1());
                Util.sendEmptyMsg(sender, ChatColor.GREEN + "Pos2:" + ChatColor.AQUA + da.getPos2());

                if(da.getSpawnpoint1() != null) {
                    Util.sendEmptyMsg(sender, ChatColor.GREEN + "Spawnpoint1:" + ChatColor.AQUA + da.getSpawnpoint1());
                } else {
                    Util.sendEmptyMsg(sender, ChatColor.GREEN + "Spawnpoint1:" + ChatColor.AQUA + "Not Set");
                }

                if(da.getSpawnpoint2() != null) {
                    Util.sendEmptyMsg(sender, ChatColor.GREEN + "Spawnpoint2:" + ChatColor.AQUA + da.getSpawnpoint2());
                } else {
                    Util.sendEmptyMsg(sender, ChatColor.GREEN + "Spawnpoint2:" + ChatColor.AQUA + "Not Set");
                }


                Util.sendEmptyMsg(sender, "");
            }
        } else {
            Util.sendEmptyMsg(sender, NO_DUEL_ARENAS);
        }

        Util.sendEmptyMsg(sender, "");
        Util.sendCredits(sender);
        Util.sendEmptyMsg(sender, Util.LINE_BREAK);
    }
}