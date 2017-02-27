package space.wolv.chatplus;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import space.wolv.chatplus.commands.CoreCmd;
import space.wolv.chatplus.commands.MsgCmd;
import space.wolv.chatplus.commands.ReplyCmd;
import space.wolv.chatplus.commands.SpyCmd;
import space.wolv.chatplus.events.OnPlayerChat;
import space.wolv.chatplus.events.OnPlayerQuit;

import java.io.File;
import java.util.HashMap;

public class ChatPlus extends JavaPlugin
{
    public static class ConfigData
    {
        private String newMessageRaw;
        private boolean formattingEnabled, requireFormattingPermission, customChatEnabled, chatJsonEnabled;

        public ConfigData(String newMessageRaw, boolean formattingEnabled, boolean requireFormattingPermission, boolean customChatEnabled, boolean chatJsonEnabled)
        {
            this.newMessageRaw = newMessageRaw;
            this.formattingEnabled = formattingEnabled;
            this.requireFormattingPermission = requireFormattingPermission;
            this.customChatEnabled = customChatEnabled;
            this.chatJsonEnabled = chatJsonEnabled;
        }

        public boolean getFormattingEnabled()
        {
            return formattingEnabled;
        }

        public boolean getRequireFormattingPermission()
        {
            return requireFormattingPermission;
        }

        public boolean getCustomChatEnabled()
        {
            return customChatEnabled;
        }

        public boolean getChatJsonEnabled()
        {
            return chatJsonEnabled;
        }

        public String getNewMessageRaw()
        {
            return newMessageRaw;
        }
    }

    public static HashMap<String, String> hash = new HashMap<>();
    public static HashMap<String, Boolean> hashBool = new HashMap<>();;
    private static boolean essentials;
    private static FileConfiguration config;
    private static ConfigData configData;
    private static ChatPlus chatPlus;

    @Override
    public void onEnable()
    {
        essentials = (getServer().getPluginManager().isPluginEnabled("Essentials"));
        chatPlus = this;

        File configFile = new File(getDataFolder(), File.separator + "config.yml");

        if(!configFile.exists())
        {
            this.saveDefaultConfig();
        }

        config = getConfig();
        loadConfigData();

        getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerChat(), this);

        this.getCommand("message").setExecutor(new MsgCmd());
        this.getCommand("reply").setExecutor(new ReplyCmd());
        this.getCommand("chatplus").setExecutor(new CoreCmd());
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

    public static void reloadData()
    {
        chatPlus.reloadConfig();
        config = chatPlus.getConfig();
        loadConfigData();
    }

    private static void loadConfigData()
    {
        String newMessageRaw = config.getString("chat.outputSyntax");
        boolean requireFormattingPermission = config.getBoolean("global.requireFormattingPermission");
        boolean formattingEnabled = config.getBoolean("global.enableFormatting");
        boolean customChatEnabled = config.getBoolean("chat.customChatEnabled");
        boolean chatJsonEnabled = config.getBoolean("chat.enableJson");

        configData = new ConfigData(newMessageRaw, formattingEnabled, requireFormattingPermission, customChatEnabled, chatJsonEnabled);
    }

    public static ConfigData getConfigData()
    {
        return configData;
    }

    public static ChatPlus getInstance()
    {
        return chatPlus;
    }
}
