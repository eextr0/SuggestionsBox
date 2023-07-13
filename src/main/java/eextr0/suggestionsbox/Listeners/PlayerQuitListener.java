package eextr0.suggestionsbox.Listeners;

import eextr0.suggestionsbox.Data.PlayerInputData;
import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final SuggestionsBox plugin;
    public PlayerQuitListener (SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (plugin.playerInputMap.containsKey(player.getUniqueId())) {
            plugin.getPlayerInputData().cancelInput();
        }
    }
}
