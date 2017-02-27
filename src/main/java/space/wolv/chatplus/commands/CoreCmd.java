package space.wolv.chatplus.commands;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.wolv.chatplus.Jsonify;
import space.wolv.chatplus.Messaging;
import space.wolv.chatplus.ChatPlus;

public class CoreCmd implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length >= 1)
        {
            switch(args[0])
            {
                case "reload":
                    if (sender.hasPermission("chatplus.reload") || sender.isOp())
                    {
                        ChatPlus.reloadData();
                        Messaging.send(sender, "&eMessenger &6&l>> &f&oConfig reloaded");
                    }
                    else
                        Messaging.send(sender, command.getPermissionMessage());
                    break;

                case "version":
                    if (sender.hasPermission("chatplus.version") || sender.isOp())
                    {
                        String ver = ChatPlus.getInstance().getDescription().getVersion();
                        Messaging.send(sender, "&eMessenger: &fv" + ver);
                    }
                    else
                        Messaging.send(sender, command.getPermissionMessage());
                    break;

                case "help":
                default:
                    sendHelp(sender);
                    break;
            }
        }
        else
        {
            sendHelp(sender);
        }

        return true;
    }

    private void sendHelp(CommandSender sender)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            TextComponent header = Jsonify.tooltip(Messaging.colorful("&eMessenger &6&l>> &f&oHelp"), Messaging.colorful("Made by &eWolveh"));
            TextComponent tooltipLine = Jsonify.tooltip(Messaging.colorful("&7&oHover over a command for more info"), "Just like that!");
            TextComponent msgCmdText = Jsonify.tooltip(Messaging.colorful("  &e- /msg &o<player> <message>"), "Send a message to a player.");
            TextComponent replyCmdText = Jsonify.tooltip(Messaging.colorful("  &e- /reply &o<message>"), "Reply to a message");
            TextComponent spyCmdText = Jsonify.tooltip(Messaging.colorful("  &e- /socialspy"), "Toggle social spy");

            Messaging.send(player, header);
            Messaging.send(player, tooltipLine);
            Messaging.send(player, msgCmdText);
            Messaging.send(player, replyCmdText);
            if (player.hasPermission("messaging.spy"))
                Messaging.send(player, spyCmdText);
        }
        else
        {
            Messaging.send(sender, "&eMessenger &6>> &fHelp");
            Messaging.send(sender, "  &e- /msg <player> <message> &f- Send a message to a player");
            Messaging.send(sender, "  &e- /reply <message> &f- Reply to a message");
        }
    }
}
