package me.ziim.chatchannel.commands;

import me.ziim.chatchannel.Channel;
import me.ziim.chatchannel.ChatChannel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class listChannels implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Channel[] channels = ChatChannel.cHelper.getChannels();
        Player player = (Player) sender;
        StringBuilder msg;
        if (channels.length == 0) {
            msg = new StringBuilder("No channels found.");
        } else {
            msg = new StringBuilder("All channels:");
            for (Channel chan : channels) {
                String tempString = chan.color + chan.channel + ": " + chan.prefix + ", ";
                msg.append(tempString);
            }
        }
        player.sendMessage(msg.toString());
        return true;
    }
}
