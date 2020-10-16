package me.ziim.chatchannel.util;

import me.ziim.chatchannel.ChatChannel;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private final String driver = "com.mysql.jdbc.Driver";
    Plugin plugin = ChatChannel.getPlugin(ChatChannel.class);
    private final String username = plugin.getConfig().getString("data.user");
    private final String password = plugin.getConfig().getString("data.password");
    private final String db = plugin.getConfig().getString("data.database");
    private final String address = plugin.getConfig().getString("data.address");
    private final String port = plugin.getConfig().getString("data.port");
    private String host = address + ":" + port;

    private final String dbUrl = "jdbc:mysql://" + this.host + "/" + this.db + "?autoReconnect=true&useSSL=false";

    private Connection connection;

    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(dbUrl, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("\033[0;31m" + "ERROR: Can't connect to DB, check config file. \u001B[37m");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
