package eextr0.suggestionsbox.Listeners;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerClickListener implements Listener {

    private final SuggestionsBox plugin;

    public PlayerClickListener (SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
            if(event.getClickedInventory() != null && event.getClickedInventory().getHolder() == null) {
                Inventory inventory = event.getClickedInventory();
                int rawSlot = event.getRawSlot();

                if (rawSlot < inventory.getSize() && rawSlot >= 0) {
                    ItemStack item = inventory.getItem(rawSlot);

                    if (item != null && item.getType() != Material.AIR) {
                        event.setCancelled(true);
                    }
                }
            }
    }
}
