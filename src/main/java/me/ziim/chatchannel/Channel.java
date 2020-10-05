package me.ziim.chatchannel;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Channel {
    public String prefix;
    public String channel;
    public ChatColor color;
    public int id;
    public List<Player> players;

    public Channel(String prefix, String channel, ChatColor color, int id) {
        System.out.println("New channel: " + channel);
        this.prefix = prefix;
        this.channel = channel;
        this.color = color;
        this.id = id;
        this.players = new ArrayList<>();
    }

    public void addPlayers(Player player) {
        System.out.println("Chan = " + prefix);
        this.players.add(player);
        System.out.println("players = " + players);
    }

    public boolean hasPlayer(Player player) {
        this.players.add(player);
        System.out.println("Chan = " + prefix);
        System.out.println("players = " + players);

        return this.players.contains(player);
    }

    public Set<Player> getRecipients() {
        Set<Player> recipients = new HashSet<>(this.players);
        return recipients;
    }
}
