package me.ziim.chatchannel;

import me.ziim.chatchannel.commands.CreateChannel;
import me.ziim.chatchannel.events.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatChannel extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ChatChannel started");

        this.getCommand("createchannel").setExecutor(new CreateChannel());
        this.getCommand("createchannel").setTabCompleter(new CreateChannel());
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
