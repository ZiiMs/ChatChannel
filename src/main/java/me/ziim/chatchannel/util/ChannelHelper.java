package me.ziim.chatchannel.util;

import me.ziim.chatchannel.Channel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChannelHelper {

    public Map<String, Channel> channelMap = new HashMap<>();

    public ChannelHelper() {
        DBHelper dbHelper = new DBHelper();
        String sql = "SELECT * FROM channels";
        try {
            PreparedStatement pst = dbHelper.connect().prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                String prefix = results.getString("prefix");
                String channel = results.getString("channel");
                ChatColor color = ChatColor.getByChar(results.getString("color"));
                System.out.println(color);
                Channel chan = new Channel(prefix, channel, color, id);
                channelMap.put(prefix, chan);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        System.out.println(channelMap.size());
    }

    public void init() {
        DBHelper dbHelper = new DBHelper();
        String sql = "SELECT * FROM channels";
        try {
            PreparedStatement pst = dbHelper.connect().prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                String prefix = results.getString("prefix");
                String channel = results.getString("channel");
                ChatColor color = ChatColor.getByChar(results.getString("color"));
                System.out.println(color);
                if (channelMap.getOrDefault(prefix, null) == null) {
                    Channel chan = new Channel(prefix, channel, color, id);
                    channelMap.put(prefix, chan);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        System.out.println(channelMap.size());
    }

    public void addPlayer(Player player, String prefix) {
        System.out.println(channelMap.size());
        Channel chan = channelMap.get(prefix);
        System.out.println("Chan: " + chan);
        chan.addPlayers(player);
    }

    public void setColorByPrefix(String prefix, ChatColor color) {
        if (channelMap.getOrDefault(prefix, null) == null) return;
        channelMap.get(prefix).color = color;
    }

    public void setChannelByPrefix(String prefix, String channel) {
        if (channelMap.getOrDefault(prefix, null) == null) return;
        channelMap.get(prefix).channel = channel;
    }

    public void setPrefixByChannel(String Channel, String prefix) {
        if (getChannelTitle(Channel.toLowerCase()) == null) return;
        System.out.println("setting prefix");
        String oldPrefix = getChannelTitle(Channel.toLowerCase()).prefix;
        getChannelTitle(Channel.toLowerCase()).prefix = prefix;
        channelMap.put(prefix, channelMap.remove(oldPrefix));
    }

    public void removePlayer(Player player, String prefix) {
        Channel chan = channelMap.get(prefix);
        chan.players.removeIf(p -> p.equals(player));
    }

    public Channel getChannelTitle(String name) {
        return channelMap.values().stream().filter(channel -> channel.channel.toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
    }

    public Set<Player> getRecipients(String prefix) {
        Channel chan = channelMap.get(prefix);
        Set<Player> recipients = new HashSet<>(chan.players);
        return recipients;
    }

    public Channel getChannel(String prefix) {
        return channelMap.get(prefix);
    }

    public boolean inChannel(Player player, String prefix) {
        Channel chan = channelMap.get(prefix.trim());
        return chan.hasPlayer(player);
    }

    public boolean hasPrefix(String prefix) {
        return channelMap.get(prefix) != null;
    }
}
