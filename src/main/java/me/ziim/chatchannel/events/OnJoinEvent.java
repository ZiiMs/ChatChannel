package me.ziim.chatchannel.events;

import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.ChannelHelper;
import me.ziim.chatchannel.util.sqlUtil;
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
        sqlUtil sqlHelper = new sqlUtil();
        String[] chans = sqlHelper.getChannels(uuid.toString());
        ChannelHelper channelHelper = ChatChannel.cHelper;
        System.out.println(chans.length);
        if (chans.length == 0) return;
        for (String chanName : chans) {
            if (channelHelper.getChannelTitle(chanName) != null) {
                System.out.println(channelHelper.getChannelTitle(chanName).channel);
                channelHelper.addPlayer(player, channelHelper.getChannelTitle(chanName).prefix);
            }
        }
    }
}
