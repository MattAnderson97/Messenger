package space.wolv.messenger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.wolv.messenger.DataTypes;
import space.wolv.messenger.Messaging;

import static space.wolv.messenger.Messenger.hashBool;

public class SpyCmd implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
        {
            Messaging.send(sender, "&cThis command is only executable by players");
            return true;
        }

        Player player = (Player) sender;

        if (!hashBool.containsKey(player.getUniqueId().toString() + "." + DataTypes.SPY.toString()))
        {
            hashBool.put(player.getUniqueId().toString() + "." + DataTypes.SPY.toString(), true);
            Messaging.send(player, "&6Message spy: &aenabled");
        }
        else
        {
            hashBool.put(player.getUniqueId().toString() + "." + DataTypes.SPY.toString(), ! hashBool.get(player.getUniqueId().toString() + "." + DataTypes.SPY.toString()));

            if (hashBool.get(player.getUniqueId().toString() + "." + DataTypes.SPY.toString()))
                Messaging.send(player, "&6Message spy: &aenabled");
            else
                Messaging.send(player, "&6Message spy: &cdisabled");
        }

        return true;
    }
}
