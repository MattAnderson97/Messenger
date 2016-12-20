package space.wolv.messenger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.wolv.messenger.Messaging;
import space.wolv.messenger.SendMessage;
import space.wolv.messenger.Utils;

import java.util.Arrays;

public class MsgCmd implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length > 1)
        {
            Player recipient = Utils.getPlayerFromString(args[0]);
            String message = Utils.arrayToString(Arrays.copyOfRange(args, 1, args.length));

            if (recipient == null)
            {
                Messaging.send(sender, "&cPlayer not found.");
                return true;
            }

            SendMessage.sendMessage(sender, recipient, message);
        }
        else
        {
            Messaging.send(sender, "&cNot enough arguments.\n" +
                    "&oUsage: /" + label + " <player> <message>");
            return true;
        }

        return true;
    }
}
