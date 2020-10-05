package me.ziim.chatchannel.commands;

import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.ChannelHelper;
import me.ziim.chatchannel.util.sqlUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditPrefix implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) return false;
        Player player = (Player) sender;
        String channel = args[0];
        String prefix = args[1];

        sqlUtil sqlHelper = new sqlUtil();
        if (!sqlHelper.containsChannel(channel)) {
            player.sendMessage(ChatColor.RED + "ERROR: Channel not found.");
        }
        boolean succeed = sqlHelper.updatePrefix(channel, prefix, player);
        if (!succeed) return true;
        ChannelHelper cHelper = ChatChannel.cHelper;
        cHelper.setPrefixByChannel(channel, prefix);
        player.sendMessage(ChatColor.GREEN + "You have set " + channel + " prefix to " + prefix);
        return true;
    }
}
