package eextr0.suggestionsbox.Listeners;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInputCancelListener implements Listener {

    private final SuggestionsBox plugin;
    private boolean checkSaveDraft;
    public PlayerInputCancelListener(SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(plugin.playerInputMap.containsKey(player.getUniqueId()) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            checkSaveDraft = true;
            player.sendMessage("Do you want to save a draft? Type 'yes' or 'no' in chat.");
            plugin.getTimeoutTask().runTaskLater(plugin, 20 * 30);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (checkSaveDraft) {
            String response = event.getMessage().toLowerCase();
            if (response.equals("yes")) {
                player.sendMessage("Saving as draft...");
            } else if (response.equals("no")) {
                player.sendMessage("Draft not saved.");
            } else {
                player.sendMessage("Invalid response. Please type 'yes' or 'no'");
                return;
            }
            plugin.getPlayerInputData().cancelInput();
            checkSaveDraft = false;
            if (plugin.getPlayerInputData().isInputSessionComplete()) {
                plugin.playerInputMap.remove(player.getUniqueId());
            }
        }
    }
}
