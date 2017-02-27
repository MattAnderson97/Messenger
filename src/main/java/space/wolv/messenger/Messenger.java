package space.wolv.messenger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import space.wolv.messenger.commands.HelpCmd;
import space.wolv.messenger.commands.MsgCmd;
import space.wolv.messenger.commands.ReplyCmd;
import space.wolv.messenger.commands.SpyCmd;
import space.wolv.messenger.events.OnPlayerChat;
import space.wolv.messenger.events.OnPlayerQuit;

import java.io.File;
import java.util.HashMap;

public class Messenger extends JavaPlugin
{
    public static HashMap<String, String> hash = new HashMap<>();
    public static HashMap<String, Boolean> hashBool = new HashMap<>();;
    private static boolean essentials;
    private static FileConfiguration config;

    @Override
    public void onEnable()
    {
        essentials = (getServer().getPluginManager().isPluginEnabled("Essentials"));

        File configFile = new File(getDataFolder(), File.separator + "config.yml");

        if(!configFile.exists())
        {
            this.saveDefaultConfig();
        }

        config = getConfig();

        getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerChat(), this);

        this.getCommand("message").setExecutor(new MsgCmd());
        this.getCommand("reply").setExecutor(new ReplyCmd());
        this.getCommand("messenger").setExecutor(new HelpCmd());
        this.getCommand("socialspy").setExecutor(new SpyCmd());
    }

    @Override
    public void onDisable()
    {
        hash.clear();
    }

    public static boolean hasEssentials()
    {
        return essentials;
    }

    public static FileConfiguration getConfiguration()
    {
        return config;
    }
}
