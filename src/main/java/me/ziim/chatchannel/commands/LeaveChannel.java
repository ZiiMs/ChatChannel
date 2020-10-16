package me.ziim.chatchannel.commands;

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

public class LeaveChannel implements TabExecutor {

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
            if (cHelper.inChannel(player, cHelper.getChannelTitle(channel).prefix)) {
                sqlHelper.removeChannel(uuid, channel);
                cHelper.removePlayer(player, cHelper.getChannelTitle(channel).prefix);
                player.sendMessage(ChatColor.YELLOW + "Leaving channel " + channel);
            } else {
                player.sendMessage("You are not in that channel");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Player player = (Player) sender;
        sqlUtil sqlHelper = new sqlUtil();
        List<String> channels = new ArrayList<>();
        List<String> tabChannels = new ArrayList<>();
        Collections.addAll(channels, sqlHelper.getChannels(player.getUniqueId().toString()));
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], channels, tabChannels);
        }
        Collections.sort(tabChannels);
        return tabChannels;
    }
}
