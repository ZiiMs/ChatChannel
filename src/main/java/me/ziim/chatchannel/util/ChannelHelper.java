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
                Channel chan = new Channel(prefix, channel, color, id);
                channelMap.put(prefix, chan);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        System.out.println("channelMap = " + channelMap.get("$").channel);
    }

    public void addPlayer(Player player, String prefix) {
        Channel chan = channelMap.get(prefix);
        chan.addPlayers(player);
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
