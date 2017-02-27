package space.wolv.messenger;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendMessage
{
    public static void sendMessage(CommandSender sender, Player recipient, String message)
    {

        //color
        if (sender.hasPermission("messenger.color")) message = Messaging.colorful(message);

        String messageSender = Messaging.colorful("&6(&eme &6&m-->&e " + recipient.getName() + "&6)&f") + message;
        String messageRecipient = Messaging.colorful("&6(&e" + sender.getName() + " &6&m-->&e me&6)&f") + message;

        if(Messenger.hasEssentials())
        {
            Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
            User user = essentials.getUser(recipient.getName());
            if (user.isAfk())
            {
                Messaging.send(sender, "&3This player is currently AFK and may not respond.");

                if (sender instanceof Player)
                {
                    if (recipient.isOp() || recipient.hasPermission("messenger.staff"))
                    {
                        Player player = (Player) sender;

                        TextComponent onlineStaff = new TextComponent();
                        boolean otherStaffFound = false;
                        int count = 0;

                        for (Player loopPlayer : Bukkit.getOnlinePlayers())
                        {
                            if (loopPlayer.isOp() || loopPlayer.hasPermission("messenger.staff"))
                            {
                                if (!loopPlayer.equals(recipient))
                                {
                                    if (!otherStaffFound) otherStaffFound = true;

                                    if (count >= 1) onlineStaff.addExtra(", ");

                                    TextComponent jsonMsg = Jsonify.tooltip(loopPlayer.getName(), "Click to message");
                                    onlineStaff.addExtra(Jsonify.suggest(jsonMsg, "/msg " + loopPlayer.getName() + " "));
                                    count++;
                                }
                            }
                        }

                        if (otherStaffFound)
                        {
                            Messaging.send(player, "&b&oOther staff online (click a name to send a message):");
                            Messaging.send(player, onlineStaff);
                        }
                    }
                }
            }
        }

        if(sender instanceof Player)
        {
            Player player = (Player) sender;

            TextComponent jsonMsgSender = Jsonify.tooltip(messageSender, Messaging.colorful("&f&oClick to reply"));
            jsonMsgSender = Jsonify.suggest(jsonMsgSender, "/msg " + recipient.getName() + " ");

            TextComponent jsonMsgRecipient = Jsonify.tooltip(messageRecipient, Messaging.colorful("&f&oClick to reply"));
            jsonMsgRecipient = Jsonify.suggest(jsonMsgRecipient, "/msg " + sender.getName() + " ");

            Messaging.send(player, jsonMsgSender);
            Messaging.send(recipient, jsonMsgRecipient);

            Messenger.hash.put(player.getUniqueId().toString() + "." + DataTypes.REPLY.toString(), recipient.getName());
            Messenger.hash.put(recipient.getUniqueId().toString() + "." + DataTypes.REPLY.toString(), player.getName());

            spy(message, sender, recipient);
        }
        else
        {
            messageSender = Messaging.colorful("&6[&eMSG&6] &fTO " + recipient.getName() + ": &r" + message);
            Messaging.send(sender, messageSender);
        }

        TextComponent jsonMsgRecipient = Jsonify.tooltip(messageRecipient, Messaging.colorful("&f&oClick to reply"));
        jsonMsgRecipient = Jsonify.suggest(jsonMsgRecipient, "/msg " + sender.getName() + " ");

        Messaging.send(recipient, jsonMsgRecipient);
    }

    private static void spy(String message, CommandSender sender, Player recipient)
    {
        String spyMsg = Messaging.colorful("&8(&7" + sender.getName() + " &8&m-->&7 " + recipient.getName() + "&8)&f " + message);

        Messaging.send(spyMsg);

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!(sender == player || recipient == player))
            {
                if (player.hasPermission("messenger.spy") || player.isOp())
                {
                    if (Messenger.hashBool.containsKey(player.getUniqueId().toString() + "." + DataTypes.SPY.toString()))
                    {
                        if (Messenger.hashBool.get(player.getUniqueId().toString() + "." + DataTypes.SPY.toString()))
                        {
                            Messaging.send(player, spyMsg);
                        }
                    }
                }
            }
        });
    }
}
