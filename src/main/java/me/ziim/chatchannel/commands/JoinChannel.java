package me.ziim.chatchannel.commands;

import me.ziim.chatchannel.ChatChannel;
import me.ziim.chatchannel.util.ChannelHelper;
import me.ziim.chatchannel.util.sqlUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

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
        if (!sqlHelper.containsChannel(channel)) {
            player.sendMessage(ChatColor.RED + "ERROR: Channel not found");
        } else {
            if (hasPerm(player, "cc." + channel)) {
                if (sqlHelper.hasChannel(uuid, channel)) {
                    player.sendMessage("You are already in that channel");
                    return true;
                }
                String[] channels = new String[]{channel};
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
                ChannelHelper cHelper = ChatChannel.cHelper;
                cHelper.addPlayer(player, cHelper.getChannelTitle(channel).prefix);
                player.sendMessage(ChatColor.YELLOW + "Joining channel " + channel);
            }
        }
        return true;
    }

    public boolean hasPerm(Player player, String perm) {
        for (PermissionAttachmentInfo perms : player.getEffectivePermissions()) {
            if (perms.getPermission().equals(perm) || player.isOp()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
