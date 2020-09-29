package me.ziim.chatchannel.util;

import me.ziim.chatchannel.ChatChannel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class sqlUtil {
    DBHelper dbHelper = new DBHelper();
    Plugin plugin = ChatChannel.getPlugin(ChatChannel.class);

    String getSql = "SELECT * FROM players where UUID=?";

    public String[] getChannels(String uuid) {
        String[] channels = new String[]{};
        String getSql = "SELECT * FROM players where UUID=?";
        try {
            PreparedStatement stmt = dbHelper.connect().prepareStatement(getSql);
            stmt.setString(1, uuid);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                String newResults = results.getString("channels").replace("[", "").replace("]", "");
                newResults = newResults.trim();
                System.out.println("newResults = " + newResults);
                channels = newResults.split(", ");
                System.out.println("channels = " + Arrays.toString(channels));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        return channels;
    }

    public boolean hasChannel(String uuid, String channel) {
        try {
            PreparedStatement stmt = dbHelper.connect().prepareStatement(getSql);
            stmt.setString(1, uuid);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                String newResults = results.getString("channels").replace("[", "").replace("]", "").replace(",", "");
                return newResults.contains(channel + " ");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        return false;
    }

    public void addChannel(String uuid, String[] channels) {
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String sql = "update players set channels = ? where UUID = ?";
                    if (!ifExists(uuid)) {
                        sql = "INSERT INTO players(channels, UUID) values (?, ?)";
                    }
                    System.out.println(Arrays.toString(channels));
                    PreparedStatement statement = dbHelper.connect().prepareStatement(sql);
                    statement.setString(1, Arrays.toString(channels));
                    statement.setString(2, uuid);
                    statement.executeUpdate();
                    System.out.println("statement: " + statement.toString());

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    dbHelper.disconnect();
                }
            }
        };
        r.runTaskAsynchronously(plugin);
    }

    public void createChannel(Player player, String prefix, ChatColor color, String title) {
        try {
            String sql = "INSERT INTO channels(prefix, channel, color) values (?, ?, ?)";
            PreparedStatement pst = dbHelper.connect().prepareStatement(sql);
            pst.setString(1, prefix);
            pst.setString(2, title);
            pst.setString(3, String.valueOf(color));
            pst.executeUpdate();
            player.sendMessage(color + "You have create channel " + title + ". With prefix " + prefix);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                player.sendMessage(ChatColor.RED + "ERROR: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
        } finally {
            dbHelper.disconnect();
        }
    }

    public boolean containsChannel(String channel) {
        try {
            String getSql = "SELECT * FROM channels where channel=?";
            PreparedStatement statement = dbHelper.connect().prepareStatement(getSql);
            statement.setString(1, channel);
            ResultSet results = statement.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        return false;
    }

    public boolean ifExists(String uuid) {
        try {
            PreparedStatement find = dbHelper.connect().prepareStatement(getSql);
            find.setString(1, uuid);
            ResultSet results = find.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        return false;
    }

}
