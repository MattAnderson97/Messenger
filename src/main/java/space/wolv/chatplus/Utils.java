package space.wolv.chatplus;

import org.bukkit.Bukkit;
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
}