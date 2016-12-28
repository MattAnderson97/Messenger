package space.wolv.messenger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.wolv.messenger.DataTypes;
import space.wolv.messenger.Messaging;
import space.wolv.messenger.SendMessage;
import space.wolv.messenger.Utils;

import static space.wolv.messenger.Messenger.hash;

public class ReplyCmd implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            Messaging.send(sender, "&cThis command is only executable by players");
            return false;
        }

        Player player = (Player) sender;

        if (hash.containsKey(player.getUniqueId().toString() + "." + DataTypes.REPLY.toString()))
        {
            if (args.length >= 1)
            {
                String recipientName = hash.get(player.getUniqueId().toString() + "." + DataTypes.REPLY.toString());
                Player recipient = Utils.getPlayerFromString(recipientName);
                String message = Utils.arrayToString(args);

                if (recipient == null)
                {
                    Messaging.send(player, "&cYou have no one to reply to.");
                    return true;
                }

                SendMessage.sendMessage(player, recipient, message);
            }
            else
            {
                Messaging.send(player, "&cNot enough arguments\n" +
                        "usage: /" + label + " <message>");
                return true;
            }
        }
        else
        {
            Messaging.send(player, "&cYou have no one to reply to.");
            return true;
        }

        return true;
    }
}
