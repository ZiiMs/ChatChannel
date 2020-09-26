package me.ziim.chatchannel.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        String baseMessage = e.getMessage();
        Player player = e.getPlayer();
        Set<Player> recipients = e.getRecipients();
        String[] arr = baseMessage.split(" ", 2);
        if (arr.length <= 1) {
            return;
        }
        String first = arr[0];
        String message = arr[1];
        if (first.equals("#")) {
            System.out.println(e.getFormat());
            e.setFormat("[Channel] <%1$s> %2$s");
            e.setMessage(message);
        }
    }
}
