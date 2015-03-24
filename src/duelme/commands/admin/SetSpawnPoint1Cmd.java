package io.rubegamer.duelme.duelme.commands.admin;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelArena;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnPoint1Cmd extends DuelAdminCmd {

    public SetSpawnPoint1Cmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(DuelArena duelArena, CommandSender sender, String subCmd, String[] args) {
        if(!(sender instanceof Player)){
            Util.sendMsg(sender, NO_CONSOLE);
            return;
        }

        String duelArenaName = duelArena.getName();
        Player player = (Player) sender;

        Location pos1 = duelArena.getPos1();
        Location pos2 = duelArena.getPos2();
        Location playerLocation = player.getLocation();

        double x = playerLocation.getBlockX();
        double y = playerLocation.getBlockY();
        double z = playerLocation.getBlockZ();

        if(!Util.isInRegion(playerLocation, pos1, pos2)) {
            Util.sendMsg(sender, ChatColor.translateAlternateColorCodes('&',
                    "&cYou must be inside the region for arena: &b" + duelArenaName + " &cto set a spawnpoint!"));
            return;
        }

        playerLocation.setY(y + 2.0);
        duelArena.setSpawnpoint1(playerLocation);//offset so player does not spawn in the ground if the chunks are not loaded.
        Util.sendMsg(sender, ChatColor.translateAlternateColorCodes('&',
                "&aSpawnpoint1 set to: " + "&a(&b" + x + "&a)(&b" + y + "&a)(&b" + z + "&a)"
                        + ChatColor.GREEN + " for arena "  + ChatColor.AQUA + duelArenaName));
    }
}
