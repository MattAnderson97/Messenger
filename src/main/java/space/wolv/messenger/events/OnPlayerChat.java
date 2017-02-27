package space.wolv.messenger.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import space.wolv.messenger.Messaging;
import space.wolv.messenger.Messenger;


public class OnPlayerChat implements Listener
{
    FileConfiguration config;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String message = event.getMessage();
        config = Messenger.getConfiguration();

        if (config.getBoolean("chat.customChatEnabled"))
        {
            event.setCancelled(true);

            TextComponent jsonifiedMessage = new TextComponent();
            String newMessageRaw = config.getString("chat.outputSyntax");
            String newMessageColorful = Messaging.colorful(newMessageRaw);
            String newMessage = getVars(newMessageColorful, player, message);

            if (config.getBoolean("chat.enableJson"))
            {
                jsonifiedMessage.addExtra(getJson(newMessage));
            }
            else
            {
                jsonifiedMessage.addExtra(newMessage);
            }

            Bukkit.getOnlinePlayers().forEach(loopPlayer -> loopPlayer.spigot().sendMessage(jsonifiedMessage));
        }
    }

    private String getVars(String message, Player player, String originalMessage)
    {
        message = message.replace("%player-name%", player.getName());
        message = message.replace("%display-name%", player.getDisplayName());
        message = message.replace("%player-world%", player.getWorld().getName());

        if (config.getBoolean("chat.enableFormatting"))
        {
            message = message.replace("%message%", (!config.getBoolean("chat.requireFormattingPermission") || (config.getBoolean("chat.requireFormattingPermisison") && player.hasPermission("chat.formatting"))) ? Messaging.colorful(originalMessage) : originalMessage);
        }
        else
        {
            message = message.replace("%message%", originalMessage);
        }

        return message;
    }

    private TextComponent getJson(String msg)
    {
        TextComponent jsonifiedMessage = new TextComponent();

        String[] jsonParts = msg.split("\\{json\\{");
        for (String jsonPart : jsonParts)
        {
            String[] splitMessage = jsonPart.split("}}}");

            for (String messagePart : splitMessage)
            {
                if (messagePart.contains("||"))
                {
                    String[] splitJsonParts = messagePart.split("\\|\\|");
                    String jsonMsg = splitJsonParts[0];
                    TextComponent json = new TextComponent(jsonMsg);

                    for (int i = 1; i < splitJsonParts.length; i++)
                    {
                        if (splitJsonParts[i].startsWith("ttp{"))
                        {
                            String toolTipText = getJsonComponentText(splitJsonParts[i], "ttp{");
                            json.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(toolTipText).create()));
                        }

                        else if (splitJsonParts[i].startsWith("sgt{"))
                        {
                            String commandName = getJsonComponentText(splitJsonParts[i], "sgt{");
                            json.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commandName));
                        }

                        else if (splitJsonParts[i].startsWith("run{"))
                        {
                            String commandName = getJsonComponentText(splitJsonParts[i], "run{");
                            json.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandName));
                        }

                        else if (splitJsonParts[i].startsWith("url{"))
                        {
                            String url = getJsonComponentText(splitJsonParts[i], "url{");
                            json.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                        }
                    }

                    jsonifiedMessage.addExtra(json);
                }
                else
                {
                    jsonifiedMessage.addExtra(messagePart);
                }
            }
        }

        return jsonifiedMessage;
    }

    private String getJsonComponentText(String jsonPart, String componentType)
    {
        String jsonComponent = "";

        for (char character : jsonPart.replace(componentType, "").toCharArray())
        {
            if (character != '{')
            {
                jsonComponent += Character.toString(character);
            }
        }

        return Messaging.colorful(jsonComponent);
    }
}
