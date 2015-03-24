package io.rubegamer.duelme.duelme.threads;

import io.rubegamer.duelme.duelme.main.DuelMe;
import io.rubegamer.duelme.duelme.util.UpdateChecker;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateCheckerThread implements Runnable {

    private DuelMe plugin;

    public UpdateCheckerThread(DuelMe plugin) {
         this.plugin = plugin;
    }

    @Override
    public void run() {
        new UpdateChecker(plugin, 60044);
    }
}