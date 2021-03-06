package me.ziim.chatchannel.commands;

import me.ziim.chatchannel.Channel;
import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.ChannelHelper;
import me.ziim.chatchannel.util.sqlUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JoinChannel implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) return false;
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        String channel = String.join(" ", args);
        sqlUtil sqlHelper = new sqlUtil();
        ChannelHelper cHelper = ChatChannel.cHelper;
        if (!sqlHelper.containsChannel(channel)) {
            player.sendMessage(ChatColor.RED + "ERROR: Channel not found");
        } else {
            if (player.hasPermission("cc." + channel)) {
                if (cHelper.inChannel(player, cHelper.getChannelTitle(channel).prefix)) {
                    player.sendMessage("You are already in that channel");
                    return true;
                }
                String[] channels = new String[]{};
                if (sqlHelper.ifExists(uuid)) {
                    List<String> channelList = new ArrayList<>();
                    Collections.addAll(channelList, sqlHelper.getChannels(uuid));
                    channelList.add(channel);
                    channels = channelList.toArray(new String[0]);
                }
                if (channels.length >= 10) {
                    player.sendMessage("You are in the maximum number of channels already");
                    return true;
                }
                sqlHelper.addChannel(uuid, channels);
                cHelper.addPlayer(player, cHelper.getChannelTitle(channel).prefix);
                player.sendMessage(ChatColor.YELLOW + "Joining channel " + channel);
            } else {
                player.sendMessage(ChatColor.RED + "You don't have permission to join that channel.");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        sqlUtil sqlHelper = new sqlUtil();
        ChannelHelper cHelper = ChatChannel.cHelper;
        List<String> channels = new ArrayList<>();
        List<String> tabChannels = new ArrayList<>();
        for (Channel channel : cHelper.getChannels()) {
            if (player.hasPermission("cc." + channel.channel) && !cHelper.inChannel(player, channel.prefix)) {
                channels.add(channel.channel);
            }
        }
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], channels, tabChannels);
        }
        Collections.sort(tabChannels);
        return tabChannels;
    }
}
