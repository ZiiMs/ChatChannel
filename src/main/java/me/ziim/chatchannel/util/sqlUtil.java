package me.ziim.chatchannel.util;

import me.ziim.chatchannel.ChatChannel;
import org.apache.commons.lang.ArrayUtils;
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
                String result = results.getString("channels");
                if (result.length() > 2) {
                    String newResults = result.replace("[", "").replace("]", "");
                    newResults = newResults.trim();
                    channels = newResults.split(", ");
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
        return channels;
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
                    PreparedStatement statement = dbHelper.connect().prepareStatement(sql);
                    statement.setString(1, Arrays.toString(channels));
                    statement.setString(2, uuid);
                    statement.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    dbHelper.disconnect();
                }
            }
        };
        r.runTaskAsynchronously(plugin);
    }

    public void removeChannel(String uuid, String channel) {
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String sql = "UPDATE players set channels = ? where UUID = ?";
                    String[] channels = getChannels(uuid);
//                    String foundChan = Arrays.stream(channels).filter(s -> s.equals(channel)).findFirst().orElse(null);
//                    if (foundChan != null) {
//                        ArrayUtils.removeElement(channels, channel);
//                    }
                    int i = 0;
                    for (String chan : channels) {
                        if (chan.toLowerCase().equals(channel.toLowerCase())) {
                            channels = (String[]) ArrayUtils.remove(channels, i);
                            break;
                        }
                        i++;
                    }
                    PreparedStatement statement = dbHelper.connect().prepareStatement(sql);
                    statement.setString(1, Arrays.toString(channels));
                    statement.setString(2, uuid);
                    statement.executeUpdate();
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
            pst.setString(3, String.valueOf(color.getChar()));
            pst.executeUpdate();
            player.sendMessage(color + "You have create channel " + title + ". With prefix " + prefix);
            System.out.println(player.getName() + " has created channel: " + title + " with prefix:" + prefix + " and color: " + color.name());
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

    public boolean updatePrefix(String channel, String prefix, Player player) {
        String updateSQL = "UPDATE channels SET prefix = ? WHERE channel = ?";
        try {
            PreparedStatement stmt = dbHelper.connect().prepareStatement(updateSQL);
            stmt.setString(1, prefix);
            stmt.setString(2, channel);
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                player.sendMessage(ChatColor.RED + "ERROR: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
            return false;
        } finally {
            dbHelper.disconnect();
        }
        return true;
    }

    public boolean updateChannel(String prefix, String channel, Player player) {
        String updateSQL = "UPDATE channels SET channel = ? WHERE prefix = ?";
        try {
            PreparedStatement stmt = dbHelper.connect().prepareStatement(updateSQL);
            stmt.setString(1, channel);
            stmt.setString(2, prefix);
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                player.sendMessage(ChatColor.RED + "ERROR: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
            return false;
        } finally {
            dbHelper.disconnect();
        }
        return true;
    }

    public boolean updateColor(String prefix, ChatColor color, Player player) {
        String updateSQL = "UPDATE channels SET color = ? WHERE prefix = ?";
        try {
            PreparedStatement stmt = dbHelper.connect().prepareStatement(updateSQL);
            stmt.setString(1, String.valueOf(color.getChar()));
            stmt.setString(2, prefix);
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                player.sendMessage(ChatColor.RED + "ERROR: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
            return false;
        } finally {
            dbHelper.disconnect();
        }
        return true;
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

    public boolean containsPrefix(String prefix) {
        try {
            String getSql = "SELECT * FROM channels where prefix=?";
            PreparedStatement statement = dbHelper.connect().prepareStatement(getSql);
            statement.setString(1, prefix);
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
