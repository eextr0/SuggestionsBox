package eextr0.suggestionsbox.Tasks;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeoutTask extends BukkitRunnable {

    private final Player player;
    private final SuggestionsBox plugin;

    public TimeoutTask (Player player, SuggestionsBox plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (plugin.getPlayerInputData().isWaitingForInput()) {
            player.sendMessage("Input timed out.");
            plugin.getPlayerInputData().setWaitingForInput(false);
        }
    }
}
