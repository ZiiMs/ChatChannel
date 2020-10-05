package me.ziim.chatchannel.events;

import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.ChannelHelper;
import me.ziim.chatchannel.util.sqlUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLeaveEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        sqlUtil sqlHelper = new sqlUtil();
        String[] channels = sqlHelper.getChannels(player.getUniqueId().toString());
        if (channels.length == 0) return;
        ChannelHelper cHelper = ChatChannel.cHelper;
        for (String channel : channels) {
            cHelper.removePlayer(player, cHelper.getChannelTitle(channel).prefix);
        }
    }
}
