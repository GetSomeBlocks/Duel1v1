package io.rubegamer.duelme.duelme.util;

import io.rubegamer.duelme.duelme.main.DuelMe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class SendConsoleMessage {

    private static final String prefix = ChatColor.GREEN + "[DuelMe] ";
    private static final String info = "[Info] ";
    private static final String severe = ChatColor.YELLOW + "[Severe] ";
    private static final String warning = ChatColor.RED + "[Warning] ";
    private static final String debug = ChatColor.AQUA + "[Debug] ";

    public SendConsoleMessage() {

    }

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + info + message);
    }

    public static void severe(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + severe + message);
    }

    public static void warning(String message) {
        Bukkit.getConsoleSender().sendMessage(prefix + warning + message);
    }

    public static void debug(String message){
        Bukkit.getConsoleSender().sendMessage(prefix + debug + message);
    }
}