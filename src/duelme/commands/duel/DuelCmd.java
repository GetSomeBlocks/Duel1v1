package io.rubegamer.duelme.duelme.commands.duel;

import io.rubegamer.duelme.duelme.commands.SubCmd;
import io.rubegamer.duelme.duelme.main.DuelMe;
import org.bukkit.command.CommandSender;

public abstract class DuelCmd extends SubCmd {

    public DuelCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    public abstract void run(CommandSender sender, String subCmd, String[] args);
}