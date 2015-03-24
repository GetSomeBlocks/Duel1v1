package io.rubegamer.duelme.duelme.commands.duel;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AboutCmd extends DuelCmd {

    public AboutCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }


    @Override
    public void run(CommandSender sender, String subCmd, String[] args) {
        Util.sendMsg(sender, ChatColor.GREEN + "DuelMe: "+ ChatColor.AQUA + "V" + plugin.getDescription().getVersion());
        Util.sendMsg(sender, ChatColor.GREEN + "Original Author:" +ChatColor.AQUA +" teozfrank."); // please do not remove or modify this. I put countless hours into this project all free of charge, return the favour by leaving me credit.
        Util.sendMsg(sender, ChatColor.GREEN + "Licensed under the" + ChatColor.AQUA + " MIT License.");
    }
}