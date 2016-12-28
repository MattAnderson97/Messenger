package space.wolv.messenger;

import org.bukkit.plugin.java.JavaPlugin;
import space.wolv.messenger.commands.HelpCmd;
import space.wolv.messenger.commands.MsgCmd;
import space.wolv.messenger.commands.ReplyCmd;
import space.wolv.messenger.commands.SpyCmd;
import space.wolv.messenger.events.OnPlayerQuit;

import java.util.HashMap;

public class Messenger extends JavaPlugin
{
    public static HashMap<String, String> hash = new HashMap<>();
    public static HashMap<String, Boolean> hashBool = new HashMap<>();
    public static boolean essentials;

    @Override
    public void onEnable()
    {
        essentials = (getServer().getPluginManager().isPluginEnabled("Essentials"));

        getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);

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
}
