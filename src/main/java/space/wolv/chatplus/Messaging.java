package space.wolv.chatplus;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messaging
{
    public static String colorful(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void send(String message)
    {
        Bukkit.getServer().getConsoleSender().sendMessage(colorful(message));
    }

    public static void send(Player player, String message)
    {
        player.sendMessage(colorful(message));
    }

    public static void send(CommandSender sender, String message)
    {
        sender.sendMessage(colorful(message));
    }

    public static void send(Player player, TextComponent message)
    {
        player.spigot().sendMessage(message);
    }

    public static void broadcast(String message)
    {
        Bukkit.broadcastMessage(colorful(message));
    }

    public static void broadcast(String message, String permission)
    {
        Bukkit.broadcast(colorful(message), permission);
    }

    public static void broadcast(TextComponent message)
    {
        Bukkit.getOnlinePlayers().forEach(player -> player.spigot().sendMessage(message));
    }

    public static void broadcast(TextComponent message, String permission)
    {
        Bukkit.getOnlinePlayers().forEach(player -> {if (player.hasPermission(permission)) player.spigot().sendMessage(message);});
    }
}
