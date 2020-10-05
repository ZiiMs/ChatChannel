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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditColor implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) return false;
        Player player = (Player) sender;
        String color = args[1];
        String prefix = args[0];

        sqlUtil sqlHelper = new sqlUtil();
        if (!sqlHelper.containsPrefix(prefix)) {
            player.sendMessage(ChatColor.RED + "ERROR: Channel not found.");
        }
        ChatColor chatColor = getColorFromText(color);
        boolean succeed = sqlHelper.updateColor(prefix, chatColor, player);
        if (!succeed) return true;
        ChannelHelper cHelper = ChatChannel.cHelper;
        cHelper.setColorByPrefix(prefix, chatColor);
        player.sendMessage(ChatColor.GREEN + "You have set " + cHelper.getChannel(prefix).channel + " color to " + chatColor + chatColor.name());
        return true;
    }

    public ChatColor getColorFromText(String text) {
        switch (text.toLowerCase()) {
            case "black": {
                return ChatColor.BLACK;
            }
            case "darkblue": {
                return ChatColor.DARK_BLUE;
            }
            case "darkgreen": {
                return ChatColor.DARK_GREEN;
            }
            case "darkaqua": {
                return ChatColor.DARK_AQUA;
            }
            case "darkred": {
                return ChatColor.DARK_RED;
            }
            case "darkpurple": {
                return ChatColor.DARK_PURPLE;
            }
            case "gold": {
                return ChatColor.GOLD;
            }
            case "gray": {
                return ChatColor.GRAY;
            }
            case "darkgray": {
                return ChatColor.DARK_GRAY;
            }
            case "blue": {
                return ChatColor.BLUE;
            }
            case "green": {
                return ChatColor.GREEN;
            }
            case "aqua": {
                return ChatColor.AQUA;
            }
            case "red": {
                return ChatColor.RED;
            }
            case "lightpurple": {
                return ChatColor.LIGHT_PURPLE;
            }
            case "yellow": {
                return ChatColor.YELLOW;
            }
            default: {
                return ChatColor.WHITE;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> results = new ArrayList<>();
        completions.addAll(Arrays.asList("black", "darkblue", "darkgreen", "darkaqua", "darkred", "darkpurple", "gold", "gray", "darkgray", "blue", "green", "aqua", "red", "lightpurple", "yellow", "white"));
        if (args.length == 1) {
            results.add("Channel prefix symbol");
            return results;
        }
        if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], completions, results);
        }
        Collections.sort(results);
        return results;
    }
}
