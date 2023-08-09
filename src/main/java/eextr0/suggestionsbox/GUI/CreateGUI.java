package eextr0.suggestionsbox.GUI;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CreateGUI {

    private final SuggestionsBox plugin;

    public CreateGUI(SuggestionsBox plugin) {
        this.plugin = plugin;
    }

    public void openUI(Player player, String title, ArrayList<ItemStack> itemList, int currentPage) {
        int pageSize = 28;
        int totalPages = (int) Math.ceil((double) itemList.size() / pageSize);
        int startIndex = (currentPage -1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, itemList.size());
        Inventory gui = Bukkit.createInventory(null, 54, title);

        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        assert glassMeta != null;
        glassMeta.setDisplayName(" ");
        glassPane.setItemMeta(glassMeta);

        for (int i = 0; i < gui.getSize(); i++) {

            ItemStack slotItem = gui.getItem(i);
            if (slotItem == null || slotItem.getType() == Material.AIR) {
                gui.setItem(i, glassPane);
            }
        }

        int guiIndex = 10;

        for (int itemIndex = startIndex; itemIndex < endIndex; itemIndex ++) {
            ItemStack item = itemList.get(itemIndex);
            if (guiIndex < 44) {
                gui.setItem(guiIndex, item);
                guiIndex++;
                if (guiIndex == 17 || guiIndex == 26 || guiIndex == 35) {
                    guiIndex += 2;
                }
            }
        }

        if (currentPage > 1) {
            ItemStack prevPage = new ItemStack(Material.ARROW);
            ItemMeta prevPageMeta = prevPage.getItemMeta();
            assert prevPageMeta != null;
            prevPageMeta.setDisplayName("Previous Page");
            prevPage.setItemMeta(prevPageMeta);
            gui.setItem(45, prevPage);
        }

        if (currentPage < totalPages) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta nextPageMeta = nextPage.getItemMeta();
            assert nextPageMeta != null;
            nextPageMeta.setDisplayName("Next Page");
            nextPage.setItemMeta(nextPageMeta);
            gui.setItem(53, nextPage);
        }
        player.openInventory(gui);
        plugin.registerGUI(player, gui, currentPage, itemList, title);
    }
}
