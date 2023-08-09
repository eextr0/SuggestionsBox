package eextr0.suggestionsbox.Listeners;

import eextr0.suggestionsbox.GUI.RegisterGUI;
import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PageNavigationListener implements Listener {

    private final SuggestionsBox plugin;

    public PageNavigationListener(SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        RegisterGUI registerGUI = plugin.checkRegisterGUI();
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) {
            return;
        }

        if (registerGUI.isRegisteredGUI(player, clickedInventory)) {
            event.setCancelled(true);

            int slot = event.getSlot();
            Inventory playerInventory = player.getInventory();

            if (slot == 45) {
                // Previous Page
                int currentPage = registerGUI.getCurrentPage(player);
                if (currentPage > 1) {
                    ArrayList<ItemStack> itemList = registerGUI.getItemList(player);
                    openGUI(player, currentPage - 1, itemList);
                }
            } else if (slot == 53) {
                // Next Page
                int currentPage = registerGUI.getCurrentPage(player);
                ArrayList<ItemStack> itemList = registerGUI.getItemList(player);
                int totalPages = (int) Math.ceil((double) itemList.size() / 28);
                if (currentPage < totalPages) {
                    openGUI(player, currentPage + 1, itemList);
                }
            }
        }
    }

    private void openGUI(Player player, int currentPage, ArrayList<ItemStack> itemList) {
        RegisterGUI registerGUI = plugin.checkRegisterGUI();
        String title = registerGUI.getTitle(player);
        plugin.createGUI().openUI(player, title, itemList, currentPage);
    }
}
