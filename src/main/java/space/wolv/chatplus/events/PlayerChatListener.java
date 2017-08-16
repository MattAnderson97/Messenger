package space.wolv.chatplus.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import space.wolv.chatplus.ChatPlus;

public class PlayerChatListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(AsyncPlayerChatEvent event)
    {
        if (event.isCancelled() || !ChatPlus.getConfigData().getBoolean("customChatEnabled"))
            return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage();
        String newMessage = parse(ChatPlus.getConfigData().getString("chatStyle"), player, message);

        Bukkit.getOnlinePlayers().forEach(loopPlayer -> loopPlayer.sendMessage(newMessage));
    }

    public String parse(String toParse, Player player, String originalMessage)
    {
        String[] splitMessage = toParse.split("||");

        for (int i = 0; i < splitMessage.length; i++)
        {
            String messagePart = splitMessage[i];
            splitMessage[i] = messagePart.replaceAll("%player%", player.getName());
            splitMessage[i] = messagePart.replaceAll("%displayname%", player.getDisplayName());
            splitMessage[i] = messagePart.replaceAll("%message%", originalMessage);
        }

        return String.join(" ", splitMessage);
    }
}
