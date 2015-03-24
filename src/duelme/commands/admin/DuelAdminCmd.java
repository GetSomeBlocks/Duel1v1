package io.rubegamer.duelme.duelme.commands.admin;

import io.rubegamer.duelme.duelme.commands.SubCmd;
import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelArena;
import org.bukkit.command.CommandSender;

public abstract class DuelAdminCmd extends SubCmd {

    public DuelAdminCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    public abstract void run(DuelArena duelArena,CommandSender sender, String subCmd, String[] args);
}