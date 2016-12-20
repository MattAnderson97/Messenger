package space.wolv.messenger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Utils
{
    public static String arrayToString(String[] array)
    {
        String message = "";
        for (int i = 0; i < array.length; i++)
        {
            message += array[i] + " ";
        }

        return message.trim();
    }

    public static String arrayToString(String[] array, String spacing)
    {
        String message = "";
        for (int i = 0; i < array.length; i++)
        {
            message += array[i] + spacing;
        }

        return message.trim();
    }

    public static ArrayList arrayToList(String[] array)
    {
        ArrayList<String> list = new ArrayList<>();

        for(String string : array)
        {
            list.add(string);
        }

        return list;
    }

    public static Player getPlayerFromString(String name)
    {
        name = name.toLowerCase();

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if(player.getName().toLowerCase().startsWith(name)) return player;
            else if (player.getDisplayName().toLowerCase().contains(name)) return player;
            else if (player.getName().toLowerCase().contains(name)) return player;
        }

        return null;
    }

    public static String center(String string)
    {
        long width = 0;

        String trim = ChatColor.stripColor(string).trim();
        for (char c : trim.toCharArray())
        {
            width += getWidthChar(c);
        }

        if (!(320-width>0)) return string;

        long need = (320-width)/8;

        String spaces = "";
        for(int i = 0; i < need; i++)
        {
            spaces += " ";
        }

        return spaces + string.trim() + spaces.substring(3);
    }

    private static long getWidthChar(char c)
    {
        switch(c) {
            case '!':
            case ',':
            case '.':
            case ':':
            case ';':
            case 'i':
            case '|':
            case '\u2022':
                return 2;
            case '\'':
            case '`':
            case 'l':
                return 3;
            case ' ':
            case 'I':
            case 't':
            case 'x':
                return 4;
            case '"':
            case '(':
            case ')':
            case '*':
            case '<':
            case '>':
            case '=':
            case 'f':
            case 'k':
            case '{':
            case '}':
                return 5;
            case '~':
            case '@':
                return 7;
            default:
                return 6;
        }
    }
}