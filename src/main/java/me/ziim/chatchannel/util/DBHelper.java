package me.ziim.chatchannel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private final String driver = "com.mysql.jdbc.Driver";
    private final String password = "root";
    private final String db = "spigot";
    private final String username = "root";
    private String host = "localhost:3307";

    private final String dbUrl = "jdbc:mysql://" + this.host + "/" + this.db + "?autoReconnect=true&useSSL=false";

    private Connection connection;

    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(dbUrl, username, password);
            } catch (ClassNotFoundException | SQLException e) {
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
