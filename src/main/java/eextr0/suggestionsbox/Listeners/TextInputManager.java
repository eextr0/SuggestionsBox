package eextr0.suggestionsbox.Listeners;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TextInputManager implements Listener {

    private final Map<UUID, CompletableFuture<String>> inputMap;
    private final SuggestionsBox plugin;

    public TextInputManager(SuggestionsBox plugin) {
        this.plugin = plugin;
        inputMap = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public CompletableFuture<String> requestInput(Player player) {
        CompletableFuture<String> inputFuture = new CompletableFuture<>();
        inputMap.put(player.getUniqueId(), inputFuture);
        return inputFuture;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (inputMap.containsKey(playerId)) {
            event.setCancelled(true);
            String input = event.getMessage();
            CompletableFuture<String> inputFuture = inputMap.remove(playerId);
            inputFuture.complete(input);
        }
    }
}
