package eextr0.suggestionsbox.Listeners;

import eextr0.suggestionsbox.Data.PlayerInputData;
import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final SuggestionsBox plugin;

    public PlayerChatListener(SuggestionsBox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerInputData playerInputData = plugin.playerInputMap.get(player.getUniqueId());
        if (playerInputData != null && playerInputData.isWaitingForInput()) {
            event.setCancelled(true);

            playerInputData.processInput(event.getMessage(), "Under Review");
            if (playerInputData.isInputSessionComplete()) {
                plugin.playerInputMap.remove(player.getUniqueId());
            } else {
                playerInputData.promptNextInput();
            }
        }
    }
}
