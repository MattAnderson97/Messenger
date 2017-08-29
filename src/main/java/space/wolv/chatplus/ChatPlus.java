package space.wolv.chatplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import space.wolv.chatplus.events.PlayerChatListener;

import java.io.File;

public class ChatPlus extends JavaPlugin {
    private static FileConfiguration config;

    @Override
    public void onEnable() {
        File file = new File(getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }

        config = getConfig();

        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static FileConfiguration getConfigData()
    {
        return config;
    }
}
