package me.ziim.chatchannel;

import me.ziim.chatchannel.commands.CreateChannel;
import me.ziim.chatchannel.commands.JoinChannel;
import me.ziim.chatchannel.events.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ChatChannel extends JavaPlugin {

    private static Connection connection;
    private String host = "localhost:3307", db = "spigot", username = "root", password = "root";

    public static Connection getConnection() {
        return connection;
    }

    @Override
    public void onEnable() {
        getLogger().info("ChatChannel started");

        this.getCommand("createchannel").setExecutor(new CreateChannel());
        this.getCommand("createchannel").setTabCompleter(new CreateChannel());
        this.getCommand("join").setExecutor(new JoinChannel());
//        this.getCommand("join").setTabCompleter(new JoinChannel());
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
        try {
            openConnection();
            Statement statement = connection.createStatement();
            int id = 0;
            String channelSql = "create table if not exists channels( " +
                    "id int auto_increment, " +
                    "prefix varchar (32) not null, " +
                    "channel varchar(64) not null, " +
                    "color varchar(16) not null, " +
                    "constraint channels_pk primary key (id)," +
                    "unique channel (channel)," +
                    "unique prefix (prefix)" +
                    "); ";

            statement.executeUpdate(channelSql);
            String playerSql = "create table if not exists players" +
                    "(" +
                    "UUID varchar (128) not null," +
                    "channels varchar (128) not null," +
                    "constraint players_pk primary key (UUID)," +
                    "unique uuid (UUID)" +
                    ");";
            statement.executeUpdate(playerSql);
//            statement.executeUpdate(sql);
//            ResultSet rs = statement.getGeneratedKeys();
//            if (rs.first()) {
//                id = (int) rs.getLong(1);
//            }
//            System.out.println("id = " + id);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        synchronized (this) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.db + "?autoReconnect=true&useSSL=false", this.username, this.password);
        }
    }

    @Override
    public void onDisable() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
