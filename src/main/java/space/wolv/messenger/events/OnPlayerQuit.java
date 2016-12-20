package space.wolv.messenger.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.wolv.messenger.Utils;

import static space.wolv.messenger.Messenger.hash;

public class OnPlayerQuit implements Listener
{
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if (hash.containsKey(player.getUniqueId().toString() + ".reply"))
        {
            Player replyPlayer = Utils.getPlayerFromString(hash.get(player.getUniqueId().toString() + ".reply"));
            hash.remove(replyPlayer.getUniqueId().toString() + ".reply");
            hash.remove(player.getUniqueId().toString() + ".reply");
        }
    }
}
