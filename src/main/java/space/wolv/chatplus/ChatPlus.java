package space.wolv.chatplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatPlus extends JavaPlugin
{
    private static FileConfiguration config;

    @Override
    public void onEnable()
    {
        config = getConfig();
    }

    @Override
    public void onDisable()
    {

    }

    public static FileConfiguration getConfigData()
    {
        return config;
    }
}
