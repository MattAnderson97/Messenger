package space.wolv.chatplus.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
        FileConfiguration config = ChatPlus.getConfigData();

        if (event.isCancelled() || !config.getBoolean("chat.enableCustomChat"))
        {
            return;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage();

        TextComponent newMessage = parse(config.getString("chat.chatStyle"), player, message);
        Bukkit.getOnlinePlayers().forEach(loopPlayer -> loopPlayer.spigot().sendMessage(newMessage));
    }

    private TextComponent parse(String toParse, Player player, String originalMessage)
    {
        TextComponent json = new TextComponent();

        String parsedMessage = toParse.replaceAll("%player%", player.getName())
                                     .replaceAll("%displayname%", player.getDisplayName())
                                     .replaceAll("%message%", originalMessage);

        return parseJson(parsedMessage);
    }

    private TextComponent parseJson(String message)
    {
        TextComponent json = null;

        if (message.contains("||"))
        {
            Bukkit.getServer().broadcastMessage("json!");

            String[] splitMessage = message.split("\\|\\|");
            TextComponent curLine = null;

            for (String line : splitMessage)
            {

                if (line.startsWith("ttp:"))
                {
                    Bukkit.getServer().broadcastMessage("tooltip");
                    ComponentBuilder toolTip = new ComponentBuilder(line.replace("ttp:", "").trim());
                    curLine.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, toolTip.create()));
                }
                else if (line.startsWith("cmd:"))
                {
                    curLine.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, line.replace("cmd:", "")));
                }
                else if (line.startsWith("sgt:"))
                {
                    curLine.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, line.replace("sgt:", "")));
                }
                else if (line.startsWith("url:"))
                {
                    curLine.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, line.replace("url:", "").trim()));
                }
                else
                {
                    if (curLine != null)
                    {
                        if (json != null)
                        {
                            json.addExtra(" " + curLine);
                        }
                        else
                        {
                            json = curLine;
                        }
                    }

                    curLine = new TextComponent(line);
                }
            }
        }
        else
        {
            json.setText(message);
        }

        return json;
    }
}
