package me.ziim.chatchannel.commands;

import me.ziim.chatchannel.ChatChannel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static me.ziim.chatchannel.ChatChannel.getConnection;

public class JoinChannel implements TabExecutor {
    Plugin plugin = ChatChannel.getPlugin(ChatChannel.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) return false;
        Connection con = getConnection();
        Player player = (Player) sender;
        try {
            String channel = String.join(" ", args);
            String getSql = "SELECT * FROM channels where channel=?";
            System.out.println("Connections????????? " + con.toString());
            PreparedStatement statement = con.prepareStatement(getSql);
            statement.setString(1, channel);
            System.out.println("Statement: " + statement.toString());
            ResultSet results = statement.executeQuery();
            if (!results.next()) {
                player.sendMessage(ChatColor.RED + "ERROR: Channel not found");
            } else {
                if (hasPerm(player, "cc." + channel)) {
                    String joinSql = "INSERT INTO players(channels, UUID) values (?, ?)";
                    if (ifExists(player.getUniqueId().toString())) {
                        joinSql = "update players set channels = ? where UUID = ?";
                        System.out.println("UPDATEING");
                    }
                    player.sendMessage(ChatColor.YELLOW + "Joining channel " + channel);
                    PreparedStatement joinStatement = con.prepareStatement(joinSql);
                    String[] channels = new String[]{"testt", "testtt"};
                    joinStatement.setString(1, Arrays.toString(channels));
                    joinStatement.setString(2, player.getUniqueId().toString());
                    System.out.println("joinStatement: " + joinStatement.toString());
                    joinStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean ifExists(String uuid) {
        String getSql = "SELECT * FROM players where UUID = ?";
        Connection con = getConnection();
        try {
            PreparedStatement find = con.prepareStatement(getSql);
            find.setString(1, uuid);
            ResultSet results = find.executeQuery();
            System.out.println("Find: " + find.toString());
            if (!results.next()) {
                System.out.println("false");
                return false;
            } else {
                System.out.println("true");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
