package me.ziim.chatchannel.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static me.ziim.chatchannel.ChatChannel.getConnection;

public class CreateChannel implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println(Arrays.toString(args));
        if (args.length < 3) return false;
        Player player = (Player) sender;
        String[] newArray = Arrays.copyOfRange(args, 2, args.length);
        String prefix = args[0];
        String stringColor = args[1];
        ChatColor color = getColorFromText(stringColor);
        String title = String.join(" ", newArray);
        Connection con = getConnection();
        try {
            String sql = "INSERT INTO channels(prefix, channel, color) values (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, prefix);
            pst.setString(2, title);
            pst.setString(3, String.valueOf(color));
            pst.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {

                player.sendMessage(ChatColor.RED + "ERROR: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
        }

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


