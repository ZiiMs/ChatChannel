package me.ziim.chatchannel;

import me.ziim.chatchannel.commands.*;
import me.ziim.chatchannel.events.ChatListener;
import me.ziim.chatchannel.events.OnJoinEvent;
import me.ziim.chatchannel.util.ChannelHelper;
import me.ziim.chatchannel.util.DBHelper;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.sql.Statement;

public final class ChatChannel extends JavaPlugin {

    public static ChannelHelper cHelper = new ChannelHelper();

    @Override
    public void onEnable() {
        getLogger().info("ChatChannel started");

        this.getCommand("createchannel").setExecutor(new CreateChannel());
        this.getCommand("createchannel").setTabCompleter(new CreateChannel());
        this.getCommand("join").setExecutor(new JoinChannel());
        this.getCommand("join").setTabCompleter(new JoinChannel());
        this.getCommand("editprefix").setExecutor(new EditPrefix());
        this.getCommand("editchannel").setExecutor(new EditChannel());
        this.getCommand("editcolor").setExecutor(new EditColor());
        this.getCommand("editcolor").setTabCompleter(new EditColor());
        this.getCommand("channels").setExecutor(new Channels());
        
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new OnJoinEvent(), this);

        DBHelper dbHelper = new DBHelper();
        try {
            Statement statement = dbHelper.connect().createStatement();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.disconnect();
        }
    }

}
