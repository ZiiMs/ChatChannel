package me.ziim.chatchannel.events;

import me.ziim.chatchannel.ChatChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class OnJoinEvent implements Listener {
    Plugin plugin = ChatChannel.getPlugin(ChatChannel.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

    }
}
