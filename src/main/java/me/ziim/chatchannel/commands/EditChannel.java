package me.ziim.chatchannel.commands;

import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.ChannelHelper;
import me.ziim.chatchannel.util.sqlUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditChannel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) return false;
        Player player = (Player) sender;
        String channel = args[1];
        String prefix = args[0];

        sqlUtil sqlHelper = new sqlUtil();
        if (!sqlHelper.containsPrefix(prefix)) {
            player.sendMessage(ChatColor.RED + "ERROR: Channel not found.");
        }
        boolean succeed = sqlHelper.updateChannel(prefix, channel, player);
        if (!succeed) return true;
        ChannelHelper cHelper = ChatChannel.cHelper;
        cHelper.setChannelByPrefix(prefix, channel);
        player.sendMessage(ChatColor.GREEN + "You have set channel name to " + channel);
        return true;
    }
}
