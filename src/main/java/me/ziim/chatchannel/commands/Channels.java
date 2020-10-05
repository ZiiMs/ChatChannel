package me.ziim.chatchannel.commands;

import me.ziim.chatchannel.Channel;
import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.sqlUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Channels implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        sqlUtil sqlHelper = new sqlUtil();
        String[] channels = sqlHelper.getChannels(player.getUniqueId().toString());
        StringBuilder msg;
        if (channels.length == 0) {
            msg = new StringBuilder("You are not in any channels.");
        } else {
            msg = new StringBuilder(ChatColor.UNDERLINE + "Channels:\n");
            for (String chan : channels) {
                Channel channel = ChatChannel.cHelper.getChannelTitle(chan);
                if (channel == null) continue;
                String newString = channel.color + channel.channel + ": " + channel.prefix + ", ";
                msg.append(newString);
            }
        }
        player.sendMessage(msg.toString());
        return true;
    }
}
