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
        String messageRecipient;
        if (sender.hasPermission("messenger.color")) messageRecipient= Messaging.colorful("&6(&e" + sender.getName() + " &6&m-->&e me&6) &f" + message);
        else messageRecipient = Messaging.colorful("&6(&e" + sender.getName() + " &6&m-->&e me&6) &f") + message;

        String messageSender;

        if(Messenger.essentials)
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
                            onlineStaff.addExtra("\n");
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

            if (player.hasPermission("messenger.color")) messageSender = Messaging.colorful("&6(&eme &6&m-->&e " + recipient.getName() + "&6) &f" + message);
            else messageSender = Messaging.colorful("&6(&eme &6&m-->&e " + recipient.getName() + "&6) &f") + message;

            TextComponent jsonMsgSender = Jsonify.tooltip(messageSender, Messaging.colorful("&f&oClick to reply"));
            jsonMsgSender = Jsonify.suggest(jsonMsgSender, "/msg " + recipient.getName() + " ");

            TextComponent jsonMsgRecipient = Jsonify.tooltip(messageRecipient, Messaging.colorful("&f&oClick to reply"));
            jsonMsgRecipient = Jsonify.suggest(jsonMsgRecipient, "/msg " + sender.getName() + " ");

            Messaging.send(player, jsonMsgSender);
            Messaging.send(recipient, jsonMsgRecipient);

            Messenger.hash.put(player.getUniqueId().toString() + ".reply", recipient.getName());
            Messenger.hash.put(player.getUniqueId().toString() + ".reply", player.getName());
        }
        else
        {
            messageSender = Messaging.colorful("&6[&eMSG&6] &fTO " + recipient.getName() + ": &r" + message);

            Messaging.send(sender, messageSender);
            Messaging.send(recipient, messageRecipient);
        }
    }
}
