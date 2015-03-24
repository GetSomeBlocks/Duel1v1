package io.rubegamer.duelme.duelme.commands.admin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.DuelArena;
import io.rubegamer.duelme.duelme.util.DuelManager;
import io.rubegamer.duelme.duelme.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by frank on 20/12/13.
 */
public class CreateCmd extends DuelAdminCmd {

    private double pos1x, pos1y, pos1z, pos2x, pos2y, pos2z;
    private String worldName;
    private World selWorld;
    private Location pos1;
    private Location pos2;

    public CreateCmd(DuelMe plugin, String mainPerm) {
        super(plugin, mainPerm);
    }

    @Override
    public void run(DuelArena duelArena, CommandSender sender, String subCmd, String[] args) {
        if (!(sender instanceof Player)) {
            Util.sendMsg(sender, NO_CONSOLE);
            return;
        }

        if (args.length < 1) {
            Util.sendMsg(sender, ChatColor.GREEN + "Usage: /dueladmin create <arenaname>");
            return;
        }

        Player p = (Player) sender;

        Location pos1 = null;
        Location pos2 = null;

        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Selection selection = worldEdit.getSelection(p);

        if (selection != null) {
            World world = selection.getWorld();
            pos1 = selection.getMinimumPoint();
            pos2 = selection.getMaximumPoint();
        } else {
            Util.sendMsg(p, ChatColor.RED + "You have not selected a region, please select one first!");
            return;
        }
        String arenaName = getValue(args, 0, "Arena");

        DuelManager dm = plugin.getDuelManager();

        for (DuelArena da : dm.getDuelArenas()) {
            if (da.getName().equalsIgnoreCase(arenaName)) {
                Util.sendMsg(sender, ChatColor.RED + "There is already a duel arena with the name " + arenaName + ".");
                return;
            }
        }

        DuelArena newArena = new DuelArena(arenaName, pos1, pos2);

        dm.addDuelArena(newArena);

        Util.sendMsg(sender, ChatColor.GREEN + "Created a new Duel arena called " + ChatColor.GOLD + arenaName + ".");

    }
}