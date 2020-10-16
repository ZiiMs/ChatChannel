package me.ziim.chatchannel.events;

import me.ziim.chatchannel.Channel;
import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.ChannelHelper;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        String baseMessage = e.getMessage();
        Player player = e.getPlayer();
        ChannelHelper channels = ChatChannel.cHelper;
        String[] arr = baseMessage.split(" ", 2);
        if (arr.length <= 1) {
            return;
        }
        String first = arr[0];
        String message = arr[1];

        if (channels.hasPrefix(first) && channels.inChannel(player, first)) {
            e.setCancelled(true);
            Channel chan = channels.getChannel(first);
            e.setFormat(chan.color + "[" + chan.channel + "]" + ChatColor.WHITE + " <%1$s> %2$s");
            e.setMessage(message);
            Set<Player> recipients = chan.getRecipients();
            for (Player p : recipients) {
                p.sendMessage(String.format(e.getFormat(), player.getName(), message));
            }
        }
    }
}
