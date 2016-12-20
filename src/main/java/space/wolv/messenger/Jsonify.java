package space.wolv.messenger;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Jsonify
{
    public static TextComponent tooltip(String message, String tooltip)
    {
        TextComponent jsonMsg = new TextComponent(message);

        return tooltip(jsonMsg, tooltip);
    }

    public static TextComponent tooltip(TextComponent jsonMsg, String tooltip)
    {
        ComponentBuilder tooltipComponent = new ComponentBuilder(tooltip);
        jsonMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltipComponent.create()));

        return jsonMsg;
    }

    public static TextComponent suggest(String message, String suggest)
    {
        TextComponent jsonMsg = new TextComponent(message);

        return suggest(jsonMsg, suggest);
    }

    public static TextComponent suggest(TextComponent jsonMsg, String suggest)
    {
        jsonMsg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));

        return jsonMsg;
    }

    public static TextComponent url(String message, String url)
    {
        TextComponent jsonMsg = new TextComponent(message);

        return url(jsonMsg, url);
    }

    public static TextComponent url(TextComponent jsonMsg, String url)
    {
        jsonMsg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));

        return jsonMsg;
    }

    public static TextComponent run(String message, String command)
    {
        TextComponent jsonMsg = new TextComponent(message);

        return run(jsonMsg, command);
    }

    public static TextComponent run(TextComponent jsonMsg, String command)
    {
        jsonMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));

        return jsonMsg;
    }
}

